import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.handle.obj.StatusType;

/*
This class handles events from Discord that the bot knows how to process
 */
public class EventHandler {
    private final static String COMMAND_PROMPT = "~";
    private final static String HELP_KEYWORD = "HELP";
    private final static String SAY_KEYWORD = "SAY";
    private final static String JOKES_KEYWORD = "JOKES";
    private final static String[] DELETE_KEYWORDS = {"DELETE", "REMOVE"};
    private final static String[] KEYWORDS = {HELP_KEYWORD,SAY_KEYWORD,JOKES_KEYWORD, DELETE_KEYWORDS[0], DELETE_KEYWORDS[1]};
    public static Guilds guilds;

    @EventSubscriber
    public void onReady(ReadyEvent r){
        Main.getClient().changePresence(StatusType.ONLINE, ActivityType.PLAYING, "~help");
        guilds = new Guilds(Main.getClient().getGuilds());
        /*
        User creator = guilds.getAllUsers().getUserByName("Danyu\uD83C\uDF43");
        if(creator != null){}
        else{
            for(Guild g : guilds.getGuilds()){
                g.getDefaultTextChannel().sendMessage("```css\nWHERE IS MY CREATOR???```");
            }
        }
        */
    }
    @EventSubscriber
    public void userJoinEvent(UserJoinEvent u){
        guilds.getAllUsers().addUser(u.getUser());
        guilds.getGuild(u.getGuild()).getUsers().addUser(u.getUser());
        guilds.getGuild(u.getGuild()).getDefaultTextChannel().sendMessage("Welcome to " + u.getGuild().getName() + "! " + u.getUser());
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent e){
        if(!e.getAuthor().isBot()) {
            if (e.getChannel().isPrivate()) { //When the current channel is private
                saveUserInput(e, true);
            }
            else{ //When the current channel is public
                saveUserInput(e, false);
            }
            if (e.getMessage().getContent().substring(0, 1).equals(COMMAND_PROMPT)) {
                if (!runCommand(e)) {
                    e.getChannel().sendMessage("Please use " + COMMAND_PROMPT + HELP_KEYWORD.toLowerCase() +
                            " to sea " + Main.getClient().getOurUser().getName() + " commands.");
                }
            }
        }
    }

    private void saveUserInput(MessageReceivedEvent e, boolean isPrivate){
        User user;
        if(isPrivate){
            user = guilds.getAllUsers().getUserByID(e.getAuthor());
        }
        else{
            user = guilds.getGuild(e.getGuild()).getUsers().getUserByID(e.getAuthor());
        }
        if (user == null) {
            e.getChannel().sendMessage("User is not registered. (EventHandler Class)");
        } else {
            if (user.getWaitingForInput()) {
                user.setInput(e.getMessage());
            }
        }
    }

    private boolean runCommand(MessageReceivedEvent e){
        String[] inputArray;
        String inputString = e.getMessage().getContent().trim();
        if(inputString.startsWith(COMMAND_PROMPT)) {
            inputArray = inputString.substring(1).trim().split("\\s+");
            inputString = inputString.substring(1).trim();
        }
        else{
            inputArray = inputString.split("\\s+");
        }
        String inputStringUpper = inputString.toUpperCase();

        if(inputStringUpper.startsWith(HELP_KEYWORD)) {
            runHelp(inputArray,e);
            return true;
        }
        else if(inputStringUpper.startsWith(SAY_KEYWORD)){
            runSay(inputString,e);
            return true;
        }
        else if(inputStringUpper.startsWith(JOKES_KEYWORD)){
            runJokes(e);
            return true;
        }
        else if(inputStringUpper.startsWith(DELETE_KEYWORDS[0]) || inputStringUpper.startsWith(DELETE_KEYWORDS[1])){
            runDelete(inputString,e);
            return true;
        }
        else{
            return false;
        }
    }

    private void runHelp(String[] inputArray, MessageReceivedEvent e){
        Help.help(KEYWORDS, inputArray, e.getChannel());
    }

    private void runSay(String inputString, MessageReceivedEvent e){
        String message = inputString.substring(SAY_KEYWORD.length()).trim();
        e.getChannel().sendMessage(e.getAuthor().getName() + " says: \n" + message);
    }

    private void runJokes(MessageReceivedEvent e){
        Jokes jokes = new Jokes(e.getChannel());
        jokes.start();
    }

    private void runDelete(String inputString, MessageReceivedEvent e){
        IChannel channel = e.getChannel();
        if(channel.isPrivate()){
            channel.sendMessage("Delete function is not available in private channels!");
        }
        else {
            if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.MANAGE_MESSAGES)) {
                Delete delMessages = new Delete(DELETE_KEYWORDS, inputString, channel, e.getAuthor());
                delMessages.start();
            } else {
                channel.sendMessage("You are not allowed to manage messages. Contact an administrator for more info.");
            }
        }

    }
}