package roulette_server;

import roulette_server.controllers.InitialScreenController;
import roulette_server.models.Game;
import roulette_server.views.InitialScreen;

/**
 * Created by Jovan on 5/31/2015.
 */
public class MVCGamePlay
{
    public static final void main(String[] arg)
    {
        InitialScreen initialScreenView=new InitialScreen();
        Game gameModel= Game.getInstance();

        InitialScreenController initialScreenController=new InitialScreenController(gameModel,initialScreenView);
        initialScreenView.setVisible(true);
//            java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new roulette_server.views.InitialScreen().setVisible(true);
//            }
//            });


    }
}
