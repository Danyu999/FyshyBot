import sx.blah.discord.handle.obj.IGuild;

import java.util.ArrayList;
import java.util.List;

public class Guilds {
    private Users allUsers;
    private ArrayList<Guild> guilds;

    public Guilds(List<IGuild> guilds){
        allUsers = new Users();
        allUsers.addUsers(guilds);
        this.guilds = new ArrayList<Guild>();
        for(IGuild g : guilds){
            this.guilds.add(new Guild(g));
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
