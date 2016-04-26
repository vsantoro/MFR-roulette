package server.models;

import java.util.Hashtable;
import java.util.Set;

import common.Bet;
import java.util.LinkedList;
import common.CommunicationCommands;
import server.models.Game;
import server.controllers.Controller;


public class Croupier implements Runnable
{
    //list of randomly generated speeds for table rotation
    private static final Double[] rotationSpeeds;

    //time reserved for placing bets in miliseconds
    private static final long PYB_TIME =10000;

    //time between games in miliseconds
    private static final long MATCH_SEPARATION_TIME =7000;

    private Controller controller;

    //initialization of various table rotating speeds
    static {
        rotationSpeeds = new Double[300];
        for(int i = 0; i < 300; i++){
            rotationSpeeds[i] = 100.00 + Math.random() * 10000.00;
        }

    }

    //separate thread for croupier activity
	private Thread croupierThread;
	private Game game;

    //croupier status flag
	private volatile boolean isAcceptingBets;

    private volatile boolean isSpinning;

    //contains the list of bets for every user
    private Hashtable<Integer,LinkedList<Bet>> bets;


    public Croupier(Game _game)
    {
        game=_game;
        croupierThread = new Thread(this);
        isSpinning = false;
        bets = new Hashtable<Integer,LinkedList<Bet>>();
    }

    void setController(Controller _controller)
    {
        controller=_controller;
    }

    //================
    //Thread control
    //================

    void start()
    {
        croupierThread.start();
    }
    void terminate() {
        croupierThread.interrupt();
    }


    //================
    //wheel management
    //================

    public synchronized void registerWheelStopping()
    {
        isSpinning =false;
        notify();
    }

    private void spinWheel(Double speed) {
        isSpinning = true;
        game.spinWheel(speed);
    }

    boolean isSpinning(){return isSpinning;}

    //==============
    //bet management
    //==============

    boolean isAcceptingBets()
    {
        return isAcceptingBets;
    }

    //adds a new bet to the players list of bets

    void addNewBet(int playerId,Bet newBet)
    {
        LinkedList<Bet> list=bets.get(playerId);
        if (list == null)
        {
            list=new LinkedList<>();
            list.add(newBet);
        }
        else
            list.add(newBet);

        bets.put(playerId,list);
    }

    //calculates winnings for every player that placed a bet, and updates player's balance

    private synchronized void calculateWinnings()
    {
        Integer winningNumber = game.getWinningNumber();

        //gets the set of ids of players that placed bets
        Set<Integer> keys=bets.keySet();

        for(Integer key : keys)
        {
            LinkedList<Bet> playersBets=bets.get(key);
            double sum = 0;
            for (Bet bet : playersBets) {
                sum += bet.winning(winningNumber);
            }
            game.updatePlayerMoney(key, sum);
            controller.cancelBettingMoney(key);
        }
    }



    //Croupier thread activity

    public void run()
    {
        int gameNumber = 1;
        try
        {
            while (!Thread.interrupted())
            {
                //Accept bets

                isAcceptingBets = true;
                game.sendMessageToAllPlayers(CommunicationCommands.PYB);

                System.out.println("CROUPIER: ACCEPTING NEW BETS!");

                controller.setFields(-2);
                controller.setStatus("Accepting new bets...");

                Thread.sleep(PYB_TIME);

                //Stop accepting bets

                isAcceptingBets = false;
                game.sendMessageToAllPlayers(CommunicationCommands.RNVP);
                System.out.println("CROUPIER: NO MORE BETS!");
                controller.setStatus("No more bets...");

                //randomly chooses a table rotation speed

                double newSpeed=rotationSpeeds[(int)(Math.random()*300)];
                System.out.println("CROUPIER: STARTING GAME NO. " + gameNumber + "");
                controller.setFields(-1);

                //spin the wheel and wait until it stops
                spinWheel(newSpeed);
                synchronized (this)
                {
                    while(isSpinning)
                        wait();
                }

                //gets the winning number and informs the players

                Integer winningNumber = game.getWinningNumber();

                System.out.println("CROUPIER: GAME NO. " + gameNumber + " FINISHED. WINNING NUMBER: " + winningNumber);
                controller.setFields(winningNumber);
                game.sendMessageToAllPlayers(CommunicationCommands.WINNUMBER + " " + winningNumber);

                //calculates player winnings
                calculateWinnings();

                //clears all the bets that were placed in the game
                bets.clear();

                //sleeps for in-between game time
                System.out.println("NEW MATCH STARTS IN " + MATCH_SEPARATION_TIME /1000);
                controller.setStatus("New match starts in " + MATCH_SEPARATION_TIME /1000 + "s...");
                Thread.sleep(MATCH_SEPARATION_TIME);
                gameNumber++;
            }
    	}
        catch(InterruptedException er){}

    }
}
