package net.blockserver.network;

import net.blockserver.Server;

import java.net.*;

public class UDPSocket
{
    public boolean conected;

    private String ip;
    private int port;
    private byte[] buffer = new byte[65535];
    private DatagramSocket sck;
    private Server server;

    public UDPSocket(Server server)
    {
        this.server = server;
        this.ip = "0.0.0.0";
        this.port = 19132;
        this.Connect();
    }

    public UDPSocket(Server server, String ip)
    {
        this.server = server;
        this.ip = ip;
        this.port = 19132;

        this.Connect();
    }

    public UDPSocket(Server server, String ip, int port)
    {
        this.server = server;
        this.ip = ip;
        this.port = port;

        this.Connect();
    }

    public synchronized void Connect()
    {
        if (!this.conected)
        {

            try
            {
                this.sck = new DatagramSocket(null);
                this.sck.setBroadcast(true);
                this.sck.setSendBufferSize(65535);
                this.sck.setReceiveBufferSize(65535);
                this.sck.bind(new InetSocketAddress(this.ip, this.port));
                this.conected = true;
                sck.setSoTimeout(0);
            }
            catch (SocketException e) {
                this.conected = false;
                this.server.getLogger().fatal(e.getMessage());
                this.server.getLogger().fatal("", e.getStackTrace());
            }
        }
        else
        {
            this.server.getLogger().error("The socket is already created.");
        }

    }

    public int SendTO(byte[] buffer, String ip, int port)
    {
        try
        {
            this.sck.send(new DatagramPacket(buffer, buffer.length, new InetSocketAddress(ip, port)));
            return buffer.length;
        }
        catch (Exception e)
        {
            this.server.getLogger().fatal(e.getMessage());
            this.server.getLogger().fatal("", e.getStackTrace());
        }
        return -1;
    }

    public int SendTO(byte[] buffer, InetSocketAddress address)
    {
        try
        {
            this.sck.send(new DatagramPacket(buffer, buffer.length, address));
            return buffer.length;
        }
        catch (Exception e)
        {
            this.server.getLogger().fatal(e.getMessage());
            this.server.getLogger().fatal("", e.getStackTrace());
        }
        return -1;
    }

    public DatagramPacket Receive()
    {
        try
        {
            this.sck.setSoTimeout(1000);
            DatagramPacket pck = new DatagramPacket(new byte[65535], 65535);
            sck.receive(pck);
            this.sck.setSoTimeout(0);
            return pck;
        }
        catch(Exception e)
        {
            this.server.getLogger().fatal(e.getMessage());
            this.server.getLogger().fatal("", e.getStackTrace());
            return null;
        }
    }

    public void Close()
    {
        if (this.conected)
        {
            this.conected = false;
            this.sck.close();
        }
    }

}
