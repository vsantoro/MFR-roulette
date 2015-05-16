package roulette_client; /**
 * Created by Dragan Obradovic on 15-May-15.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author POOP
 */
public class Client extends SocketCommunicator
{
    public  InetAddress serverAddress;
    public Client(InetAddress _serverAddress) throws SocketException {
        serverAddress = _serverAddress;
    }

    public void send(String message) throws IOException {
        send(message, serverAddress, SERVER_PORT);
    }

}
