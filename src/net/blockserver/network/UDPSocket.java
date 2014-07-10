package net.blockserver.network;

import net.blockserver.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;

public class UDPSocket
{
    public boolean connected;

    private String ip;
    private int port;
    private byte[] buffer = new byte[65535];
    private DatagramSocket sck;
    private Server server;

    public UDPSocket(Server server)  throws SocketException
    {
        this.server = server;
        this.ip = "0.0.0.0";
        this.port = 19132;
        this.Connect();
    }

    public UDPSocket(Server server, String ip)  throws SocketException
    {
        this.server = server;
        this.ip = ip;
        this.port = 19132;

        this.Connect();
    }

    public UDPSocket(Server server, String ip, int port) throws SocketException
    {
        this.server = server;
        this.ip = ip;
        this.port = port;

        this.Connect();
    }

    public synchronized void Connect() throws SocketException
    {
        if (!this.connected)
        {
                this.sck = new DatagramSocket(null);
                this.sck.setBroadcast(true);
                this.sck.setSendBufferSize(65535);
                this.sck.setReceiveBufferSize(65535);
                this.sck.bind(new InetSocketAddress(this.ip, this.port));
                this.connected = true;
                sck.setSoTimeout(0);

        }
        else
        {
            this.server.getLogger().error("The socket is already created.");
        }

    }

    public int SendTO(byte[] buffer, String ip, int port) throws IOException
    {
        this.sck.send(new DatagramPacket(buffer, buffer.length, new InetSocketAddress(ip, port)));
        return buffer.length;
    }

    public int SendTO(byte[] buffer, InetSocketAddress address) throws  IOException
    {
            this.sck.send(new DatagramPacket(buffer, buffer.length, address));
            return buffer.length;
    }

    public int Send(DatagramPacket pck) throws  IOException
    {
        this.sck.send(pck);
        return pck.getData().length;
    }

    public DatagramPacket Receive() throws SocketException, IOException
    {
        byte[] buffer = new byte[1536];
        DatagramPacket pck = new DatagramPacket(buffer, 1536);
        sck.receive(pck);
        pck.setData(Arrays.copyOf(buffer, pck.getLength()));
        return pck;
    }

    public void Close()
    {
        if (this.connected)
        {
            this.connected = false;
            this.sck.close();
        }
    }

}
