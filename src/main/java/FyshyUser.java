import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class FyshyUser {
    private IUser user;
    private IMessage input;
    private boolean waitingForInput;

    public FyshyUser(IUser user){
        this.user = user;
        waitingForInput = false;
        input = null;
    }

    public IUser getUser(){
        return user;
    }

    public boolean getWaitingForInput(){
        return waitingForInput;
    }

    public void setWaitingForInput(boolean val){
        waitingForInput = val;
    }

    public IMessage getInput(){
        return input;
    }

    public void setInput(IMessage input){
        this.input = input;
    }
}
