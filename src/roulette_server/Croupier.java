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

import common.Bet;
import java.util.LinkedList;
import common.CommunicationCommands;
/**
 *
 * @author POOP
 */
public class Croupier implements Runnable
{
	private Thread croupierThread;
	private Game game;
	private volatile boolean acceptingBets;
    private volatile boolean wheelStoppedSpinning;

    private Hashtable<Integer,LinkedList<Bet>> bets;
    public void run()
    {
        try
        {
            while (true)
            {
                acceptingBets=true;
                game.sendMessageToAllPlayers(CommunicationCommands.PYB);
                System.out.println("Krupje poceo!");

                Thread.sleep(10000);

                System.out.println("Krupje zavrsio!");
                acceptingBets=false;
                game.sendMessageToAllPlayers(CommunicationCommands.RNVP);
                game.spinTable();
                synchronized (this)
                {
                    while(!wheelStoppedSpinning)
                        wait();
                }
                System.out.println(game.getGeneratedNumber());
                wheelStoppedSpinning=false;
            }
    	}
        catch(InterruptedException er){}

    }
    
    public Croupier(Game _game)
    {
    	game=_game;
    	croupierThread=new Thread(this);
        wheelStoppedSpinning=false;
        bets=new Hashtable<Integer,LinkedList<Bet>>();
    	croupierThread.start();
    }
    
    public boolean isAcceptingBets()
    {
        return acceptingBets;
    }

    public void addNewBet(int playerId,Bet newBet)
    {
        LinkedList<Bet> list=bets.get(playerId);
        if(list==null)
        {
            list=new LinkedList<Bet>();
            list.add(newBet);
        }
        else
        {
            list.add(newBet);
        }
        bets.put(playerId,list);
    }

    public synchronized void wheelFinished()
    {
        wheelStoppedSpinning=true;
        notify();
    }
}
