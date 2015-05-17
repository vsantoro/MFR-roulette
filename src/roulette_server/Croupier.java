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
    private volatile boolean spinning;
    private Hashtable<Integer,LinkedList<Bet>> bets;
    private static final Double[] rotationSpeeds;



    //==========================
    //constructor and terminator
    //==========================

    static {
        rotationSpeeds = new Double[300];
        for(int i = 0; i < 300; i++){
            rotationSpeeds[i] = 100.00 + Math.random() * 10000.00;
        }

    }

    public Croupier(Game _game)
    {
        game=_game;
        croupierThread = new Thread(this);
        spinning = false;
        bets = new Hashtable<Integer,LinkedList<Bet>>();
        croupierThread.start();
    }

    public void terminate() {
        croupierThread.interrupt();
    }



    //================
    //table management
    //================

    public synchronized void wheelStopped()
    {
        spinning=false;
        notify();
    }

    public void spinWheel(Double speed) {
        spinning = true;
        game.spinWheel(speed);
    }



    //==============
    //bet management
    //==============

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



    //===
    //run
    //===

    public void run()
    {
        int game_no = 1;
        try
        {
            while (!Thread.interrupted())
            {
                acceptingBets = true;
                game.sendMessageToAllPlayers(CommunicationCommands.PYB);
                System.out.println("CROUPIER: ACCEPTING NEW BETS!");

                Thread.sleep(15000);

                System.out.println("CROUPIER: NOT ACCEPTING NEW BETS!");
                acceptingBets = false;
                game.sendMessageToAllPlayers(CommunicationCommands.RNVP);
                double newSpeed=rotationSpeeds[(int)(Math.random()*300)];
                System.out.println("CROUPIER: STARTING GAME NO. " + game_no + "");
                spinWheel(newSpeed);
                synchronized (this)
                {
                    while(spinning)
                        wait();
                }
                System.out.println("CROUPIER: GAME NO. " + game_no + " FINISHED. WINNING NUMBER: " + game.getWinningNumber());
                game.sendMessageToAllPlayers(CommunicationCommands.WINNUMBER + " " + game.getWinningNumber());
                game_no++;
                calculateWinnings();
                bets.clear();
            }
    	}
        catch(InterruptedException er){}

    }
}
