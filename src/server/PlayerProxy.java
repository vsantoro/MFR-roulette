package server;

import java.io.IOException;
import java.net.InetAddress;

/*
Contains client address and port information. Used for sending response messages
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
        //assigns outputCommunicator that will be used for sending messages to clients
        outputCommunicator = _outputCommunicator;

        //gets clients ip address
        clientAddress = _clientAddress;

        //gets clients port
        clientPort = _clientPort;
    }

    //sets recieved message
    synchronized void setMessage(String msg)
    {
        receivedMessage = msg;
        notify();
    }

    //sends message to client at specified address and port
    public void send(String message) throws IOException
    {
        outputCommunicator.send(message, clientAddress, clientPort);
    }


    public synchronized String receive() throws InterruptedException
    {
        while( receivedMessage == null )
            wait();
        
        String message = receivedMessage;
        receivedMessage = null;

        return message;
    }
}
