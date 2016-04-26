package server.models;

import java.net.SocketException;
import java.util.Hashtable;
import java.util.Set;


import common.CommunicationCommands;
import server.*;
import common.Bet;

import server.controllers.Controller;

/*
Singleton class that implements main game activity
 */
public class Game 
{
    private static Game instance = null;
    private double startingAmount = 200;
    private Hashtable<Integer, Player> players;
    private Croupier croupier;
    private SpinningWheel wheel;

    private Server server;
    private static int maxPlayerNumber=4;
    private Controller controller;


    public void setServer(int port)
    {
        try
        {
            server=new Server(this,port);
            System.out.println("Port value: " + port);
        }
        catch (SocketException e) {}
    }

    public void setStartingAmount(double startMoney)
    {
        System.out.println("Player's starting money: " + startMoney);

        startingAmount =startMoney;
    }

    public void setMaxPlayerNumber(int _maxPlayerNumber)
    {
        System.out.println("Maximum number of players: " + _maxPlayerNumber);

        maxPlayerNumber=_maxPlayerNumber;
    }

    public void createCroupierAndWheel()
    {
        croupier=new Croupier(this);
        wheel =new SpinningWheel();
        wheel.setCroupier(croupier);

        System.out.println("Croupier and wheel are ready!");
    }

    public int getMaxPlayerNumber()
    {
        return maxPlayerNumber;
    }

    public void setGamePlayControllerToCroupier(Controller controller)
    {
        croupier.setController(controller);
    }

    public void startCroupier()
    {
        croupier.start();
    }


    public void setGamePlayController(Controller _controller)
    {
        controller=_controller;
    }

    //checks whether there is place for more players
    public boolean isTableFull()
    {
        if(players.size()==maxPlayerNumber)
            return true;

        return false;
    }

    //checks whether a new player's name is occupied
    public boolean isNameOccupied(String name)
    {
        Set<Integer> keys=players.keySet();
        for(Integer k : keys)
        {
            Player p=players.get(k);
            if(p.getName().equals(name))
                return true;
        }
        return false;
    }


    //=====================================
    //singleton constructor and terminators
    //=====================================

    protected Game() {
        players = new Hashtable<Integer, Player>();
    }

    //gets a singleton instance
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private void terminateCroupier() {
        croupier.terminate();
    }

    private void terminateTable() {
        wheel.terminate();
    }



    //=================
    //player management
    //=================

    //adds a new player to the game
    public synchronized double addPlayer(PlayerProxy pp, String _name)
    {
        Player p = new Player(pp, startingAmount, this,controller,_name);
        players.put(new Integer(p.getId()), p);
        controller.addTableRow(p.getId(),p.getName(),p.getMoney(),0);
        return startingAmount;
    }

    //removes player from the game
    public synchronized void removePlayer(int playerId)
    {
        controller.removeTableRow(playerId);
        players.remove(playerId);
    }

    //kicks out player from the game
    public synchronized  void kickPlayer(int playerId)
    {
        Player kickedPlayer = players.get(playerId);
        kickedPlayer.sendMessage(CommunicationCommands.QUIT_RESPONSE);
        players.remove(playerId);
    }

    public double getStartingMoney()
    {
        return startingAmount;
    }


    synchronized void updatePlayerMoney(int id, double money) {
        Player player = players.get(id);
        player.updateMoney(money);
        if(money > 0) player.sendMessage(CommunicationCommands.WIN + " " + money);
        else player.sendMessage(CommunicationCommands.WIN + " " + 0);
    }



    //=============
    //communication
    //=============

    void sendMessageToAllPlayers(String message)
    {
        Set<Integer> keys=players.keySet();
        for(Integer k : keys)
        {
            Player p=players.get(k);
            p.sendMessage(message);
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

    synchronized void spinWheel(double speed)
    {
        wheel.startSpinning(speed);
    }

    public boolean isSpinning()
    {
        return croupier.isSpinning();
    }
    int getWinningNumber()
    {
        return wheel.getWinningNumber();
    }

}
