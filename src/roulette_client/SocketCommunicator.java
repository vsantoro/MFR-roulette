package roulette_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;


public abstract class SocketCommunicator {
    Scanner in = new Scanner(System.in);
    protected static int SERVER_PORT = 4000;
    protected static int RCV_BUFFER_LEN = 1024;
    protected DatagramSocket datagramSocket;
    protected byte []receiveBuffer = new byte[RCV_BUFFER_LEN];
    protected DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

    public SocketCommunicator(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    public SocketCommunicator() throws SocketException {
        datagramSocket = new DatagramSocket();
    }

    void send(String message, InetAddress address, int port) throws IOException {
        byte []buffer = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer,
                buffer.length,
                address,
                port);
        datagramSocket.send(datagramPacket);
    }

    public String receive() throws IOException {
        datagramSocket.receive(receivePacket);
        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }
}

