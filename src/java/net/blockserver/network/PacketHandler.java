package net.blockserver.network;

import net.blockserver.Server;
import net.blockserver.network.minecraft.ClientConnectPacket;
import net.blockserver.network.minecraft.ServerHandshakePacket;
import net.blockserver.network.raknet.*;
import net.blockserver.utility.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class PacketHandler extends Thread
{
	private UDPSocket socket;
	private Server server;
	
	private int packetsSent;
	private int packetsRecived;
	private int totalPackets;

    private boolean isRunning;

    public boolean isRunning() { return this.isRunning; }

	public PacketHandler(Server server)  throws SocketException {
        this.setName("PacketHandler");
        this.server = server;
		this.socket = new UDPSocket(server, server.getServerIP(), server.getServerPort());
	}

    public void Start() throws Exception
    {
        if(this.isRunning) {
            throw new Exception("The packet handler class is already running!");
        }

        if(this.server.getServerPort() < 19132 || 19135 < this.server.getServerPort()) {
            this.server.getLogger().warning("You are using a non standard port ");
        }

        this.isRunning = true;
        this.start();
    }

    public void sendPacket(byte[] buffer, String ip, int port) throws Exception {
    	this.packetsSent++;
        this.socket.SendTO(buffer, ip, port);
    }
    
    public void sendACK(byte[] ackBuffer, InetAddress ip, int port) throws Exception{
    	DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length, ip, port);
    	this.sendPacket(ackPacket);
    }

    public void sendPacket(DatagramPacket pck) throws Exception {
    	this.packetsSent++;
        this.socket.Send(pck);
    }

    public void run() {
        while(this.isRunning)
        {
            try {
                DatagramPacket pck = this.socket.Receive();
                byte pid = pck.getData()[0];
                if( pid >= RaknetsID.UNCONNECTED_PING &&  pid <= RaknetsID.ADVERTISE_SYSTEM) { // raknet Login Packets Range

                    BaseLoginPacket packet;
                    switch(pid){
                        case RaknetsID.UNCONNECTED_PING: //ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
                        case RaknetsID.UNCONNECTED_PING_OPEN_CONNECTIONS: // (0x02)
                            packet = new ConnectedPingPacket(pck, server);
                            ByteBuffer response = packet.getResponse();

                            DatagramPacket packet1c = new DatagramPacket(response.array(), response.capacity(), pck.getAddress(), pck.getPort());
                            sendPacket(packet1c);
                            break;


                        case RaknetsID.OPEN_CONNECTION_REQUEST_1: //ID_OPEN_CONNECTION_REQUEST_1 (0x05)
                            packet = new ConnectionRequest1Packet(pck, server);

                            ByteBuffer response6 = packet.getResponse();
                            byte protocol = ((ConnectionRequest1Packet) packet).getProtocol();
                            if(protocol != RaknetsID.STRUCTURE){
                                //Wrong protocol
                                server.getLogger().warning("Client "+pck.getAddress().getHostName()+":"+pck.getPort()+" is outdated, current protocol is 5");
                                IncompatibleProtocolPacket pk = new IncompatibleProtocolPacket(pck.getAddress(), pck.getPort(), (byte) RaknetsID.STRUCTURE, server);
                                sendPacket(pk.getPacket());

                            }
                            else{
                                DatagramPacket packet06 = new DatagramPacket(response6.array(), response6.capacity(), pck.getAddress(), pck.getPort());
                                sendPacket(packet06);
                            }

                            break;

                        case RaknetsID.OPEN_CONNECTION_REQUEST_2: //ID_OPEN_CONNECTION_REQUEST_2 (0x07)
                            packet = new ConnectionRequest2(pck, server);

                            ByteBuffer response8 = packet.getResponse();

                            DatagramPacket packet08 = new DatagramPacket(response8.array(), response8.capacity(), pck.getAddress(), pck.getPort());
                            sendPacket(packet08);
                            break;

                        default:
                            server.getLogger().warning("Recived unsupported raknet packet! PID: %02X", pid);
                    }
                }
                else if( pid >= (byte)RaknetsID.DATA_PACKET_0 &&  pid <= (byte)RaknetsID.DATA_PACKET_F) // Custom Data Packet Range
                {
                    this.server.getLogger().info("Data packet: %02X", pid);

                    CustomPacket packet = new CustomPacket(pck.getData());
                    packet.decode();
                    List<InternalPacket> packets = packet.packets;
                    for(int i = 0; i < packets.size(); i++){
	                	byte[] buffer = packets.get(i).buffer;
	                	//this.server.getLogger().info("Data packet payload: "+Arrays.toString(buffer));
	                	this.dhandle(packets.get(i), pck);
                    }
                	
                }
                else if(pid == RaknetsID.ACK || pid == RaknetsID.NACK)
                {
                    if(this.server.getPlayer(pck.getAddress().toString()) != null)
                    {

                    }
                }
                else // Unknown Packet Received!! New Protocol Changes?
                    this.server.getLogger().warning("Received Unknown Packet: 0x%02X",  pid);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void dhandle(InternalPacket packet, DatagramPacket pkt) throws IOException{
    	this.packetsRecived++; //Recived a packet!
    	this.totalPackets++;
    	ByteBuffer buffer = ByteBuffer.wrap(packet.buffer);
    	byte PID = buffer.get();
    	int[] packetNumber = new int[] {totalPackets};
    	ACKPacket ack = new ACKPacket(packetNumber);
    	ack.encode();
    	byte[] ackBuffer = ack.buffer;
    	try {
			this.sendACK(ackBuffer, pkt.getAddress(), pkt.getPort());
		} catch (Exception e) {
			this.server.getLogger().error("Exception while sending ACK packet:");
			this.server.getLogger().error(e.getMessage());
		}
    	
    	switch(PID){
    	case 0x09:
    		//TODO: Init our player, add player to list in server etc.
    		//ClientConnect Packet
    		ClientConnectPacket ccp = new ClientConnectPacket(packet.buffer);
    		//Send a ServerHandshake packet
    		ServerHandshakePacket shp = new ServerHandshakePacket(pkt.getPort(), ccp.session);
    		shp.encode();
			ByteBuffer shpBuffer = shp.getBuffer();
			InternalPacket[] handshake = InternalPacket.fromBinary(shpBuffer.array());
			CustomPacket encodedPacket = new CustomPacket(handshake);
			encodedPacket.encode();
			byte[] encodedBuffer = encodedPacket.getBuffer().array();
			
			
    		DatagramPacket shpPacket = new DatagramPacket(encodedBuffer, encodedBuffer.length, pkt.getAddress(), pkt.getPort());
			this.socket.Send(shpPacket);
    		
    	}
    }

    public void Stop() throws Exception
    {
        if(!this.isRunning)
            throw new Exception("The packet handler is not running");

        this.isRunning = false;
        this.join();
    }
}
