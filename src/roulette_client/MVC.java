package roulette_client;

/**
 * Created by Dragan Obradovic on 31-May-15.
 */
public class MVC {

    public static void main(String[] args) {
        RoulettePlayer roulettePlayer = new RoulettePlayer();
        final ServerSettingsView serverInfo = new ServerSettingsView();
        Controller controller = new Controller(roulettePlayer, serverInfo);
    }


}
