package client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;


public class Client extends SocketCommunicator
{
    public  InetAddress serverAddress;
    public Client(InetAddress _serverAddress, int _port) throws SocketException {
        //For 2 different computers
        //super(_port);
        serverAddress = _serverAddress;
    }

    public void send(String message) throws IOException {
        send(message, serverAddress, SERVER_PORT);
    }

    void close(){
        datagramSocket.close();
    }

}
