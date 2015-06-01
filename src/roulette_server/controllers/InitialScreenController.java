package roulette_server.controllers;

import roulette_server.views.InitialScreen;
import roulette_server.views.GamePlayScreen;
import roulette_server.models.Game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InitialScreenController
{
    private Game gameModel;
    private InitialScreen initialScreenView;
    private GamePlayScreen gamePlayScreen;


    public InitialScreenController(Game _gameModel,InitialScreen _initialScreenView)
    {
        gameModel=_gameModel;
        initialScreenView=_initialScreenView;
        initialScreenView.addConfirmButtonListener(new InitialScreenConfirmListener());
    }

    private class InitialScreenConfirmListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            try
            {
                gameModel.setPlayerStartMoney(initialScreenView.getStartingAmountValue());
                gameModel.setMaxPlayerNumber(initialScreenView.getMaxPlayerNumberValue());
                gameModel.setServer(initialScreenView.getPortNumberValue());
                initialScreenView.dispose();
                gamePlayScreen=new GamePlayScreen();
                gamePlayScreen.addKickButtonActionListener(new KickButtonActionListener());
                gamePlayScreen.setVisible(true);
                gameModel.createCroupierAndTable();
                gameModel.setGamePlayControllerToCroupier(InitialScreenController.this);
                gameModel.setGamePlayController(InitialScreenController.this);
                gameModel.startCroupier();
            }
            catch(NumberFormatException ex)
            {
                initialScreenView.displayErrorMessage("You need to enter correct values!");
            }
        }
    }

    private class KickButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            int id=gamePlayScreen.removeSelectedRow();
            gameModel.kickPlayer(id);
        }
    }

    public synchronized void sendToStatusBar(String info)   //gamePlayScreen
    {
        gamePlayScreen.setToStatusBarTextField(info);
    }

    public synchronized void sendToNumberFields(int number)   //gamePlayScreen
    {
        gamePlayScreen.setToNumberInfoFields(number);
    }

    public synchronized void insertRowInTable(int id,String name,double balance,double betted)
    {
        gamePlayScreen.addRowToTable(id,name,balance,betted);
    }

    public synchronized void updateBettedMoney(int id,double money)
    {
        gamePlayScreen.updateBettedMoney(id,money);
    }

    public synchronized void updateBalaceMoney(int id,double money)
    {
        gamePlayScreen.upadateBalanceMoney(id,money);
    }

    public synchronized  void removeRow(int id)
    {
        gamePlayScreen.removeRow(id);
    }

    public synchronized void anullBettedMoney(int id)
    {
        gamePlayScreen.anullBettedMoney(id);
    }

}
