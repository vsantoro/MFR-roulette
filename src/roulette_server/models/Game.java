package roulette_server.models;

import java.net.SocketException;
import java.util.Hashtable;
import java.util.Set;


import common.CommunicationCommands;
import roulette_server.*;
import common.Bet;

import roulette_server.controllers.InitialScreenController;
import roulette_server.views.GamePlayScreen;

public class Game 
{
    private double playerStartMoney = 200;
    private Hashtable<Integer, Player> players;
    private Croupier croupier;
    private TableWheel table;
    private Integer winningNumber;



    private Server server;
    private static int maxPlayerNumber=4;
    private InitialScreenController controller;


    public void setServer(int port)
    {
        try
        {
            server=new Server(this,port);
            System.out.println("Server postavljen na vrednost " + port);
        }
        catch (SocketException e) {}
    }

    public void setPlayerStartMoney(double startMoney)
    {
        System.out.println("Pare postavljen na vrednost " + startMoney);

        playerStartMoney=startMoney;
    }

    public  void setMaxPlayerNumber(int _maxPlayerNumber)
    {
        System.out.println("max broj igraca postavljen na vrednost " + _maxPlayerNumber);

        maxPlayerNumber=_maxPlayerNumber;
    }

    public void createCroupierAndTable()
    {
        croupier=new Croupier(this);
        table=new TableWheel();
        table.setCroupier(croupier);
        System.out.println("Postavljeni i sto i krupje");
    }

    public int getMaxPlayerNumber()
    {
        return maxPlayerNumber;
    }

    public void setGamePlayControllerToCroupier(InitialScreenController controller)
    {
        croupier.setGamePlayView(controller);
    }

    public void startCroupier()
    {
        croupier.startCroupier();
    }


    public void setGamePlayController(InitialScreenController _controller)
    {
        controller=_controller;
    }

    public boolean isTableFull()
    {
        if(players.size()==maxPlayerNumber)
            return true;

        return false;
    }

    private static Game instance = null;




    //=====================================
    //singleton constructor and terminators
    //=====================================

    protected Game() {
        players = new Hashtable<Integer, Player>();
//        croupier = new Croupier(this);
//        table = new TableWheel();
//        table.setCroupier(croupier);
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
        Player p = new Player(pp, playerStartMoney, this,controller);
        players.put(new Integer(p.getId()), p);
        controller.insertRowInTable(p.getId(),p.getName(),p.getMoney(),0);
        return playerStartMoney;
    }

    public synchronized void deletePlayer(int playerId)
    {
        controller.removeRow(playerId);
        players.remove(playerId);
    }

    public synchronized  void kickPlayer(int playerId)
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

}
