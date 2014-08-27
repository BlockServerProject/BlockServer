package org.blockserver.player;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.Entity;
import org.blockserver.entity.EntityType;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.network.minecraft.ClientConnectPacket;
import org.blockserver.network.minecraft.ClientHandShakePacket;
import org.blockserver.network.minecraft.Disconnect;
import org.blockserver.network.minecraft.LoginPacket;
import org.blockserver.network.minecraft.LoginStatusPacket;
import org.blockserver.network.minecraft.MessagePacket;
import org.blockserver.network.minecraft.PacketsID;
import org.blockserver.network.minecraft.PingPacket;
import org.blockserver.network.minecraft.PongPacket;
import org.blockserver.network.minecraft.ServerHandshakePacket;
import org.blockserver.network.raknet.ACKPacket;
import org.blockserver.network.raknet.AcknowledgePacket;
import org.blockserver.network.raknet.CustomPacket;
import org.blockserver.network.raknet.InternalPacket;
import org.blockserver.network.raknet.NACKPacket;
import org.blockserver.scheduler.CallBackTask;

public class Player// extends Entity
{
    private String name;
    private String ip;

    private int maxHealth;

    private int lastSequenceNum;
    private int SequenceNum;
    private int messageIndex;

    private long clientID; // Client ID From MCPE Client

    private int port;
    private short mtuSize; // Maximum Transport Unit Size

    private Server server;
    private CustomPacket Queue;
    private List<Integer> ACKQueue; // Received Packet Queue
    private List<Integer> NACKQueue; // Not received packet queue
    private Map<Integer, CustomPacket> recoveryQueue; // Packet sent queue to be used if not received

    public String getIP()
    {
        return this.ip;
    }

    public Player(String ip, int port, short mtu, long clientID)
    {
        //super(0, 0, 0, null);
        this.ip = ip.replace("/", "");
        this.port = port;
        mtuSize = mtu;
        this.clientID = clientID;

        lastSequenceNum = SequenceNum = messageIndex = 0;

        server = Server.getInstance();
        Queue = new CustomPacket();
        ACKQueue = new ArrayList<Integer>();
        NACKQueue = new ArrayList<Integer>();
        recoveryQueue = new HashMap<Integer, CustomPacket>();

        try
        {
            this.server.getScheduler().addTask(new CallBackTask(this, "update", 10, true));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void login(){
        server.getPlayerDatabase().load(getIName());
    }

    public void update(int ticks)
    {
        if(this.ACKQueue.size() > 0)
        {
            int[] array = new int[this.ACKQueue.size()];
            for (int i = 0; i < this.ACKQueue.size(); i++)
            {
                array[i] = this.ACKQueue.get(i);
            }

            ACKPacket pck = new ACKPacket(array);
            pck.encode();

            this.server.sendPacket(pck.getBuffer().array(), this.ip, this.port);
        }

        if(this.NACKQueue.size() > 0)
        {
            int[] array = new int[this.NACKQueue.size()];
            for (int i = 0; i < this.NACKQueue.size(); i++)
            {
                array[i] = this.NACKQueue.get(i);
            }

            NACKPacket pck = new NACKPacket(array);
            pck.encode();

            this.server.sendPacket(pck.getBuffer().array(), this.ip, this.port);
        }

        if(this.Queue.packets.size() > 0)
        {
            this.Queue.encode();
            this.server.sendPacket(this.Queue.getBuffer().array(), this.ip, this.port);
            this.recoveryQueue.put(this.Queue.SequenceNumber, this.Queue);
            this.Queue.packets.clear();
        }
    }

    public void addToQueue(BaseDataPacket pck)
    {
        pck.encode();
        InternalPacket ipck = new InternalPacket();
        ipck.buffer = pck.getBuffer().array();
        ipck.reliability = 2;
        ipck.messageIndex = this.messageIndex++;
        ipck.toBinary();

        if(this.Queue.getLength() >= this.mtuSize)
        {
            this.Queue.SequenceNumber = this.SequenceNum++;
            this.Queue.encode();
            this.server.sendPacket(this.Queue.getBuffer().array(), this.ip, this.port);
            this.Queue.packets.clear();
        }

        this.Queue.packets.add(ipck);
    }

    public void handlePacket(CustomPacket pck)
    {
        if(pck.SequenceNumber - this.lastSequenceNum == 1)
        {
            this.lastSequenceNum = pck.SequenceNumber;
        }
        else
        {
            for (int i = this.lastSequenceNum; i < pck.SequenceNumber; ++i)
            {
                this.NACKQueue.add(i);
            }
        }

        this.ACKQueue.add(pck.SequenceNumber);

        for (InternalPacket ipck : pck.packets)
        {
            switch (ipck.buffer[0])
            {
                case PacketsID.PING: //PING Packet
                    //
                    PingPacket pp = new PingPacket(ipck.buffer);
                    pp.decode();

                    PongPacket reply = new PongPacket(pp.pingID);

                    this.addToQueue(reply);
                break;

                case PacketsID.CLIENT_CONNECT: // 0x09. Use the constants class
                {
                    ClientConnectPacket ccp = new ClientConnectPacket(ipck.buffer);
                    ccp.decode();
                    //Send a ServerHandshake packet
                    ServerHandshakePacket shp = new ServerHandshakePacket(this.port, ccp.session);
                    this.addToQueue(shp);
                }
                break;

                case PacketsID.CLIENT_HANDSHAKE:
                    ClientHandShakePacket chs = new ClientHandShakePacket(ipck.buffer);
                    chs.decode();

                    break;

                case PacketsID.LOGIN:
                    LoginPacket lp = new LoginPacket(ipck.buffer);

                    this.server.getLogger().info("Login Packet: %d", ipck.buffer.length);
                    lp.decode();

                    if(lp.protocol != PacketsID.CURRENT_PROTOCOL || lp.protocol2 != PacketsID.CURRENT_PROTOCOL)
                    {
                        if(lp.protocol < PacketsID.CURRENT_PROTOCOL || lp.protocol2 < PacketsID.CURRENT_PROTOCOL)
                        {
                            addToQueue(new LoginStatusPacket(1)); // Client outdated
                            close("Wrong Protocol: Client is outdated.");
                        }

                        if(lp.protocol > PacketsID.CURRENT_PROTOCOL || lp.protocol2 > PacketsID.CURRENT_PROTOCOL)
                        {
                            addToQueue(new LoginStatusPacket(2)); // Server outdated
                            close("Wrong Protocol: Server is outdated.");
                        }
                    }

                    this.addToQueue(new LoginStatusPacket(0)); // No error with the protocol.

                    if(lp.username.length() < 3 || lp.username.length() > 15)
                    {
                        close("Username is not valid.");
                    }
                    this.name = lp.username;

                    login();

                    // this.CID = lp.clientID; // we don't need this
                    
                    //Once we get World gen up, uncomment this:
                    /*
                    StartGamePacket sgp = new StartGamePacket(server.getDefaultLevel(), this.entityID);
                    sgp.encode();
                    this.addToQueue(sgp);
                    
					*/
                    break;
                
                default:
                	this.server.getLogger().info("Internal Packet Received packet: %02x", ipck.buffer[0]);
            }
        }
    }

    public void handleAcknowledgePackets(AcknowledgePacket pck) // Ack and Nack
    {
        pck.decode();
        if(pck instanceof ACKPacket) // When whe receive a ACK Packet then
        {
            for(int i : pck.sequenceNumbers)
            {
                this.server.getLogger().info("ACK Packet Received Seq: %d", i);
                this.recoveryQueue.remove(i);
            }
        }
        else if(pck instanceof NACKPacket)
        {
            for(int i : pck.sequenceNumbers)
            {
                this.server.getLogger().info("NACK Packet Received Seq: %d", i);
                this.server.sendPacket(this.recoveryQueue.get(i).getBuffer().array(), this.ip, this.port);
            }
        }
        else
            this.server.getLogger().error("Unknown Acknowledge Packet: %02x", pck.buffer[0]);
    }

    public void sendMessage(String msg)
    {
        addToQueue(new MessagePacket(msg)); // be aware of the message-too-long exception
    }

    public void close(String reason)
    {
        sendMessage(reason);
        addToQueue(new Disconnect());

    }

    public InetAddress getAddress() throws UnknownHostException{
    	return InetAddress.getByName(ip);
    }
    
    public int getPort(){
    	return this.port;
    }

    public String getIName(){
        return name.toLowerCase(Locale.ENGLISH);
    }

    public String getName(){
        return name;
    }

    public String getIdentifier(){ // why not just use EID?
        return ip + Integer.toString(port);
    }

    public EntityType getType() {
        return EntityType.PLAYER;
    }

    public int getMaxHealth() {
        return maxHealth;
    }


    public long getClientID() {
        return clientID;
    }

}
