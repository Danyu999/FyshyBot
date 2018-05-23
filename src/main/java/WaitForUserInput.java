import sx.blah.discord.handle.obj.IChannel;

public class WaitForUserInput extends Thread{
    private static final long sleepPeriod = 1000;
    private long timeoutPeriod;
    private String userInput;
    private String[] targetInputs;
    private IChannel channel;
    private User user;

    public WaitForUserInput(User user, IChannel channel, String[] targetInputs, long timeOutPeriod){
        this.channel = channel;
        this.userInput = "";
        this.targetInputs = targetInputs;
        this.user = user;
        this.timeoutPeriod = timeOutPeriod;
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
            long timeoutCounter = 0;
            while (!checkMatchInputs()) {
                user.setWaitingForInput(true);
                if(user.getInput() != null && user.getInput().getChannel() == channel) {
                    userInput = user.getInput().getContent();
                }
                sleep(sleepPeriod);
                timeoutCounter += sleepPeriod;
                if(timeoutCounter >= timeoutPeriod){
                    channel.sendMessage("Waited for valid response for " + timeoutPeriod/1000 + " seconds. Process cancelled!");
                    user.setInput(channel.getFullMessageHistory().getLatestMessage());
                    break;
                }
            }
            user.setWaitingForInput(false);
            interrupt();
        }
        catch(InterruptedException e){}
    }
}
