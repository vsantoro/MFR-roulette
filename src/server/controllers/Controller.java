package server.controllers;

import server.views.GameView;
import server.views.SetupView;
import server.models.Game;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller
{
    //model
    private Game game;

    //views
    private SetupView setupView;
    private GameView gameView;


    public Controller(Game _gameModel, SetupView _setupView)
    {
        game =_gameModel;
        setupView =_setupView;
        setupView.addConfirmButtonListener(new ConfirmButtonListener());
    }

    //==========
    //Setup view
    //==========

    private class ConfirmButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            try
            {
                /*
                Prepares game for execution:
                -Creates a game server that will enable communication between client and server app
                -Sets maximum number of players
                -Sets starting amount
                -Disposes initial setup view
                -Sets up controller
                -Starts the croupier
                -Creates a new game view and makes it visible
                 */

                game.setStartingAmount(setupView.getStartingAmount());
                game.setMaxPlayerNumber(setupView.getMaxPlayerNumber());
                game.setServer(setupView.getPortNumber());
                setupView.dispose();
                gameView =new GameView();
                gameView.addKickButtonListener(new KickButtonActionListener());
                game.createCroupierAndWheel();
                game.setGamePlayControllerToCroupier(Controller.this);
                game.setGamePlayController(Controller.this);
                game.startCroupier();
                gameView.setVisible(true);
            }
            catch(NumberFormatException ex)
            {
                setupView.displayErrorMessage("You need to enter correct values!");
            }
        }
    }

    //==========
    //Game view
    //==========

    private class KickButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            if( !game.isSpinning() && !game.isAcceptingBets()) {
                int id = gameView.removeSelectedRow();
                game.kickPlayer(id);
            }
            else
                JOptionPane.showMessageDialog(gameView, "Can't kick during the round!");
        }
    }

    public synchronized void setStatus(String info)
    {
        gameView.setStatus(info);
    }

    public synchronized void setFields(int number)
    {
        gameView.setFields(number);
    }

    public synchronized void addTableRow(int playerId, String name, double balance, double betted)
    {
        gameView.addTableRow(playerId,name,balance,betted);
    }

    public synchronized void updateBettingMoney(int playerId, double money)
    {
        gameView.updateBettingMoney(playerId,money);
    }

    public synchronized void updateBalanceMoney(int playerId, double money)
    {
        gameView.updateBalanceMoney(playerId,money);
    }

    public synchronized  void removeTableRow(int playerId)
    {
        gameView.removeTableRow(playerId);
    }

    public synchronized void cancelBettingMoney(int playerId)
    {
        gameView.cancelBettingMoney(playerId);
    }

}
