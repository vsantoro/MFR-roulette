package roulette_client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

        import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

        import java.io.IOException;
        import java.net.InetAddress;
        import java.net.SocketException;
        import java.net.UnknownHostException;
        import java.util.Scanner;
        import java.util.logging.Level;
        import java.util.logging.Logger;

/**
 *
 * @author POOP
 */

public class RoulettePlayer implements Runnable{
    private Thread roulettePlayerThread = new Thread(this);
    private Client client;
    private int playerID = 0;
    private boolean connected = false;

    public RoulettePlayer(InetAddress address) throws SocketException, IOException {
        client = new Client(address);
        roulettePlayerThread.start();
    }

    public void run() {
        while (!Thread.interrupted()) {
            try{

                synchronized (this){
                    while (!connected) { wait();}
                }

                String message = client.receive();
                processMessage(message);
            } catch (IOException e){

            } catch (InterruptedException e){ }

        }
    }

    private void processMessage(String message) throws IOException{
        if(message.startsWith(CommunicationCommands.WELCOME_MESSAGE)){
            String []parts = message.split("\\s+");
            playerID = Integer.parseInt(parts[1]);

            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.equals(CommunicationCommands.PYB)){
            System.out.println("\nSERVER: " + "PYB");
        }
        else
        if(message.equals(CommunicationCommands.RNVP)){
            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.startsWith(CommunicationCommands.QUIT_RESPONSE)){
            System.out.println("\nSERVER: " + message);
            disconnect();
            playerID = 0;
        }
    }

    private synchronized void connect(){
        if(!connected) connected = true;
        notifyAll();
    }

    private void terminate() {
        roulettePlayerThread.interrupt();
    }

    private synchronized void disconnect(){
        if (connected) connected = false;
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        RoulettePlayer player = null;
        try {
            System.out.println("Server ip address: ");
            //player = new RoulettePlayer(InetAddress.getByName(in.next()));
            player = new RoulettePlayer(InetAddress.getByName("localhost"));
            boolean loop = true;
            while (loop) {
                System.out.println();
                System.out.println("Menu");
                System.out.println("1. Join Game;");
                System.out.println("2. Quit Game;");
                System.out.println("3.State");
                String selector = in.next();
                switch (selector) {
                    case "join":
                        player.connect();
                        player.client.send(CommunicationCommands.JOIN_MESSAGE);
                        break;
                    case "quit":
                        player.client.send(CommunicationCommands.QUIT_MESSAGE + " " + player.playerID);
                        break;
                    case "state":
                        player.client.send(CommunicationCommands.STATE_REQUEST + " " + player.playerID);
                        break;
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(player != null){
            player.terminate();
            player.client.datagramSocket.close();
        }
    }
}
