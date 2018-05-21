import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class FyshyUsers {
    private ArrayList<FyshyUser> fyshyUsers;

    public FyshyUsers(){
        fyshyUsers = new ArrayList<FyshyUser>();
    }

    public FyshyUser getFyshyUserByUser(IUser user){ //Returns the FyshyUser associated with the given IUser. Returns null if no such user exists
        for(FyshyUser u : fyshyUsers){
            if(u.getUser() == user){
                return u;
            }
        }
        return null;
    }

    public FyshyUser getFyshyUserByName(String name){
        for(FyshyUser u : fyshyUsers){
            if(u.getUser().getName().equals(name)){
                return u;
            }
        }
        return null;
    }

    public void addUsers(List<IGuild> guilds){ //Builds fyshyUsers by adding all users in the list of given guilds
        for(IGuild g : guilds) {
            for (IUser u : g.getUsers()) {
                if (getFyshyUserByUser(u) == null) {
                    fyshyUsers.add(new FyshyUser(u));
                }
            }
        }
    }

    public void addUsers(IGuild guild){
        for(IUser u : guild.getUsers()){
            if(getFyshyUserByUser(u) == null){
                fyshyUsers.add(new FyshyUser(u));
            }
        }
    }

    public void addUser(IUser user){
        if(getFyshyUserByUser(user) == null) {
            fyshyUsers.add(new FyshyUser(user));
        }
    }
}
