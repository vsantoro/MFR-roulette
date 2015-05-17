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
        import common.CommunicationCommands;

/**
 *
 * @author POOP
 */

public class RoulettePlayer implements Runnable{
    private Thread roulettePlayerThread = new Thread(this);
    private Client client;
    private int playerID = 0;
    private volatile boolean connected = false;
    private volatile boolean playing = false;

    public RoulettePlayer(InetAddress address) throws IOException {
        client = new Client(address);
        roulettePlayerThread.start();
    }

    public void run() {
        try{
            while (!Thread.interrupted()) {
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
        else
        if(message.equals(CommunicationCommands.ACCEPT)){
            startPlaying();
            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.equals(CommunicationCommands.REJECT)){
            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.equals(CommunicationCommands.FUND)){
            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.startsWith(CommunicationCommands.WINNUMBER)){
            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.startsWith(CommunicationCommands.WIN)){
            stopPlaying();
            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.startsWith(CommunicationCommands.BALANCE)){
            System.out.println("\nSERVER: " + message);
        }
    }
    public synchronized void connect(){
        if(!connected) connected = true;
        notifyAll();
    }

    public synchronized void disconnect(){
        if (connected) connected = false;
    }

    public void terminate() {
        roulettePlayerThread.interrupt();
    }

    public Client getClient() { return client; }

    public Integer getID() { return playerID; }

    public boolean isConnected(){ return connected; }

    public void startPlaying(){ playing = true;}

    public void stopPlaying(){ playing = false;}

    public boolean isPlaying(){ return playing;}


    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        try {
            RoulettePlayer  player = new RoulettePlayer(InetAddress.getByName("localhost"));
            Menu menu = new Menu(player);
            boolean loop = true;
            while (loop){
                try{
                    menu.displayMainMenu();
                    loop = menu.handleMainInput(in.nextInt());
                } catch (InvalidIndexException e){ System.out.println(e); }
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
