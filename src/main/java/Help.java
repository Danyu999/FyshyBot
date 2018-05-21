import sx.blah.discord.handle.obj.IChannel;

public class Help {

    public static void fyshForHelp(IChannel channel){
        channel.sendMessage("```Use ~'command' to talk to FyshyBot \n" +
                "--------------------------------------------- \n" +
                "~~COMMANDS~~\n" +
                "~help || presents this help text \n" +
                "~say || asks FyshyBot to say something \n" +
                "~jokes || tells a fish joke/pun \n" +
                "\n" +
                "~~SPECIAL PERMISSION COMMANDS~~\n" +
                "~delete 'message' || deletes messages in the channel containing the given message \n" +
                "--------------------------------------------- \n" +
                "Use ~help 'command' to see detailed info about that command```");
    }

    public static void fyshForHelp(String[] KEYWORDS,String[] stringArray, IChannel channel){
        if(stringArray.length == 1){
            fyshForHelp(channel);
        }
        else {
            String extraCommand = stringArray[1].trim().toUpperCase();
            if (extraCommand.equals(KEYWORDS[0])) { //Keyword = HELP
                helpHelp(channel);
            } else if (extraCommand.equals(KEYWORDS[1])) { //Keyword = SAY
                sayHelp(channel);
            } else if (extraCommand.equals(KEYWORDS[2])) { //Keyword = JOKES
                jokesHelp(channel);
            } else if (extraCommand.equals(KEYWORDS[3]) || extraCommand.equals(KEYWORDS[4])) { //Keywords = DELETE and REMOVE
                deleteHelp(channel);
            } else {
                fyshForHelp(channel);
            }
        }
    }

    private static void helpHelp(IChannel channel){
        channel.sendMessage("```Use ~help to see a list of commands ||| Use ~help 'command' to see detailed info about that command```");
    }

    private static void sayHelp(IChannel channel){
        channel.sendMessage("```Use ~say 'message' to ask FyshyBot to output the message```");
    }

    private static void jokesHelp(IChannel channel){
        channel.sendMessage("```Use ~jokes to ask FyshyBot to tell a random fish joke/pun```");
    }

    private static void deleteHelp(IChannel channel){
        channel.sendMessage("```Use ~delete 'message' to delete messages from the current channel that contains the message \n" +
                "(Can also use ~remove 'message') \n" +
                "--------------------------------------------\n" +
                "Example: ~delete https would delete all messages in the channel with the string \"https\" \n" +
                "--------------------------------------------\n" +
                "This function is limited to deleting at most 100 messages at a time and at oldest 2 week old messages. " +
                        "Rerun if you want to continue deleting.\n" +
                "--------------------------------------------\n" +
                "Only usable in public channels.\n" +
                "--------------------------------------------\n" +
                "**Only usable by users" +
                " with MANAGE_MESSAGES permission**```");
    }
}
