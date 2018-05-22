import sx.blah.discord.handle.obj.IChannel;

public class WaitForUserInput extends Thread{
    private String userInput;
    private String[] targetInputs;
    private IChannel channel;
    private User user;

    public WaitForUserInput(User user, IChannel channel, String[] targetInputs){
        this.channel = channel;
        this.userInput = "";
        this.targetInputs = targetInputs;
        this.user = user;
    }

    private boolean checkMatchInputs(){
        for(String s : targetInputs){
            if(s.toLowerCase().equals(userInput.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public void run(){
        if(user == null){
            channel.sendMessage("User is not registered. (WaitForUserInput)");
            return;
        }
        try {
            while (!checkMatchInputs()) {
                user.setWaitingForInput(true);
                if(user.getInput() != null && user.getInput().getChannel() == channel) {
                    userInput = user.getInput().getContent();
                }
                sleep(1000);
            }
            user.setWaitingForInput(false);
            interrupt();
        }
        catch(InterruptedException e){}
    }
}
