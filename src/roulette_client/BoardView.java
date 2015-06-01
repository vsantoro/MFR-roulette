/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roulette_client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardView extends JFrame {

    private JButton betButton;
    private JLabel betLabel;
    private JPanel betSettingsPanel;
    private JLabel betTypeLabel;
    private JTextField bettingAmount;
    private JLabel bettingAmountLabel;
    private JPanel bettingPanel;
    private JLabel board;
    private JPanel boardPanel;
    private JPanel centralPanel;
    private JTextField croupierInfo;
    private JTextArea currentBets;
    private JLabel currentBetsLabel;
    private JButton exitButton;
    private JScrollPane jScrollPane1;
    private JButton joinButton;
    private JLabel menuLabel;
    private JLabel moneyDisplayLabel;
    private JLabel moneyLabel;
    private JLabel nickname;
    private JLabel nicknameLabel;
    private JButton quitButton;
    private JLabel serverMessageLabel;
    private JButton stateButton;
    private JPanel userPanel;

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

    void addStateButtonListener(ActionListener listenerForStateButton){
        stateButton.addActionListener(listenerForStateButton);
    }

    void addExitButtonListener(ActionListener listenerForExitButton){
        exitButton.addActionListener(listenerForExitButton);
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

    void setNickname(String _nickname){
        nickname.setText(_nickname);
    }

    private void initComponents() {

        centralPanel = new JPanel();
        serverMessageLabel = new JLabel();
        croupierInfo = new JTextField();
        boardPanel = new JPanel();
        board = new JLabel();
        userPanel = new JPanel();
        nicknameLabel = new JLabel();
        nickname = new JLabel();
        joinButton = new JButton();
        stateButton = new JButton();
        quitButton = new JButton();
        menuLabel = new JLabel();
        exitButton = new JButton();
        bettingPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        currentBets = new JTextArea();
        currentBetsLabel = new JLabel();
        betSettingsPanel = new JPanel();
        betLabel = new JLabel();
        betTypeLabel = new JLabel();
        moneyLabel = new JLabel();
        moneyDisplayLabel = new JLabel();
        bettingAmountLabel = new JLabel();
        bettingAmount = new JTextField();
        betButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(28, 112, 34));
        setResizable(false);

        centralPanel.setBackground(new java.awt.Color(28, 112, 34));

        serverMessageLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        serverMessageLabel.setText("SERVER MESSAGES");

        croupierInfo.setEditable(false);
        croupierInfo.setFont(new java.awt.Font("Times New Roman", 0, 18));
        croupierInfo.setHorizontalAlignment(JTextField.CENTER);

        boardPanel.setBackground(new java.awt.Color(28, 112, 34));

        board.setIcon(new ImageIcon("images\\BOARD.png"));

        GroupLayout boardPanelLayout = new GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
                boardPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(board)
                                .addGap(19, 19, 19))
        );
        boardPanelLayout.setVerticalGroup(
                boardPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(boardPanelLayout.createSequentialGroup()
                                .addComponent(board)
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        GroupLayout centralPanelLayout = new GroupLayout(centralPanel);
        centralPanel.setLayout(centralPanelLayout);
        centralPanelLayout.setHorizontalGroup(
                centralPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(centralPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(centralPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(boardPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(croupierInfo))
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, centralPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(serverMessageLabel)
                                .addGap(88, 88, 88))
        );
        centralPanelLayout.setVerticalGroup(
                centralPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(centralPanelLayout.createSequentialGroup()
                                .addContainerGap(26, Short.MAX_VALUE)
                                .addComponent(serverMessageLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(croupierInfo, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(boardPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        userPanel.setBackground(new java.awt.Color(28, 112, 34));

        nicknameLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        nicknameLabel.setText("Nickname: ");

        nickname.setFont(new java.awt.Font("Times New Roman", 0, 18));
        nickname.setText("");

        joinButton.setText("Join Game");

        quitButton.setText("Quit Game");

        menuLabel.setFont(new java.awt.Font("Times New Roman", Font.ITALIC, 18));
        menuLabel.setText("MENU");

        exitButton.setText("Exit Game");

        stateButton.setText("State");

        GroupLayout userPanelLayout = new GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(userPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(userPanelLayout.createSequentialGroup()
                                                .addComponent(nicknameLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nickname, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(joinButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(quitButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(exitButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(stateButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(menuLabel)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(userPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(nicknameLabel)
                                        .addComponent(nickname))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(menuLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(joinButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(quitButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitButton)
                                .addContainerGap())
        );

        bettingPanel.setBackground(new java.awt.Color(28, 112, 34));

        currentBets.setEditable(false);
        currentBets.setBackground(new java.awt.Color(240, 240, 240));
        currentBets.setColumns(20);
        currentBets.setFont(new java.awt.Font("Times New Roman", 0, 14));
        currentBets.setRows(5);
        jScrollPane1.setViewportView(currentBets);

        currentBetsLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        currentBetsLabel.setText("CURRENT BETS");
        currentBetsLabel.setFocusable(false);
        currentBetsLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        betSettingsPanel.setBackground(new java.awt.Color(28, 112, 34));

        betLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        betLabel.setText("Bet:");

        betTypeLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        betTypeLabel.setText("Manque");

        moneyLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        moneyLabel.setText("Money: ");

        moneyDisplayLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        moneyDisplayLabel.setText("1000$");

        bettingAmountLabel.setFont(new java.awt.Font("Times New Roman", 0, 18));
        bettingAmountLabel.setText("Amount:");

        bettingAmount.setFont(new java.awt.Font("Times New Roman", 0, 18));

        GroupLayout betSettingsPanelLayout = new GroupLayout(betSettingsPanel);
        betSettingsPanel.setLayout(betSettingsPanelLayout);
        betSettingsPanelLayout.setHorizontalGroup(
                betSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(betSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                                .addComponent(betLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(betTypeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                                .addComponent(moneyLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(moneyDisplayLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(betSettingsPanelLayout.createSequentialGroup()
                                                .addComponent(bettingAmountLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bettingAmount, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        betSettingsPanelLayout.setVerticalGroup(
                betSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, betSettingsPanelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(betSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(betLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(betTypeLabel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(betSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(moneyLabel)
                                        .addComponent(moneyDisplayLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(betSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bettingAmountLabel)
                                        .addComponent(bettingAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(45, Short.MAX_VALUE))
        );

        betButton.setText("BET");

        GroupLayout bettingPanelLayout = new GroupLayout(bettingPanel);
        bettingPanel.setLayout(bettingPanelLayout);
        bettingPanelLayout.setHorizontalGroup(
                bettingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                .addGroup(bettingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, bettingPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                                        .addComponent(betSettingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(betButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(currentBetsLabel)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bettingPanelLayout.setVerticalGroup(
                bettingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(bettingPanelLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(currentBetsLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(betSettingsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(betButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(userPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(centralPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bettingPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(bettingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(centralPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
}
