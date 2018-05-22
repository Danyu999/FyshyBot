# FyshyBot
A Discord bot that does miscellaneous fyshy things.
Coded using IntelliJ, Maven, and Discord4J

**IMPORTANT NOTES**
-------------------
In order to keep the privacy of FyshyBot's token, the FyshyMain.java class is not included here. To get the bot to work,
simply copy the following code into your main class, putting your bot's token where it says "ENTER TOKEN HERE".
This step is REQUIRED to make the code work. Because well, otherwise you don't have a main class.
__________________________________________________

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class FyshyMain {
    private final static IDiscordClient client = new ClientBuilder().withToken("ENTER TOKEN HERE").build();

    public static void main(String[] args){
        IDiscordClient client = getClient();
        client.getDispatcher().registerListener(new FyshyEventHandler());
        client.login();
    }

    public static IDiscordClient getClient(){
        return client;
    }
}
__________________________________________________

-------------------

