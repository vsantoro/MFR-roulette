/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roulette_server;

import java.util.Hashtable;
import java.util.Set;
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
    private Double[] rotationSpeeds;
    public void run()
    {
        try
        {
            while (true)
            {
                acceptingBets=true;
                game.sendMessageToAllPlayers(CommunicationCommands.PYB);
                System.out.println("Krupje poceo!");

                Thread.sleep(15000);

                System.out.println("Krupje zavrsio!");
                acceptingBets=false;
                game.sendMessageToAllPlayers(CommunicationCommands.RNVP);
                double newSpeed=rotationSpeeds[(int)(Math.random()*5)];
                game.spinTable(newSpeed);
                synchronized (this)
                {
                    while(!wheelStoppedSpinning)
                        wait();
                }
                game.sendMessageToAllPlayers(CommunicationCommands.WINNUMBER + " " + game.getWinningNumber());
                wheelStoppedSpinning=false;
                calculateWinnings();
                bets.clear();
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
        rotationSpeeds=new Double[5];
        rotationSpeeds[0]=100.0;
        rotationSpeeds[1]=200.0;
        rotationSpeeds[2]=300.0;
        rotationSpeeds[3]=400.0;
        rotationSpeeds[4]=500.0;

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
            list=new LinkedList<>();
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

    public synchronized void calculateWinnings() {
        Integer winningNumber = game.getWinningNumber();
        Set<Integer> keys=bets.keySet();
        for(Integer key : keys) {
            LinkedList<Bet> player_bets=bets.get(key);
            double sum = 0;
            for (Bet player_bet : player_bets) {
                sum += player_bet.winning(winningNumber);
            }
            game.updatePlayerMoney(key, sum);
        }
    }
}
