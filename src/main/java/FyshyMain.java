import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class FyshyMain {
    private final static IDiscordClient client = new ClientBuilder().withToken("NDM2NzA1ODU3MjU0OTgxNjQz.DeDofQ.lPIzkhPpIPvtzpWeS05WNyBca0g").build();

    public static void main(String[] args){
        IDiscordClient client = getClient();
        client.getDispatcher().registerListener(new FyshyEventHandler());
        client.login();
    }

    public static IDiscordClient getClient(){
        return client;
    }

    public static void sendMessage(String message, IUser author, IChannel channel){
        channel.sendMessage(author.getName() + " says: \n" + message);
    }
}
