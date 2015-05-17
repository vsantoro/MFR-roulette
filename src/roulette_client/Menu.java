package roulette_client;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;

class InvalidIndexException extends Exception{
    public String toString() {
        return "Invalid index!";
    }
}

public class Menu {
    Scanner in = new Scanner(System.in);
    private ArrayList<String> mainMenu = new ArrayList<>();
    private ArrayList<String> betsMenu = new ArrayList<>();
    private RoulettePlayer player;

    public Menu(RoulettePlayer _player) {
        player = _player;

        mainMenu.add(mainMenu.size() + 1 + ". " + "Join Game;");
        mainMenu.add(mainMenu.size() + 1 + ". " + "Quit Game;");
        mainMenu.add(mainMenu.size() + 1 + ". " + "State;");
        mainMenu.add(mainMenu.size() + 1 + ". " + "Place Bet;");
        mainMenu.add(mainMenu.size() + 1 + ". " + "Exit.");

        betsMenu.add(betsMenu.size() + 1 + ". " + "Manque;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Passe;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Rouge;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Noir;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Pair;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Impair;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Single;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Column;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Row;");
        betsMenu.add(betsMenu.size() + 1 + ". " + "Return to main menu.");
    }

    public void displayMainMenu() {
        for (String item : mainMenu) {
            System.out.println(item);
        }
    }

    public void displayBetsMenu() {
        for (String bet : betsMenu) {
            System.out.println(bet);
        }
    }

    public void addItem(String item){
        mainMenu.add(mainMenu.size() + ". " + item);
    }

    public void addBet(String bet) {
        betsMenu.add(betsMenu.size() + ". " + bet);
    }

    public boolean handleMainInput(int index) throws InvalidIndexException{
        if(0 > index || index > mainMenu.size()) throw new InvalidIndexException();
        String []parts = (mainMenu.get(index - 1)).split(" ", 2);
        try{
            if(parts[1].equals("Join Game;")){
                if(player.getID() == 0){
                    player.connect();
                    player.getClient().send(CommunicationCommands.JOIN_MESSAGE);
                    return true;
                }
                return true;
            }
            else
            if(parts[1].equals("Quit Game;")){
                player.getClient().send(CommunicationCommands.QUIT_MESSAGE + " " + player.getID());
                return true;
            }
            else
            if(parts[1].equals("State;")){
                player.getClient().send(CommunicationCommands.STATE_REQUEST + " " + player.getID());
                return true;
            }
            else
            if(parts[1].equals("Place Bet;")){
                displayBetsMenu();
                int _index = in.nextInt();
                handleBetsInput(_index);
                return true;
            }
            else
            if(parts[1].equals("Exit.")){
                if(player.isConnected()) player.getClient().send(CommunicationCommands.QUIT_MESSAGE + " " + player.getID());
                player.terminate();
                player.getClient().datagramSocket.close();
                return false;
            }
        } catch (IOException ex){
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void handleBetsInput(int index) throws InvalidIndexException {
        if(0 > index || index > betsMenu.size()) throw new InvalidIndexException();
        String []parts = (betsMenu.get(index - 1)).split(" ", 2);
        try{
            if(parts[1].equals("Manque;")){}
            else
            if(parts[1].equals("Passe;")){}
            else
            if(parts[1].equals("Rouge;")){}
            else
            if(parts[1].equals("Noir;")){}
            else
            if(parts[1].equals("Impair;")){}
            else
            if(parts[1].equals("Single;")){
                System.out.print("Input a number ranging from 0 to 36 to bet on: ");
                int number = in.nextInt();
                while(number < 0 || number > 36){
                    System.out.println("Invalid number!");
                    number = in.nextInt();
                }

                System.out.println("Input amount: ");
                double amount = in.nextDouble();
                while (amount < 0){
                    System.out.println("Invalid amount!");
                    amount = in.nextDouble();
                }
                player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "SINGLE" + "_" + number + " " + amount);
            }
            if(parts[1].equals("Column;")){}
            if(parts[1].equals("Row;")){}
            if(parts[1].equals("Return to main menu;")){

            }

        } catch (IOException ex){
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
