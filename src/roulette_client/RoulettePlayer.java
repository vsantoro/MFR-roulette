package roulette_client;


import common.Bet;
import common.Column;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.CommunicationCommands;



public class RoulettePlayer implements Runnable{
    private ArrayList<Bet> currentBets = new ArrayList<>();

    private Controller controller;

    private Thread roulettePlayerThread = new Thread(this);
    private Client client;
    private int playerID = 0;
    private volatile boolean connected = false;
    private volatile boolean playing = false;

    public void run() {
        try{
            while (!Thread.interrupted()) {
                synchronized (this) { while (!connected)  wait(); }
                String message = client.receive();
                processMessage(message);
            }
        } catch (IOException e){

        } catch (InterruptedException e){}
    }

    public void setController(Controller _controller){
        controller = _controller;
    }

    void send(String message){
        try {
            client.send(message);
        } catch (IOException e) { }
    }

    private void processMessage(String message) throws IOException{
        if(message.startsWith(CommunicationCommands.WELCOME_MESSAGE)){
            String []parts = message.split("\\s+");
            playerID = Integer.parseInt(parts[1]);
            notify(message);
//            System.out.println("\nSERVER: " + message);
        }
        else
        if(message.equals(CommunicationCommands.PYB)){
            notify(message);
        }
        else
        if(message.equals(CommunicationCommands.RNVP)){
            notify(message);
        }
        else
        if(message.startsWith(CommunicationCommands.QUIT_RESPONSE)){
            playerID = 0;
            notify(message);
        }
        else
        if(message.equals(CommunicationCommands.ACCEPT)){
            startPlaying();
            notifyAboutBets(printBets());
            notify(message);
        }
        else
        if(message.equals(CommunicationCommands.REJECT)){
            currentBets.remove(currentBets.size() - 1);
            notify(message);
        }
        else
        if(message.equals(CommunicationCommands.FUND)){
            currentBets.remove(currentBets.size() - 1);
            notify(message);
        }
        else
        if(message.startsWith(CommunicationCommands.WINNUMBER)){
            notify(message);
        }
        else
        if(message.startsWith(CommunicationCommands.WIN)){
            stopPlaying();
            clearBets();
            notify(message);
        }
        else
        if(message.startsWith(CommunicationCommands.BALANCE)){
            notify(message);
        }
    }

    void processBet(Bet bet) {
        currentBets.add(bet);
        String code = bet.getCode();
        send(CommunicationCommands.BET + " " + playerID + " " + code);
    }

    void closeClient() {
        client.close();
    }

    String printBets(){
        String bets = "";
        for(Bet bet : currentBets){
            bets += bet.toString() + "\n";
        }
        return bets;
    }

    void clearBets(){
        currentBets.clear();
        notifyAboutBets(printBets());
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

    public void joinServer(String ipAddress, int port) throws SocketException, UnknownHostException{
        client = new Client(InetAddress.getByName(ipAddress), port);
        roulettePlayerThread.start();
    }

    public void notify(String message){
        controller.update(message);
    }

    public void notifyAboutBets(String betList){
        controller.updateBetList(betList);
    }

    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
//        try {
//            Menu menu = new Menu(player);
//            boolean loop = true;
//            while (loop){
//                try{
//                    menu.displayMainMenu();
//                    loop = menu.handleMainInput(in.nextInt());
//                } catch (InvalidIndexException e){ System.out.println(e); }
//            }
//
//        } catch (SocketException ex) {
//            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
