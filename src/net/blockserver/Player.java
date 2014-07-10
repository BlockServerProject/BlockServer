package net.blockserver;


import net.blockserver.math.Vector3f;
import net.blockserver.network.RaknetsID;
import net.blockserver.network.raknet.CustomPacket;
import net.blockserver.scheduler.CallBackTask;

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
    private List<Integer> ACKQueue;
    private List<Integer> NACKQueue;
    private Map<Integer, CustomPacket> recoveryQueue;

    public String getIP()
    {
        return this.ip;
    }

    public Player(String ip, int port, short mtu, int clientID)
    {
        super(0);
        this.entityID = Player.nextID++;
        this.ip = ip;
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
            //Do Stuff
        }

        if(this.NACKQueue.size() > 0)
        {
            //Do Stuff
        }

        if(this.Queue.packets.size() > 0)
        {
            //Do Stuff
        }
    }

    public void handlePacket(byte[] buffer)
    {
        byte pid = buffer[0];
        switch (pid)
        {
        }
    }

    public void handleAcknowledgePackets(byte[] buffer) // Ack and Nack
    {
        if(buffer[0] == RaknetsID.ACK)
        {
            //Do Stuff
        }
        else if(buffer[0] == RaknetsID.NACK)
        {
            //Do Stuff
        }
        else
            this.server.getLogger().error("Unknown Packet: %02x", buffer[0]);
    }
}
