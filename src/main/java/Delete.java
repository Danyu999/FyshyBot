import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class Delete extends Thread{
    private static final int MAX_MESSAGES_TO_BE_DELETED = 100;
    private String inputString;
    private IChannel channel;
    private IUser author;

    public Delete(String inputString, IChannel channel, IUser author){
        this.inputString = inputString;
        this.channel = channel;
        this.author = author;
    }

    public void run() { //NEW FEATURE IDEA: Add feature to delete messages from a certain user/a certain time period
        String deleteString = inputString.substring(Config.getDeleteKeywords()[0].length()).trim();
        channel.sendMessage("Are you sure you want to delete all messages containing the string in " +
                "the following quotes?(y/n) \"" + deleteString + "\"");
        IMessage tempMessage = channel.getFullMessageHistory().get(0);
        try {
            if (testYesOrNo()) {
                tempMessage.delete();
                List<IMessage> messagesToBeDeleted = new ArrayList<IMessage>();
                for (IMessage i : channel.getFullMessageHistory()) {
                    if (i.getContent().contains(deleteString)) {
                        messagesToBeDeleted.add(i);
                    }
                    if(messagesToBeDeleted.size() == MAX_MESSAGES_TO_BE_DELETED){
                        break;
                    }
                }
                channel.bulkDelete(messagesToBeDeleted);
                channel.sendMessage("Deleted messages with the string \"" + deleteString + "\" successfully!");
                IMessage tempMessage2 = channel.getFullMessageHistory().getLatestMessage();
                sleep(5000);
                tempMessage2.delete();
                interrupt();
            }
            else{
                tempMessage.delete();
                channel.sendMessage("Deletion canceled successfully.");
                IMessage tempMessage2 = channel.getFullMessageHistory().getLatestMessage();
                sleep(5000);
                tempMessage2.delete();
                interrupt();
            }
        }
        catch(InterruptedException e){
            channel.sendMessage("Process Interrupted (Delete Class)");
        }
    }

    private boolean testYesOrNo() throws InterruptedException{
        String[] targetInputs = {"yes","no","y","n"};
        User user = EventHandler.guilds.getGuild(channel.getGuild()).getUsers().getUserByID(author);
        if(user == null){
            channel.sendMessage("User is not registered. (Delete Class)");
        }
        else {
            WaitForUserInput yesOrNo = new WaitForUserInput(user, channel, targetInputs, 15000); //default timeoutPeriod is 15 seconds
            yesOrNo.start();
            yesOrNo.join();
            String userInput = user.getInput().getContent();
            if (userInput.equals(targetInputs[0]) || userInput.equals(targetInputs[2])) {
                user.getInput().delete();
                user.setInput(null);
                return true;
            }
            else if(userInput.equals(targetInputs[1]) || userInput.equals(targetInputs[3])){
                user.getInput().delete();
                user.setInput(null);
            }
            else {
                user.setInput(null);
            }
        }
        return false;
    }
}
