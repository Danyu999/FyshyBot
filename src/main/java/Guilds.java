import sx.blah.discord.handle.obj.IGuild;

import java.util.ArrayList;
import java.util.List;

public class Guilds {
    private Users allUsers;
    private ArrayList<Guild> guilds;

    public Guilds(){
        allUsers = new Users();
        guilds = new ArrayList<Guild>();
    }

    public void addGuild(IGuild guild){
        if(getGuild(guild) == null) {
            guilds.add(new Guild(guild));
            allUsers.addUsers(guild);
        }
    }

    public Users getAllUsers() {
        return allUsers;
    }

    public ArrayList<Guild> getGuilds(){
        return guilds;
    }

    public Guild getGuild(IGuild guild){
        for(Guild g : guilds){
            if(g.getGuildID() == guild){
                return g;
            }
        }
        return null;
    }
}
