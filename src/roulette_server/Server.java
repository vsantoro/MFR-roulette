package roulette_server;

import java.io.IOException;
import java.net.SocketException;
import java.util.Hashtable;

import common.CommunicationCommands;
import roulette_server.models.Game;


public class Server extends SocketCommunicator implements Runnable {
    private Hashtable<Integer, PlayerProxy> connectedPlayers = new Hashtable<>();
    private Thread serverThread = new Thread(this);
    private int clientID;
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
                String message = receive();
                System.out.println("Server received: " + message);
                processMessage(message);
            }
            catch(IOException e) {  }
        }
        System.out.println("... game server ended.");
    }

    private void processMessage(String message) throws IOException {

        if( message.startsWith(CommunicationCommands.JOIN_MESSAGE) ) {
            PlayerProxy pp = new PlayerProxy(this, receivePacket.getAddress(), receivePacket.getPort());
            if(game.isTableFull()==true)
            {
                pp.send(CommunicationCommands.BUSY);
            }
            else
            {
                String[] parts=message.split(" ");
                clientID++;
                connectedPlayers.put(clientID, pp);
                String name=parts[1];
                if(game.isNameOccupied(name))
                    name="USER_" + clientID;

                double playerStartMoney = game.newPlayer(pp,name);
                pp.send(CommunicationCommands.WELCOME_MESSAGE + " " + name + " " + clientID + " " +  game.getStartingMoney());
            }
        }
        else
        if( message.startsWith(CommunicationCommands.QUIT_MESSAGE) ) {
            String []parts = message.split("\\s+");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if( pp != null ) {
                pp.receivedMessage(parts[0]);
                pp.send(CommunicationCommands.QUIT_RESPONSE);
                connectedPlayers.remove(id);
            }
        }
        else
        if (message.startsWith(CommunicationCommands.STATE_REQUEST))
		{
        	String[] parts=message.split("\\s+");
            Integer id=Integer.parseInt(parts[1]);
            PlayerProxy pp= connectedPlayers.get(id);
            if( pp != null )
            {
                if(game.isAcceptingBets())
                    pp.send(CommunicationCommands.PYB);
                else
                    pp.send(CommunicationCommands.RNVP);
            }
		}
        else
        if(message.startsWith(CommunicationCommands.BET)){
            String[] parts = message.split("\\s+");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if (pp != null) {
                pp.receivedMessage(parts[0] + " " +  parts[2] + " " + parts[3]);
            }

        }
        else
        if(message.startsWith(CommunicationCommands.BALANCE))
        {
            String[] parts=message.split(" ");
            Integer id = Integer.parseInt(parts[1]);
            PlayerProxy pp = connectedPlayers.get(id);
            if(pp!=null)
            {
                pp.receivedMessage(parts[0]);
            }
        }
    }

    public void terminate() {
        serverThread.interrupt();
        datagramSocket.close();
    }
}
