/**
 * Created by Dragan Obradovic on 15-May-15.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roulette_server;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
//import roulette .server.Game;

/**
 *
 * @author POOP
 */
public class Server extends SocketCommunicator implements Runnable {
    private Hashtable<Integer, PlayerProxy> connectedPlayers = new Hashtable<>();
    private Thread serverThread = new Thread(this);
    private int clientID;
    private Game game;

    public Server(Game _game) throws SocketException
    {
        super(SERVER_PORT);
        game = _game;
        serverThread.start();
    }

    public void run() {
        System.out.println("Game server started...");
        while( ! serverThread.interrupted() ) {
            try {
                String message = receive();
                System.out.println("Server received: " + message);
                processMessage(message);
            }
            catch(IOException e) {  }
        }
        System.out.println("... game server ended.");
    }

    public void sendmsg(String message, InetAddress clientAddress, int clientPort) throws IOException {
        send(message, clientAddress, clientPort);
    }

    private void processMessage(String message) throws IOException {

        // Bilo bi dobro najpre proveriti da li je poznata komanda.
        // Ako nije - poslati izvoru nepoznate komande poruku da je nastala greska

        // Bilo bi dobro nekako grupisati komande - na primer po kategoriji:
        // komande konekcije i komande igraca
        // Onda bi ova metoda prvo pitala nesto poput

        // if( connectionCommands.containCommandStartingWith(message) )
        // { ... obradi komandu konekcije ... }

        // if( playerCommands.containCommandStartingWith(message) )
        // { ... izdvoj ID igraca, obradi ostatak komande ... }

        if( message.equals(CommunicationCommands.JOIN_MESSAGE) ) {
            PlayerProxy pp = new PlayerProxy(this, receivePacket.getAddress(), receivePacket.getPort());
            clientID++;
            connectedPlayers.put(clientID, pp);
            double playerStartMoney = game.newPlayer(pp);
            pp.send(CommunicationCommands.WELCOME_MESSAGE + " " + clientID + " "+ game.getStartingMoney());
        }
        else
        if( message.startsWith(CommunicationCommands.QUIT_MESSAGE) ) {
            String []parts = message.split("\\s+");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if( pp != null ) {
                System.out.println("LLL");
                pp.send(message);
                pp.send(CommunicationCommands.QUIT_RESPONSE);
                connectedPlayers.remove(pp);
            }
        }
        else 
        if (message.startsWith(CommunicationCommands.STATE_REQUEST))    //**Jovan-Pretpostavljam da se i state unosi kao "STATE" + ID
		{
        	String[] parts=message.split("\\s+");
            Integer id=Integer.parseInt(parts[1]);
            PlayerProxy pp= connectedPlayers.get(id);
            if( pp != null )
            {
                if(game.isAcceptingBets()==true)
                    pp.send(CommunicationCommands.PYB);
                else
                    pp.send(CommunicationCommands.RNVP);
            }
		}
    }

    public void terminate() {
        serverThread.interrupt();
        datagramSocket.close();
    }
}

