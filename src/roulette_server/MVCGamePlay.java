package roulette_server;

import roulette_server.controllers.InitialScreenController;
import roulette_server.models.Game;
import roulette_server.views.InitialScreen;


public class MVCGamePlay
{
    public static void main(String[] arg)
    {
        InitialScreen initialScreenView=new InitialScreen();
        Game gameModel= Game.getInstance();

        InitialScreenController initialScreenController=new InitialScreenController(gameModel,initialScreenView);
        initialScreenView.setVisible(true);


    }
}
