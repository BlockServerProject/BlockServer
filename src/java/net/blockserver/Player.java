package net.blockserver;


import net.blockserver.math.Vector3f;
import net.blockserver.network.RaknetsID;
import net.blockserver.network.minecraft.BaseDataPacket;
import net.blockserver.network.minecraft.ClientConnectPacket;
import net.blockserver.network.minecraft.PacketsID;
import net.blockserver.network.minecraft.PingPacket;
import net.blockserver.network.minecraft.PongPacket;
import net.blockserver.network.minecraft.ServerHandshakePacket;
import net.blockserver.network.raknet.*;
import net.blockserver.scheduler.CallBackTask;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Vector3f
{
    public static int nextID = 1;

    private String name;
    private String iname;
    private String ip;

    private int entityID;

    private int lastSequenceNum;
    private int SequenceNum;
    private int messageIndex;

    private int clientID;
    private int CID; // Client ID From MCPE Client

    private int port;
    private short mtuSize; // Maximum Transport Unit Size

    private Server server;
    private CustomPacket Queue;
    private List<Integer> ACKQueue; // Received Packet Queue
    private List<Integer> NACKQueue; // Not received packet queue
    private Map<Integer, CustomPacket> recoveryQueue; // Packet sended queue to be used if not rceived

    public String getIP()
    {
        return this.ip;
    }

    public Player(String ip, int port, short mtu, int clientID)
    {
        super(0);
        this.entityID = Player.nextID++;
        this.ip = ip.replace("/", "");
        this.port = port;
        this.mtuSize = mtu;
        this.clientID = clientID;

        this.lastSequenceNum = this.SequenceNum = this.messageIndex = 0;

        this.server = Server.getInstance();
        this.Queue = new CustomPacket();
        this.ACKQueue = new ArrayList<>();
        this.NACKQueue = new ArrayList<>();
        this.recoveryQueue = new HashMap<>();

        try {
            this.server.getScheduler().addTask(new CallBackTask(this, "Update", 10, true));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Update(int ticks)
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
            this.server.getLogger().info("Triggered!");
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
                case PacketsID.CLIENT_CONNECT: // 0x09. Use the constants class
                {
                    ClientConnectPacket ccp = new ClientConnectPacket(ipck.buffer);
                    ccp.decode();
                    //Send a ServerHandshake packet
                    ServerHandshakePacket shp = new ServerHandshakePacket(this.port, ccp.session);
                    shp.encode();
                    this.addToQueue(shp);
                }
                break;
                
                case PacketsID.PING: //PING Packet
                    //
                	PingPacket pp = new PingPacket(ipck.buffer);
                	pp.decode();

                	PongPacket reply = new PongPacket(pp.pingID);
                	reply.encode();
                	
                	this.addToQueue(reply);
                	break;
                
                default:
                	this.server.getLogger().info("Recived packet: %02x", ipck.buffer[0]);
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
    
    public InetAddress getAddress() throws UnknownHostException{
    	return InetAddress.getByName(this.ip);
    }
    
    public int getPort(){
    	return this.port;
    }
}
