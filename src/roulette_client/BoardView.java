/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roulette_client;

import javax.swing.*;
import java.awt.event.*;

public class BoardView extends javax.swing.JFrame {

    public BoardView() {
        initComponents();
        setVisible(true);
    }

    void setCurrentBet(String betType){
        betTypeLabel.setText(betType);
    }

    String getCurrentBetInfo(){ return betTypeLabel.getText(); }

    String getCurrentBetAmount(){ return bettingAmount.getText();}

    void addJoinButtonListener(ActionListener listenerForJoinButton){
        joinButton.addActionListener(listenerForJoinButton);
    }

    void addQuitButtonListener(ActionListener listenerForQuitButton){
        quitButton.addActionListener(listenerForQuitButton);
    }

    void addBoardListener(MouseAdapter listenerForBoard){
        board.addMouseListener(listenerForBoard);
    }

    void addBetButtonListener(ActionListener listenerForBetButton){
        betButton.addActionListener(listenerForBetButton);
    }

    void updateMessageDisplay(String message){
        croupierInfo.setText(message);
    }

    void updateBalance(String balance){
        moneyDisplayLabel.setText(balance);
    }

    void updateBetList(String betList){
        currentBets.setText(betList);
    }

    void inputNickname(){
        nickname.setText(JOptionPane.showInputDialog("Nickname: "));
    }

    String getNickname(){
        return nickname.getText();
    }

    private void initComponents() {

        centralPanel = new javax.swing.JPanel();
        serverMessageLabel = new javax.swing.JLabel();
        croupierInfo = new javax.swing.JTextField();
        boardPanel = new javax.swing.JPanel();
        board = new javax.swing.JLabel();
        userPanel = new javax.swing.JPanel();
        nicknameLabel = new javax.swing.JLabel();
        nickname = new javax.swing.JLabel();
        joinButton = new javax.swing.JButton();
        stateButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        menuLabel = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        bettingPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        currentBets = new javax.swing.JTextArea();
        currentBetsLabel = new javax.swing.JLabel();
        betSettingsPanel = new javax.swing.JPanel();
        betLabel = new javax.swing.JLabel();
        betTypeLabel = new javax.swing.JLabel();
        moneyLabel = new javax.swing.JLabel();
        moneyDisplayLabel = new javax.swing.JLabel();
        bettingAmountLabel = new javax.swing.JLabel();
        bettingAmount = new javax.swing.JTextField();
        betButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(28, 112, 34));
        setResizable(false);

        centralPanel.setBackground(new java.awt.Color(28, 112, 34));

        serverMessageLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        serverMessageLabel.setText("SERVER MESSAGES");

        croupierInfo.setEditable(false);
        croupierInfo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        croupierInfo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        boardPanel.setBackground(new java.awt.Color(28, 112, 34));

        board.setIcon(new javax.swing.ImageIcon("C:\\Coding\\POOP\\roulette\\images\\BOARD.png")); // NOI18N

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
                boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(board)
                                .addGap(19, 19, 19))
        );
        boardPanelLayout.setVerticalGroup(
                boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(boardPanelLayout.createSequentialGroup()
                                .addComponent(board)
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout centralPanelLayout = new javax.swing.GroupLayout(centralPanel);
        centralPanel.setLayout(centralPanelLayout);
        centralPanelLayout.setHorizontalGroup(
                centralPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(centralPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(centralPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(croupierInfo))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centralPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(serverMessageLabel)
                                .addGap(88, 88, 88))
        );
        centralPanelLayout.setVerticalGroup(
                centralPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(centralPanelLayout.createSequentialGroup()
                                .addContainerGap(26, Short.MAX_VALUE)
                                .addComponent(serverMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(croupierInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        userPanel.setBackground(new java.awt.Color(28, 112, 34));

        nicknameLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        nicknameLabel.setText("Nickname: ");

        nickname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        nickname.setText("");

        joinButton.setText("Join Game");

        quitButton.setText("Quit Game");

        menuLabel.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        menuLabel.setText("MENU");

        exitButton.setText("Exit Game");

        stateButton.setText("State");

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(userPanelLayout.createSequentialGroup()
                                                .addComponent(nicknameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(joinButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(quitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(exitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(stateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(menuLabel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nicknameLabel)
                                        .addComponent(nickname))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(menuLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(joinButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(quitButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitButton)
                                .addContainerGap())
        );

        bettingPanel.setBackground(new java.awt.Color(28, 112, 34));

        currentBets.setEditable(false);
        currentBets.setBackground(new java.awt.Color(240, 240, 240));
        currentBets.setColumns(20);
        currentBets.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        currentBets.setRows(5);
        jScrollPane1.setViewportView(currentBets);

        currentBetsLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        currentBetsLabel.setText("CURRENT BETS");
        currentBetsLabel.setFocusable(false);
        currentBetsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        betSettingsPanel.setBackground(new java.awt.Color(28, 112, 34));

        betLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        betLabel.setText("Bet:");

        betTypeLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        betTypeLabel.setText("Manque");

        moneyLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        moneyLabel.setText("Money: ");

        moneyDisplayLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        moneyDisplayLabel.setText("1000$");

        bettingAmountLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        bettingAmountLabel.setText("Amount:");

        bettingAmount.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout betSettingsPanelLayout = new javax.swing.GroupLayout(betSettingsPanel);
        betSettingsPanel.setLayout(betSettingsPanelLayout);
        betSettingsPanelLayout.setHorizontalGroup(
                betSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(betSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                                .addComponent(betLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(betTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                                .addComponent(moneyLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moneyDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                                .addComponent(bettingAmountLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bettingAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        betSettingsPanelLayout.setVerticalGroup(
                betSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, betSettingsPanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(betSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(betLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(betTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(betSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(moneyLabel)
                                        .addComponent(moneyDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(betSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(bettingAmountLabel)
                                        .addComponent(bettingAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(45, Short.MAX_VALUE))
        );

        betButton.setText("BET");

        javax.swing.GroupLayout bettingPanelLayout = new javax.swing.GroupLayout(bettingPanel);
        bettingPanel.setLayout(bettingPanelLayout);
        bettingPanelLayout.setHorizontalGroup(
                bettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                .addGroup(bettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bettingPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                                        .addComponent(betSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(betButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(currentBetsLabel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bettingPanelLayout.setVerticalGroup(
                bettingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(currentBetsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(betSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(betButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(centralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bettingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bettingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(centralPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton betButton;
    private javax.swing.JLabel betLabel;
    private javax.swing.JPanel betSettingsPanel;
    private javax.swing.JLabel betTypeLabel;
    private javax.swing.JTextField bettingAmount;
    private javax.swing.JLabel bettingAmountLabel;
    private javax.swing.JPanel bettingPanel;
    private javax.swing.JLabel board;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JTextField croupierInfo;
    private javax.swing.JTextArea currentBets;
    private javax.swing.JLabel currentBetsLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton joinButton;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JLabel moneyDisplayLabel;
    private javax.swing.JLabel moneyLabel;
    private javax.swing.JLabel nickname;
    private javax.swing.JLabel nicknameLabel;
    private javax.swing.JButton quitButton;
    private javax.swing.JLabel serverMessageLabel;
    private javax.swing.JButton stateButton;
    private javax.swing.JPanel userPanel;
    // End of variables declaration
}
