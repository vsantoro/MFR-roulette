package server;

import server.controllers.Controller;
import server.models.Game;
import server.views.SetupView;

//Main-class
public class ServerApplication
{
    public static void main(String[] arg)
    {
        SetupView setupView=new SetupView();
        Game game= Game.getInstance();

        Controller controller=new Controller(game,setupView);
        setupView.setVisible(true);
    }
}
