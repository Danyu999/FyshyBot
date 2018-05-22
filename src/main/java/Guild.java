import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class Guild {
    private IChannel defaultTextChannel;
    private IVoiceChannel defaultVoiceChannel;
    private IGuild guildID;
    private Users guildUsers;

    public Guild(IGuild guild){
        guildID = guild;
        guildUsers = new Users();
        guildUsers.addUsers(guild);
        defaultTextChannel = guild.getDefaultChannel();
        defaultVoiceChannel = guild.getVoiceChannels().get(0);
    }

    public IGuild getGuildID() {
        return guildID;
    }

    public Users getUsers() {
        return guildUsers;
    }

    public IChannel getDefaultTextChannel() {
        return defaultTextChannel;
    }

    public IVoiceChannel getDefaultVoiceChannel() {
        return defaultVoiceChannel;
    }
}
