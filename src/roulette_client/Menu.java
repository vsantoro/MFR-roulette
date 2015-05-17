package roulette_client;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import common.CommunicationCommands;

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
            switch (parts[1]) {
                case "Join Game;":
                    if(player.getID() == 0){
                        player.connect();
                        player.getClient().send(CommunicationCommands.JOIN_MESSAGE);
                        return true;
                    }
                    return true;

                case "Quit Game;":
                    if(!player.isPlaying()) player.getClient().send(CommunicationCommands.QUIT_MESSAGE + " " + player.getID());
                    else System.out.println("You are still in game.");
                    return true;

                case "State;":
                    if(player.getID() != 0) player.getClient().send(CommunicationCommands.STATE_REQUEST + " " + player.getID());
                    return true;

                case "Place Bet;":
                    displayBetsMenu();
                    int _index = in.nextInt();
                    handleBetsInput(_index);
                    return true;

                case "Exit.":
                    if(player.isConnected()) player.getClient().send(CommunicationCommands.QUIT_MESSAGE + " " + player.getID());
                    player.terminate();
                    player.getClient().datagramSocket.close();
                    return false;

                default:
                    return true;
            }
        } catch (IOException ex){
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void handleBetsInput(int index) throws InvalidIndexException {
        if(0 > index || index > betsMenu.size()) throw new InvalidIndexException();
        double amount;
        String []parts = (betsMenu.get(index - 1)).split(" ", 2);
        try{
            switch (parts[1]){
                case "Manque;":
                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "MANQUE" + " " + amount);
                    break;

                case "Passe;":
                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "PASSE" + " " + amount);
                    break;

                case "Rouge;":
                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "ROUGE" + " " + amount);
                    break;

                case "Noir;":
                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "NOIR" + " " + amount);
                    break;

                case "Pair;":
                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "PAIR" + " " + amount);
                    break;

                case "Impair;":
                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "IMPAIR" + " " + amount);
                    break;

                case "Single;":
                    System.out.print("Which number from 0 to 36 would you like to bet on: ");
                    int number = in.nextInt();
                    while(number < 0 || number > 36){
                        System.out.print("Invalid number!\nWhich number from 0 to 36 would you like to bet on: ");
                        number = in.nextInt();
                    }

                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }

                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "SINGLE" + "_" + number + " " + amount);
                    break;

                case "Column;":
                    System.out.print("Which column (1st, 2nd or 3rd) would you like to bet on: ");
                    number = in.nextInt();
                    while(number < 1 || number > 3){
                        System.out.print("Invalid number!\nWhich column (1st, 2nd or 3rd) would you like to bet on: ");
                        number = in.nextInt();
                    }

                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "COLUMN" + "_" + number + " " + amount);
                    break;

                case "Row;":
                    System.out.print("Which of 12 rows would you like to bet on: ");
                    number = in.nextInt();
                    while(number < 1 || number > 12){
                        System.out.print("Invalid number!\nWhich of 12 rows would you like to bet on: ");
                        number = in.nextInt();
                    }

                    System.out.print("Input amount: ");
                    amount = in.nextDouble();
                    while (amount < 0){
                        System.out.print("Invalid amount!\nInput amount:");
                        amount = in.nextDouble();
                    }
                    player.getClient().send(CommunicationCommands.BET + " " + player.getID() + " " + "ROW" + "_" + number + " " + amount);
                    break;
                case "Return to main menu.": default:
                    break;
            }
        } catch (IOException ex){
            Logger.getLogger(RoulettePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (InputMismatchException ex){
            System.out.println("Niste uneli broj!");
        }
    }
}
