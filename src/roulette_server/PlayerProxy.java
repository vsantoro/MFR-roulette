package roulette_server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

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
