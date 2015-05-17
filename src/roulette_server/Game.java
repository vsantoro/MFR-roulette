package roulette_server;

import java.net.SocketException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


import common.CommunicationCommands;
import roulette_server.TableWheel;
import common.Bet;


public class Game 
{
    private double playerStartMoney = 200;
    private Hashtable<Integer, Player> players;
    private Croupier croupier;
    private TableWheel table;
    private Integer winningNumber;

    private static Game instance = null;




    //=====================================
    //singleton constructor and terminators
    //=====================================

    protected Game() {
        players = new Hashtable<Integer, Player>();
        croupier = new Croupier(this);
        table = new TableWheel();
        table.setCroupier(croupier);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void terminateCroupier() {
        croupier.terminate();
    }

    public void terminateTable() {
        table.terminate();
    }



    //=================
    //player management
    //=================

    public synchronized double newPlayer(PlayerProxy pp)
    {
        Player p = new Player(pp, playerStartMoney, this);
        players.put(new Integer(p.getId()), p);
        return playerStartMoney;
    }

    public synchronized void deletePlayer(int playerId)
    {
        players.remove(playerId);
    }

    public double getStartingMoney()
    {
        return playerStartMoney;
    }

    public synchronized void updatePlayerMoney(int id, double money) {
        Player player = players.get(id);
        player.updateMoney(money);
        if(money > 0) player.reportMessage(CommunicationCommands.WIN + " " + money);
        else player.reportMessage(CommunicationCommands.WIN + " " + 0);
    }



    //=============
    //communication
    //=============

    public void sendMessageToAllPlayers(String message)
    {
        Set<Integer> keys=players.keySet();
        for(Integer k : keys)
        {
            Player p=players.get(k);
            p.reportMessage(message);
        }
    }

    //===============
    //game management
    //===============

    public boolean isAcceptingBets()
    {
        return croupier.isAcceptingBets();
    }

    public void sendBetToCroupier(int playerId,Bet newBet)
    {
        croupier.addNewBet(playerId, newBet);
    }

    public synchronized void spinWheel(double speed)
    {
        table.startSpinning(speed);
    }

    public int getWinningNumber()
    {
        return table.getWinningNumber();
    }



    //====
    //main
    //====

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        String stop = "START";
        try {
            System.out.println("To terminate game, input \"STOP\".");
            Game g1=new Game();
            Server server = new Server(g1);

            while(!stop.equals("STOP")){
                stop = in.next();
            }
            g1.terminateCroupier();
            g1.terminateTable();
            server.terminate();
            System.out.println("Game terminated.");
        }
        catch (SocketException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
