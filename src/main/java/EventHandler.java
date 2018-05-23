import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
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
    public static Guilds guilds = new Guilds();

    @EventSubscriber
    public void onReady(ReadyEvent r){
        Config.getClient().changePresence(StatusType.ONLINE, ActivityType.PLAYING, Config.getCommandPrompt() + Config.getHelpKeyword().toLowerCase());
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
    public void guildCreateEvent(GuildCreateEvent g){
        guilds.addGuild(g.getGuild());
    }

    @EventSubscriber
    public void userJoinEvent(UserJoinEvent u){
        guilds.getAllUsers().addUser(u.getUser());
        guilds.getGuild(u.getGuild()).getUsers().addUser(u.getUser());
        //guilds.getGuild(u.getGuild()).getDefaultTextChannel().sendMessage("Welcome to " + u.getGuild().getName() + "! " + u.getUser());
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

            if (e.getMessage().getContent().substring(0, 1).equals(Config.getCommandPrompt())) {
                runCommand(e, false);
            }
            else if(e.getMessage().getContent().contains(Config.getBotChannelMention())){
                if(!runCommand(e, true)){
                    e.getChannel().sendMessage("Use " + Config.getCommandPrompt() + Config.getHelpKeyword() + " to see a list of commands");
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

    private boolean runCommand(MessageReceivedEvent e, boolean mentionedBot){
        String[] inputArray;
        String inputString = e.getMessage().getContent().trim();
        if(inputString.startsWith(Config.getCommandPrompt())) {
            inputArray = inputString.substring(1).trim().split("\\s+");
            inputString = inputString.substring(1).trim();
        }
        else{
            int mentionIndex = inputString.indexOf(Config.getBotChannelMention());
            inputString = (inputString.substring(0, mentionIndex) + inputString.substring(mentionIndex
                    + Config.getBotChannelMention().length())).trim();
            inputArray = inputString.split("\\s+");

        }
        String inputStringUpper = inputString.toUpperCase();

        //Check if matches command logic
        if(inputStringUpper.startsWith(Config.getHelpKeyword())) {
            runHelp(inputArray,e);
            return true;
        }
        else if(inputStringUpper.startsWith(Config.getInfoKeyword())){
            runInfo(e);
            return true;
        }
        else if(inputStringUpper.startsWith(Config.getSayKeyword())){
            runSay(inputString,e);
            return true;
        }
        else if(inputStringUpper.startsWith(Config.getJokesKeyword())){
            runJokes(e);
            return true;
        }
        else if(inputStringUpper.startsWith(Config.getDeleteKeywords()[0]) || inputStringUpper.startsWith(Config.getDeleteKeywords()[1])){
            runDelete(inputString,e);
            return true;
        }
        else{
            return false;
        }
    }

    private void runHelp(String[] inputArray, MessageReceivedEvent e){
        Help.help(Config.getKeywords(), inputArray, e.getChannel());
    }

    private void runInfo(MessageReceivedEvent e){
        Info.info(e.getChannel());
    }

    private void runSay(String inputString, MessageReceivedEvent e){
        String message = inputString.substring(Config.getSayKeyword().length()).trim();
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
        else if(!Config.getClient().getOurUser().getPermissionsForGuild(e.getGuild()).contains(Permissions.MANAGE_MESSAGES)) {
            channel.sendMessage("FyshyBot does not have permission to manage messages! The delete function is unavailable.");
        }
        else {
            if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.MANAGE_MESSAGES)) {
                Delete delMessages = new Delete(inputString, channel, e.getAuthor());
                delMessages.start();
            } else {
                channel.sendMessage("You are not allowed to manage messages. Contact an administrator for more info.");
            }
        }
    }
}
