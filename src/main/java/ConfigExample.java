import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class ConfigExample {
    private static final String token = "PUT BOT TOKEN HERE"; //EDIT
    private static final IDiscordClient client = new ClientBuilder().withToken(getToken()).build();
    private static final String botPurpose = "Does a bunch of random things.";
    private static final String botChannelMention = "<@>"; //EDIT

    private final static String COMMAND_PROMPT = "~";
    private final static String HELP_KEYWORD = "HELP";
    private final static String INFO_KEYWORD = "INFO";
    private final static String SAY_KEYWORD = "SAY";
    private final static String JOKES_KEYWORD = "JOKES";
    private final static String[] DELETE_KEYWORDS = {"DELETE", "REMOVE"};
    private final static String[] KEYWORDS = {HELP_KEYWORD, INFO_KEYWORD, SAY_KEYWORD, JOKES_KEYWORD, DELETE_KEYWORDS[0], DELETE_KEYWORDS[1]};

    public static String getToken(){
        return token;
    }

    public static IDiscordClient getClient(){
        return client;
    }

    public static String getBotPurpose(){
        return(botPurpose);
    }

    public static String getBotChannelMention(){
        return botChannelMention;
    }

    public static String getCommandPrompt() {
        return COMMAND_PROMPT;
    }

    public static String getHelpKeyword() {
        return HELP_KEYWORD;
    }

    public static String getInfoKeyword() {
        return INFO_KEYWORD;
    }

    public static String getSayKeyword() {
        return SAY_KEYWORD;
    }

    public static String getJokesKeyword() {
        return JOKES_KEYWORD;
    }

    public static String[] getDeleteKeywords() {
        return DELETE_KEYWORDS;
    }

    public static String[] getKeywords() {
        return KEYWORDS;
    }
}
