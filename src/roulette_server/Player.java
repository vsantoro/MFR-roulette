/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roulette_server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import roulette.communication.PlayerProxy;
import common.Bet;
import common.Bets;
import common.CommunicationCommands;

/**
 *
 * @author POOP
 */
public class Player implements Runnable
{
    private Thread playerThread = new Thread(this);
    private PlayerProxy playerProxy;
    //**Jovan 
    private Game game;
    private double money;
    private static int id=0;
    private int playerId;
    
    //**
    
    public Player(PlayerProxy _playerProxy, double _startMoney, Game _game)
    {
        playerProxy = _playerProxy;
        //**Jovan
        game=_game;
        money=_startMoney;
        playerId=++id;
        //**
        playerThread.start();
    }
       
    public void run()
    {
        // napraviti ciklus u kojem nit ceka da PlayerProxy dostavi poruku
        // pozivom metode PlayerProxy.receive()
        
        // zatim analizirati sadrzaj poruke (koja komanda je u pitanju)
        // i postupiti saglasno primljenoj poruci
    	
    	//***Jovan
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
    	//**
    	
    }
    
    private void quitGame()  //u mesto public stavio private
    {
        // U ovoj metodi igrac javlja klasi Game da napusta igru
        // A klasa Game onda tog igraca uklanja iz spiska igraca
        // (vodeci racuna da se mozda upravo u tom trenutku neki
        // drugi igrac prijavljuje - dakle treba obezbediti
        // medjusobnu iskljucivost niti kod pristupa)

    	game.deletePlayer(playerId);
    }
    //**
    
    // Metoda kojom neki akter sa serverske strane (na primer - krupije)
    // salje klijentu poruku o nekom dogadjaju (na primer - dozvoljeno
    // ulaganje) 
    public void reportMessage(String message)
    {
        try 
        {
            playerProxy.send(message);
        } 
        catch (IOException ex) 
        {
            // Razmisliti: mozda nije pravo mesto za hvatanje ovog izuzetka,
            // jer onaj deo koda iz kojeg se klijentu nesto signalizira nece
            // imati obavestenje da signalizacija nije uspela.
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
    }
    
    public int getId()
    {
    	return playerId;
    }
}
