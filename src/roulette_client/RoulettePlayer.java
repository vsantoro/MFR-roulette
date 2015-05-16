package roulette_client; /**
 * Created by Dragan Obradovic on 15-May-15.
 */
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
    public int playerID = 0;

    public RoulettePlayer(InetAddress address) throws SocketException, IOException {
        client = new Client(address);
        roulettePlayerThread.start();
    }

    public void run() {
        while (!roulettePlayerThread.interrupted()) {
            try{
                String message = client.receive();
                System.out.println("New Message Received!");
                processMessage(message);
            } catch (IOException e){ }
        }
    }

    private void processMessage(String message) throws IOException{
        if(message.startsWith("bajaga")){
            String msg = message;
            String []parts = message.split("\\s+");
            playerID = Integer.parseInt(parts[1]);
            System.out.println(msg);
        }
        else
        if(message.equals(CommunicationCommands.PYB)){
            System.out.println("PYB");
        }
        else
        if(message.equals(CommunicationCommands.RNVP)){
            System.out.println("RNVP");
        }
        if(message.startsWith(CommunicationCommands.QUIT_RESPONSE)){
            System.out.println(message);
            terminate();
        }
    }

    private void terminate() {
        roulettePlayerThread.interrupt();
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        RoulettePlayer player;
        try {
            System.out.println("Server ip address: ");
            //player = new RoulettePlayer(InetAddress.getByName(in.next()));
            player = new RoulettePlayer(InetAddress.getByName("localhost"));
            boolean loop = true;
            while (loop == true) {
                System.out.println();
                System.out.println("Vas id je: " + player.playerID);
                System.out.println("Menu");
                System.out.println("1. Join Game;");
                System.out.println("2. Quit Game;");
                String selector = in.next();
                switch (selector) {
                    case "join":
                        player.client.send("Ojsa");
                        break;
                    case "quit":
                        player.client.send(CommunicationCommands.QUIT_MESSAGE + " " + player.playerID);
                        System.out.println(player.roulettePlayerThread.isAlive());
                        player.terminate();
                        System.out.println(player.roulettePlayerThread.isAlive());
                        player.client.datagramSocket.close();
                        loop = false;
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
    }
}
