package server;

import java.io.IOException;
import java.net.SocketException;
import java.util.Hashtable;

import common.CommunicationCommands;
import server.models.Game;

/*
Class Server receives messages from the client application and interprets them adequately.
 */
public class Server extends SocketCommunicator implements Runnable {

    //hastable of all registered player proxies
    private Hashtable<Integer, PlayerProxy> connectedPlayers = new Hashtable<>();

    //server's separate thread of execution
    private Thread serverThread = new Thread(this);

    //incremented for every new player proxy and assigned as key value at proxy table
    private int CLIENT_ID;
    private Game game;

    public Server(Game _game,int portNumber) throws SocketException
    {
        //super(SERVER_PORT);
        super(portNumber);
        game = _game;
        serverThread.start();
    }

    public void run() {
        System.out.println("Game server started...");
        while( ! Thread.interrupted() ) {
            try {

                //receives the message that is sent through the socket and parses it

                String message = receive();
                System.out.println("Server received: " + message);
                processMessage(message);
            }
            catch(IOException e) {  }
        }
        System.out.println("Game server ended...");
    }

    private void processMessage(String message) throws IOException {

        //Players joins a game
        if( message.startsWith(CommunicationCommands.JOIN_MESSAGE) ) {
            PlayerProxy pp = new PlayerProxy(this, receivePacket.getAddress(), receivePacket.getPort());

            //if the game is maxed out
            if(game.isTableFull()==true)
                pp.send(CommunicationCommands.BUSY);
            else
            {
                //adds new player to the game
                String[] parts=message.split(" ");
                CLIENT_ID++;
                connectedPlayers.put(CLIENT_ID, pp);
                String name=parts[1];
                if(game.isNameOccupied(name))
                    name="USER_" + CLIENT_ID;

                double startingMoney = game.addPlayer(pp,name);
                pp.send(CommunicationCommands.WELCOME_MESSAGE + " " + name + " " + CLIENT_ID + " " + startingMoney);
            }
        }
        else
        //Player exits the game
        if( message.startsWith(CommunicationCommands.QUIT_MESSAGE) ) {
            String []parts = message.split("\\s+");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if( pp != null )
            {
                pp.setMessage(parts[0]);
                pp.send(CommunicationCommands.QUIT_RESPONSE);
                connectedPlayers.remove(id);
            }
        }
        else
        //Players requests game state
        if (message.startsWith(CommunicationCommands.STATE_REQUEST))
		{
        	String[] parts=message.split("\\s+");
            Integer id=Integer.parseInt(parts[1]);
            PlayerProxy pp= connectedPlayers.get(id);
            if( pp != null )
            {
                if(game.isAcceptingBets())
                    //Sends place your bets state
                    pp.send(CommunicationCommands.PYB);
                else
                    //Sends no more bets status (Rien ne va plus)
                    pp.send(CommunicationCommands.RNVP);
            }
		}
        else
        //Player wants to place a bet
        if(message.startsWith(CommunicationCommands.BET)){
            String[] parts = message.split("\\s+");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if (pp != null) {
                pp.setMessage(parts[0] + " " +  parts[2] + " " + parts[3]);
            }
        }
        else
        //Players requests his balance information
        if(message.startsWith(CommunicationCommands.BALANCE))
        {
            String[] parts=message.split(" ");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if(pp!=null)
            {
                pp.setMessage(parts[0]);
            }
        }
    }

    public void terminate() {
        serverThread.interrupt();
        datagramSocket.close();
    }
}
