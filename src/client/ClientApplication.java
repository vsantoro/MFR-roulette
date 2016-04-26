package client;

public class ClientApplication {

    public static void main(String[] args) {
        RoulettePlayer roulettePlayer = new RoulettePlayer();
        final ServerSettingsView serverInfo = new ServerSettingsView();
        Controller controller = new Controller(roulettePlayer, serverInfo);
    }


}
