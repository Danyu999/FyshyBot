public class Main {

    public static void main(String[] args){
        Config.getClient().getDispatcher().registerListener(new EventHandler());
        Config.getClient().login();
    }
}
