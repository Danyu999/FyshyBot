import sx.blah.discord.handle.obj.IChannel;

import java.util.Random;

public class Jokes extends Thread{
    private static String[][] jokesList = new String[5][];
    private IChannel channel;

    public Jokes(IChannel channel){
        initJokes();
        this.channel = channel;
    }

    public void run(){
        Random r = new Random();
        try {
            for (String s : jokesList[r.nextInt(jokesList.length)]) {
                channel.sendMessage(s);
                sleep(1000);
            }
            interrupt();
        }
        catch(InterruptedException e){}
    }

    private static void initJokes(){
        jokesList[0] = new String[]{"FyshyBot Attack!", "*splash splash*", "It wasn't very effective..."};
        jokesList[1] = new String[]{"Why are fish always so smart?", "Because... They're always in school!!!"};
        jokesList[2] = new String[]{"Why did the fish cross the road?", "It didn't! Fish can't walk!"};
        jokesList[3] = new String[]{"If you think of a better fish pun,", "Let minnow."};
        jokesList[4] = new String[]{"What did the fish say when he was acquitted?", "\"I'm off the hook!\""};
    }
}
