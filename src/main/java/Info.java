import sx.blah.discord.handle.obj.IChannel;

public class Info {

    public static void info(IChannel channel){
        channel.sendMessage("--INFO--\n" +
                getBotInfo() + "\n" +
                getCreatorInfo() + "\n" +
                "-----------");
    }

    private static String getBotInfo(){
        return("Bot Name: " + Config.getClient().getOurUser().getName() +"\n" +
                "Bot Purpose: " + Config.getBotPurpose());
    }

    private static String getCreatorInfo(){
        return("Creator: Daniel Yu, Lehigh University Undergraduate CSE Student");//\n" +
                //"GitHub: https://github.com/Danyu999");
    }
}
