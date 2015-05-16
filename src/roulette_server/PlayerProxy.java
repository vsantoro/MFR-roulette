/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roulette_server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author POOP
 */
public class PlayerProxy
{
    private InetAddress clientAddress;
    private int clientPort;
    private String receivedMessage;
    private SocketCommunicator outputCommunicator;
    
    
    public PlayerProxy(SocketCommunicator _outputCommunicator, 
                       InetAddress _clientAddress, 
                       int _clientPort)
    {
        outputCommunicator = _outputCommunicator;
        clientAddress = _clientAddress;
        clientPort = _clientPort;
    }
    
    
    // Metoda koju poziva server da bi naznacio da je stigla poruka bas za
    // ovog igraca.
    synchronized void receivedMessage(String msg)
    {
        receivedMessage = msg;
        notify();
    }
    
    public void send(String message) throws IOException
    {
        outputCommunicator.send(message, clientAddress, clientPort);
    }
        
    public synchronized String receive() throws InterruptedException
    {
        while( receivedMessage == null )
            wait();
        
        String retMessage = receivedMessage;
        receivedMessage = null;

        return retMessage;
    }
}
