package roulette_server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.Bet;
import common.Bets;
import common.CommunicationCommands;
import roulette_server.models.Game;
import roulette_server.controllers.InitialScreenController;

public class Player implements Runnable
{
    private Thread playerThread = new Thread(this);
    private PlayerProxy playerProxy;

    private Game game;
    private double money;
    private static int id=0;
    private int playerId;
    private String name;
    private InitialScreenController controller;

    public  String getName()
    {
        return name;
    }

    public  double getMoney()
    {
        return money;
    }

    public Player(PlayerProxy _playerProxy, double _startMoney, Game _game,InitialScreenController _controller)
    {
        playerProxy = _playerProxy;
        game=_game;
        money=_startMoney;
        playerId=++id;
        name="USER_" + playerId;
        controller=_controller;
        playerThread.start();
    }
       
    public void run()
    {
    	while(!playerThread.interrupted())
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
    
    private void quitGame()
    {
    	game.deletePlayer(playerId);
    }

    public void reportMessage(String message)
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
    
    public void processMessage(String message)
    {
    	if(message.equals(CommunicationCommands.QUIT_MESSAGE))
    	{
    		quitGame();
    	}
        else
        if(message.startsWith(CommunicationCommands.BET))
        {
            String[] parts=message.split(" ");
            if(game.isAcceptingBets())
            {
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
                    Bet newBet=Bets.decodeBet(parts[1] + " " + parts[2]);
                    game.sendBetToCroupier(playerId,newBet);
                    double bettedMoney=Double.parseDouble(parts[2]);
                    updateMoney(-bettedMoney);
                    controller.updateBettedMoney(playerId,bettedMoney);
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

    public synchronized void updateMoney(double amount){
        money += amount;
        controller.updateBalaceMoney(playerId,money);
    }
}
