/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roulette_server;

import java.net.SocketException;
//import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

//import roulette.communication.PlayerProxy;
//import roulette.communication.Server;

/**
 *
 * @author POOP
 */
public class Game
{
    private double playerStartMoney = 200;
    private Hashtable<Integer, Player> players;
    private Croupier croupier;


	// Pošto je samo jedna igra predviðena, mogao bi da se koristi
	// uzorak Unikat (singleton). Pomoæu tog uzorka se lako može
	// proširiti funkcionalnost da podrži veæi (ali kontrolisan)
	// broj igara.


    // Metoda kojom se prikljucuje nov igrac u igru
	// Metoda vraæa iznos koji je dodeljen igraèu
    public synchronized double newPlayer(PlayerProxy pp)
    {
        Player p = new Player(pp, playerStartMoney, this);
        // Dodati igraèa u kolekciju igraèa koju pamti Game
        //**Jovan
        players.put(new Integer(p.getId()), p);
        //**
        return playerStartMoney;
    }

    // Metoda koja sluzi za "broadcast" - slanje poruke svim igracima
    public void sendMessageToAllPlayers(String message)
    {
    	Set<Integer> keys=players.keySet();
        for(Integer k : keys)
        {
        	Player p=players.get(k);
            p.reportMessage(message);
        }
    }

    public synchronized void deletePlayer(int playerId)
    {
    	//Ako je pocela partija ne sme da se izbaci igrac. Dodaj to kao
    	//uslov kada regulises croupiera , i odluci gde bi trebalo da stoji
    	//(u croupier ili u igri)
    	players.remove(playerId);
    }
//    
//    public void sendToCroupier(String msg)
//    {
//    }
//    
//    public void recieveFromCroupier(String msg)
//    {
//    	
//    }

    public boolean isAcceptingBets()
    {
    	if(croupier.isAcceptingBets()==true)
    		return true;
    	else
    		return false;
    }
    public Game()
    {
    	players=new Hashtable<Integer, Player>();
    	croupier=new Croupier(this);
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        int i = 1;
        try {
            Game g1=new Game();
            Server server = new Server(g1);

        }
        catch (SocketException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }


    	System.out.println("Nesto");
        while(i == 1){
            i = in.nextInt();
        }
        System.out.println("Igra je zavrsena");
    }
}
