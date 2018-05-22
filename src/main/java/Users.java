import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private ArrayList<User> Users;

    public Users(){
        Users = new ArrayList<User>();
    }

    public User getUserByID(IUser user){ //Returns the User associated with the given IUser. Returns null if no such user exists
        for(User u : Users){
            if(u.getUser() == user){
                return u;
            }
        }
        return null;
    }

    public User getUserByName(String name){
        for(User u : Users){
            if(u.getUser().getName().equals(name)){
                return u;
            }
        }
        return null;
    }

    public void addUsers(List<IGuild> guilds){ //Builds Users by adding all users in the list of given guilds
        for(IGuild g : guilds) {
            for (IUser u : g.getUsers()) {
                if (getUserByID(u) == null) {
                    Users.add(new User(u));
                }
            }
        }
    }

    public void addUsers(IGuild guild){
        for(IUser u : guild.getUsers()){
            if(getUserByID(u) == null){
                Users.add(new User(u));
            }
        }
    }

    public void addUser(IUser user){
        if(getUserByID(user) == null) {
            Users.add(new User(user));
        }
    }
}
