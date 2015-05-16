/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roulette_server;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
//import roulette.common.Bet;
//import roulette.common.TableWheel;

/**
 *
 * @author POOP
 */
public class Croupier implements Runnable
{
	private Thread croupierThread;
	private Game game;
	private volatile boolean acceptingBets;
    public void run()
    {
    	while (true)
    	{
	        acceptingBets=true;
	        game.sendMessageToAllPlayers(CommunicationCommands.PYB);
	        System.out.println("Krupje poceo!");
	        try
	        {
	        	Thread.sleep(15000);
	        }
	        catch(InterruptedException er){}
	        System.out.println("Krupje zavrsio!");
	        acceptingBets=false;
	        game.sendMessageToAllPlayers(CommunicationCommands.RNVP);
    	}
    }
    
    public Croupier(Game _game)
    {
    	game=_game;
    	croupierThread=new Thread(this);
    	croupierThread.start();
    }
    
    public boolean isAcceptingBets()
    {
    	if(acceptingBets==true)
    		return true;
    	return false;
    }
    

}
