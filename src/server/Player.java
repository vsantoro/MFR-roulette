package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.Bet;
import common.Bets;
import common.CommunicationCommands;
import server.models.Game;
import server.controllers.Controller;

public class Player implements Runnable
{
    private Thread playerThread = new Thread(this);
    private PlayerProxy playerProxy;

    private Game game;
    private double money;
    private static int id=0;
    private int playerId;
    private String name;
    private Controller controller;


    private void quitGame()
    {
        game.removePlayer(playerId);
    }

    public  String getName()
    {
        return name;
    }

    public  double getMoney()
    {
        return money;
    }

    public Player(PlayerProxy _playerProxy, double _startMoney, Game _game, Controller _controller, String _name)
    {
        playerProxy = _playerProxy;
        game=_game;
        money=_startMoney;
        playerId=++id;
        //name="USER_" + playerId;
        name=_name;
        controller=_controller;
        playerThread.start();
    }
       
    public void run()
    {
    	while(!Thread.interrupted())
    	{
			try
			{
	    		String msg;
				msg = playerProxy.receive();
				processMessage(msg);
			} 
			catch (InterruptedException e){}
    	}
    }
    
    public void sendMessage(String message)
    {
        try 
        {
            playerProxy.send(message);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //processes recieved message
    public void processMessage(String message)
    {
        //if the player wants to quit
    	if(message.equals(CommunicationCommands.QUIT_MESSAGE))
    		quitGame();
        else
        //if the player wants to place a bet
        if(message.startsWith(CommunicationCommands.BET))
        {
            String[] parts=message.split(" ");
            if(game.isAcceptingBets())
            {
                //if the user has insufficient balance
                if(Double.parseDouble(parts[parts.length-1])>money)
                {
                    try
                    {
                        playerProxy.send(CommunicationCommands.FUND);
                    }
                    catch (IOException e){}
                }
                else
                {
                    //Adds a new bet and updates bet money amount

                    Bet newBet=Bets.decodeBet(parts[1] + " " + parts[2]);
                    game.sendBetToCroupier(playerId,newBet);
                    double bettedMoney=Double.parseDouble(parts[2]);
                    updateMoney(-bettedMoney);
                    controller.updateBettingMoney(playerId,bettedMoney);
                    try
                    {
                        playerProxy.send(CommunicationCommands.ACCEPT);
                    }
                    catch(IOException e){}
                }
            }
            else
            {
                try
                {
                    playerProxy.send(CommunicationCommands.REJECT);
                }
                catch(IOException e){}
            }
        }
        else
        //if the user wants to check out his balance
        if(message.equals(CommunicationCommands.BALANCE))
        {
            try
            {
                synchronized (this)
                {
                     playerProxy.send(CommunicationCommands.BALANCE +" "+ money);
                }
            }
            catch(IOException e){}
        }
    }
    
    public int getId()
    {
    	return playerId;
    }

    public synchronized void updateMoney(double amount)
    {
        money += amount;
        controller.updateBalanceMoney(playerId,money);
    }
}
