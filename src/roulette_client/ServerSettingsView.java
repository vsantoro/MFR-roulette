package roulette_client;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ServerSettingsView extends JFrame {

    private JPanel ServerSettingsView;
    private JButton confirmButton;
    private JTextField ipAddress;
    private JLabel ipLabel;
    private JTextField port;
    private JLabel portLabel;

    public ServerSettingsView() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        ServerSettingsView = new JPanel();
        ipLabel = new JLabel();
        portLabel = new JLabel();
        ipAddress = new JTextField();
        port = new JTextField();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ipLabel.setText("IP Address: ");

        portLabel.setText("Port: ");

        confirmButton.setText("Confirm");

        GroupLayout ServerInfoInputFrameLayout = new GroupLayout(ServerSettingsView);
        ServerSettingsView.setLayout(ServerInfoInputFrameLayout);
        ServerInfoInputFrameLayout.setHorizontalGroup(
                ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ServerInfoInputFrameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(ServerInfoInputFrameLayout.createSequentialGroup()
                                                .addGap(134, 134, 134)
                                                .addComponent(confirmButton))
                                        .addGroup(ServerInfoInputFrameLayout.createSequentialGroup()
                                                .addGroup(ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(ipLabel)
                                                        .addComponent(portLabel))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(ipAddress, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(port, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ServerInfoInputFrameLayout.setVerticalGroup(
                ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ServerInfoInputFrameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(ipLabel)
                                        .addComponent(ipAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ServerInfoInputFrameLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(portLabel)
                                        .addComponent(port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confirmButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(ServerSettingsView, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(ServerSettingsView, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    //==========================
    //Controller Methods BEGIN
    //==========================

    void addConfirmButtonListener(ActionListener listenerForConfirmButton){
        confirmButton.addActionListener(listenerForConfirmButton);
    }

    void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    public String getIpAddress(){ return ipAddress.getText(); }
    public int getPort() throws NumberFormatException { return Integer.parseInt(port.getText()); }


    //==========================
    //Controller Methods END
    //==========================
}
