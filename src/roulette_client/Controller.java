package roulette_client;

import common.Bet;
import common.Bets;
import common.CommunicationCommands;

import java.awt.*;
import java.awt.event.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Controller {

    private RoulettePlayer roulettePlayerModel;
    private ServerSettingsView serverView;
    private BoardView boardView;
    private String currentBet;

    private static ManqueBettingField manqueBettingField;


    //==================================
    //Betting table helper classes BEGIN
    //==================================

    private abstract class BettingField {
        Rectangle area;

        BettingField(Rectangle _area) {
            area = _area;
        }

        public boolean contains(Point point) {
            return area.contains(point);
        }

        public abstract void setBettingPanel();
    }

    private class ManqueBettingField extends BettingField {
        public ManqueBettingField(Rectangle area) {
            super(area);
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Manque");
        }
    }

    private class PasseBettingField extends BettingField {
        public PasseBettingField(Rectangle area) {
            super(area);
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Passe");
        }
    }

    private class PairBettingField extends BettingField {

        public PairBettingField(Rectangle area) {
            super(area);
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Pair");
        }
    }

    private class ImpairBettingField extends BettingField {
        public ImpairBettingField(Rectangle area) {
            super(area);
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Impair");
        }
    }

    private class RougeBettingField extends BettingField {
        public RougeBettingField(Rectangle area) {
            super(area);
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Rouge");
        }
    }

    private class NoirBettingField extends BettingField {
        public NoirBettingField(Rectangle area) {
            super(area);
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Noir");
        }
    }

    private class SingleBettingField extends BettingField {
        int number;

        public SingleBettingField(Rectangle area, int _number) {
            super(area);
            number = _number;
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Single " + number);
        }
    }

    private class RowBettingField extends BettingField {
        int row;

        public RowBettingField(Rectangle area, int _row) {
            super(area);
            row = _row;
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Row " + row);
        }
    }

    private class ColumnBettingField extends BettingField {
        int column;

        public ColumnBettingField(Rectangle area, int _column) {
            super(area);
            column = _column;
        }

        @Override
        public void setBettingPanel() {
            boardView.setCurrentBet("Column " + column);
        }
    }

    private final ArrayList<BettingField> PixelGrid;

    {
        PixelGrid = new ArrayList<>();

        //FirstColumn

        PixelGrid.add(new ManqueBettingField(new Rectangle(14, 94, 50, 92)));
        PixelGrid.add(new PairBettingField(new Rectangle(14, 189, 50, 89)));
        PixelGrid.add(new RougeBettingField(new Rectangle(14, 282, 50, 88)));
        PixelGrid.add(new NoirBettingField(new Rectangle(14, 374, 50, 88)));
        PixelGrid.add(new ImpairBettingField(new Rectangle(14, 466, 50, 88)));
        PixelGrid.add(new PasseBettingField(new Rectangle(14, 558, 50, 88)));

        //Second Column

        PixelGrid.add(new RowBettingField(new Rectangle(68, 94, 41, 42), 1));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 144, 41, 42), 2));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 190, 41, 42), 3));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 236, 41, 42), 4));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 282, 41, 42), 5));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 328, 55, 42), 6));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 374, 41, 42), 7));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 420, 41, 42), 8));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 466, 41, 42), 9));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 512, 41, 42), 10));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 558, 41, 42), 11));
        PixelGrid.add(new RowBettingField(new Rectangle(68, 604, 41, 42), 12));

        //Third Column

        PixelGrid.add(new SingleBettingField(new Rectangle(112, 94, 55, 42), 1));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 144, 55, 42), 4));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 190, 55, 42), 7));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 236, 55, 42), 10));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 282, 55, 42), 13));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 328, 55, 42), 16));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 374, 55, 42), 19));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 420, 55, 42), 22));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 466, 55, 42), 25));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 512, 55, 42), 28));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 558, 55, 42), 31));
        PixelGrid.add(new SingleBettingField(new Rectangle(112, 604, 55, 42), 34));
        PixelGrid.add(new ColumnBettingField(new Rectangle(112, 650, 55, 42), 1));

        //Fourth Column

        PixelGrid.add(new SingleBettingField(new Rectangle(170, 94, 53, 42), 2));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 144, 53, 42), 5));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 190, 53, 42), 8));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 236, 53, 42), 11));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 282, 53, 42), 14));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 328, 53, 42), 17));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 374, 53, 42), 20));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 420, 53, 42), 23));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 466, 53, 42), 26));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 512, 53, 42), 29));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 558, 53, 42), 32));
        PixelGrid.add(new SingleBettingField(new Rectangle(170, 604, 53, 42), 35));
        PixelGrid.add(new ColumnBettingField(new Rectangle(170, 650, 53, 42), 2));

        //Fifth Column

        PixelGrid.add(new SingleBettingField(new Rectangle(227, 94, 55, 42), 3));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 144, 55, 42), 6));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 190, 55, 42), 9));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 236, 55, 42), 12));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 282, 55, 42), 15));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 328, 55, 42), 18));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 374, 55, 42), 21));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 420, 55, 42), 24));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 466, 55, 42), 27));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 512, 55, 42), 30));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 558, 55, 42), 33));
        PixelGrid.add(new SingleBettingField(new Rectangle(227, 604, 55, 42), 36));
        PixelGrid.add(new ColumnBettingField(new Rectangle(227, 650, 55, 42), 3));
    }

    //================================
    //Betting table helper classes END
    //================================




    public Controller(RoulettePlayer _roulettePlayerModel, ServerSettingsView _serverView){
        roulettePlayerModel = _roulettePlayerModel;
        serverView = _serverView;

        serverView.addConfirmButtonListener(new ConfirmButtonListener());
        roulettePlayerModel.setController(this);
    }

    public void update(String message){
        if(message.startsWith(CommunicationCommands.WELCOME_MESSAGE)){
            String parts[] = message.split(" ");
            if(parts.length == 4){
                boardView.updateMessageDisplay(message);
                boardView.setNickname(parts[1]);
                roulettePlayerModel.setID(Integer.parseInt(parts[2]));
                boardView.updateBalance(parts[3]);
            }

        }
        else if(message.startsWith(CommunicationCommands.QUIT_RESPONSE)){
            boardView.updateMessageDisplay(message);
            roulettePlayerModel.disconnect();
        }
        else if(message.equals(CommunicationCommands.PYB)){
            boardView.updateMessageDisplay("PLACE YOUR BETS!");
        }
        else if (message.equals(CommunicationCommands.RNVP)){
            boardView.updateMessageDisplay("RNVP");
        }
        else if(message.equals(CommunicationCommands.ACCEPT)){
            boardView.updateMessageDisplay("BET ACCEPTED");
            roulettePlayerModel.send(CommunicationCommands.BALANCE + " " + roulettePlayerModel.getID());
        }
        else if(message.startsWith(CommunicationCommands.BALANCE)){
            updateBalance(message);
        }
        else if(message.equals(CommunicationCommands.REJECT)){
            boardView.updateMessageDisplay("REJECT");
        }
        else if(message.equals(CommunicationCommands.FUND)){
            boardView.updateMessageDisplay("FUND");
        }
        else if(message.startsWith(CommunicationCommands.WINNUMBER)){
            boardView.updateMessageDisplay(message);
            roulettePlayerModel.send(CommunicationCommands.BALANCE + " " + roulettePlayerModel.getID());
        }
        else if(message.startsWith(CommunicationCommands.WIN)){
            boardView.updateMessageDisplay(message);
        }
        else if(message.equals(CommunicationCommands.BUSY)){
            boardView.updateMessageDisplay(message);
        }
    }

    public void updateBetList(String betList){
        boardView.updateBetList(betList);
    }

    //=====================
    // SERVER SETTINGS VIEW
    //=====================

    private class ConfirmButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String ipAddress;
            int port;

            try {
                ipAddress = serverView.getIpAddress();
                port = serverView.getPort();
                roulettePlayerModel.joinServer(ipAddress, port);
                serverView.dispose();
                boardView = new BoardView();
                boardView.addJoinButtonListener(new JoinButtonListener());
                boardView.addStateButtonListener(new StateButtonListener());
                boardView.addQuitButtonListener(new QuitButtonListener());
                boardView.addExitButtonListener(new ExitButtonListener());
                boardView.addBoardListener(new BoardListener());
                boardView.addBetButtonListener(new BetButtonListener());


            } catch (NumberFormatException ex){
                serverView.displayErrorMessage("Niste uneli broj!" );
            } catch (SocketException ex) {
                serverView.displayErrorMessage("Port je zauzet!");
            } catch (UnknownHostException ex) {
                serverView.displayErrorMessage("Ne postoji takva ip adresa!");
            }
        }
    }

    //===========
    // BOARD VIEW
    //===========

    private void updateBalance(String message){
        String parts[] = message.split(" ");
        boardView.updateBalance(parts[1]);
    }

    private class JoinButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.inputNickname();
            roulettePlayerModel.send(CommunicationCommands.JOIN_MESSAGE + " " + boardView.getNickname());
            roulettePlayerModel.connect();
        }
    }

    private class QuitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!roulettePlayerModel.isPlaying()) roulettePlayerModel.send(CommunicationCommands.QUIT_MESSAGE + " " + roulettePlayerModel.getID());
        }
    }

    private class StateButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            roulettePlayerModel.send(CommunicationCommands.STATE_REQUEST + " " + roulettePlayerModel.getID());
        }
    }

    private class ExitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.dispose();
            System.exit(0);
        }
    }

    private class BoardListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            Point point = new Point(e.getX(), e.getY());
            for(BettingField field : PixelGrid){
                if(field.contains(point)){
                    field.setBettingPanel();
                }
            }
        }
    }

    private class BetButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String betType = boardView.getCurrentBetInfo();
            String parts[] = betType.split(" ");
            String betInfo;

            if(parts.length == 1){
                 betInfo = parts[0].toUpperCase() + " " + boardView.getCurrentBetAmount();
            }
            else{
                betInfo = parts[0].toUpperCase() + "_" + parts[1] + " " + boardView.getCurrentBetAmount();
            }
            Bet newBet = Bets.decodeBet(betInfo);

            roulettePlayerModel.processBet(newBet);
        }
    }
}
