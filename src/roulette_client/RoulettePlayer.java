package roulette_client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

        import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

        import javax.net.ssl.SSLContext;
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
    private volatile boolean connected = false;

    public RoulettePlayer(InetAddress address) throws IOException {
        client = new Client(address);
        roulettePlayerThread.start();
    }

    public void run() {
        try{
            while (!roulettePlayerThread.interrupted()) {
                synchronized (this) { while (!connected)  wait(); }
                String message = client.receive();
                processMessage(message);
            }
        } catch (IOException e){

        } catch (InterruptedException e){
            //roulettePlayerThread.interrupt();
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

    private synchronized void disconnect(){
        if (connected) connected = false;
    }

    private void terminate() {
        roulettePlayerThread.interrupt();
    }



    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        RoulettePlayer player;
        try {
            player = new RoulettePlayer(InetAddress.getByName("localhost"));
            boolean loop = true;
            while (loop) {
                System.out.println();
                System.out.println("Menu");
                System.out.println("1. Join Game;");
                System.out.println("2. Quit Game;");
                System.out.println("3. State;");
                System.out.println("4. Place bet;");
                System.out.println("0. Exit.");
                int selector = in.nextInt();
                switch (selector) {
                    case 1:
                        player.connect();
                        player.client.send(CommunicationCommands.JOIN_MESSAGE);
                        break;
                    case 2:
                        player.client.send(CommunicationCommands.QUIT_MESSAGE + " " + player.playerID);
                        break;
                    case 3:
                        player.client.send(CommunicationCommands.STATE_REQUEST + " " + player.playerID);
                        break;
                    case 4:
                        if(player.connected){
                            double amount;
                            int bet_selector;
                            System.out.println("\nBets");
                            System.out.println("1. Manque;");
                            System.out.println("2. Passe;");
                            System.out.println("3. Rouge;");
                            System.out.println("4. Noir;");
                            System.out.println("5. Pair;");
                            System.out.println("6. Impair;");
                            System.out.println("7. Single;");
                            System.out.println("8. Column;");
                            System.out.println("9. Row;");
                            System.out.println("0. Return to main menu.");
                            bet_selector = in.nextInt();

                            switch (bet_selector) {
                                case 7:
                                    System.out.print("Input a number ranging from 0 to 36 to bet on: ");
                                    int number = in.nextInt();
                                    while(number < 0 || number > 36){
                                        System.out.println("Invalid number!");
                                        number = in.nextInt();
                                    }

                                    System.out.println("Input amount: ");
                                    amount = in.nextDouble();
                                    while (amount < 0){
                                        System.out.println("Invalid amount!");
                                        amount = in.nextDouble();
                                    }
                                    player.client.send(CommunicationCommands.BET + " " + player.playerID + " " + "SINGLE" + "_" + number + " " + amount);
                                    break;
                                case 0:  default:
                                    break;
                            }
                            break;
                        }
                        else break;
                    case 0:
                        if(player.connected) player.client.send(CommunicationCommands.QUIT_MESSAGE + " " + player.playerID);
                        loop = false;
                        player.terminate();
                        player.client.datagramSocket.close();
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
