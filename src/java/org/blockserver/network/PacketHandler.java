package org.blockserver.network;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import org.blockserver.Server;
import org.blockserver.network.raknet.ACKPacket;
import org.blockserver.network.raknet.ConnectedPingPacket;
import org.blockserver.network.raknet.ConnectionRequest1Packet;
import org.blockserver.network.raknet.ConnectionRequest2;
import org.blockserver.network.raknet.CustomPacket;
import org.blockserver.network.raknet.IncompatibleProtocolPacket;
import org.blockserver.network.raknet.NACKPacket;
import org.blockserver.player.Player;

public class PacketHandler extends Thread{
	private UDPSocket socket;
	private Server server;
	private boolean isRunning;

	public PacketHandler(Server server) throws SocketException{
		setName("PacketHandler");
		this.server = server;
		socket = new UDPSocket(server, server.getServerIP(), server.getServerPort());
	}

	public void sendPacket(byte[] buffer, String ip, int port) throws Exception{
		socket.sendTo(buffer, ip, port);
	}
	public void sendPacket(DatagramPacket pck) throws Exception{
		socket.send(pck);
	}

	public void run(){
		if(isRunning){
			throw new RuntimeException("Cannot start already-running packet handler");
		}
		isRunning = true;
		while(isRunning){
			try{
				DatagramPacket pck = socket.receive();
				byte pid = pck.getData()[0];
				if(pid >= RaknetsID.UNCONNECTED_PING && pid <= RaknetsID.ADVERTISE_SYSTEM){ // RakNet Login Packets Range
					switch(pid){
						case RaknetsID.UNCONNECTED_PING: //ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
						case RaknetsID.UNCONNECTED_PING_OPEN_CONNECTIONS: // (0x02)
							ConnectedPingPacket pingPk = new ConnectedPingPacket(pck, server);
							ByteBuffer response = pingPk.getResponse();
							DatagramPacket packet1c = new DatagramPacket(response.array(), response.capacity(), pck.getAddress(), pck.getPort());
							sendPacket(packet1c);
							break;
						case RaknetsID.OPEN_CONNECTION_REQUEST_1: //ID_OPEN_CONNECTION_REQUEST_1 (0x05)
							ConnectionRequest1Packet crPk1 = new ConnectionRequest1Packet(pck, server);
							ByteBuffer response6 = crPk1.getResponse();
							byte protocol = ((ConnectionRequest1Packet) crPk1).getProtocol();
							if (protocol != RaknetsID.STRUCTURE) {
								//Wrong protocol
								server.getLogger().warning("Client " + pck.getAddress().getHostName() + ":" + pck.getPort() + " RakNet protocol is outdated, current protocol is 5");
								IncompatibleProtocolPacket pk = new IncompatibleProtocolPacket(pck.getAddress(), pck.getPort(), (byte) RaknetsID.STRUCTURE, server);
								sendPacket(pk.getPacket());
							}
							else{
								DatagramPacket packet06 = new DatagramPacket(response6.array(), response6.capacity(), pck.getAddress(), pck.getPort());
								sendPacket(packet06);
							}
							break;
						case RaknetsID.OPEN_CONNECTION_REQUEST_2: //ID_OPEN_CONNECTION_REQUEST_2 (0x07)
							ConnectionRequest2 crPk2 = new ConnectionRequest2(pck, server);
							ByteBuffer response8 = crPk2.getResponse();
							DatagramPacket packet08 = new DatagramPacket(response8.array(), response8.capacity(), pck.getAddress(), pck.getPort());
							sendPacket(packet08);
							Player player = new Player(server, pck.getAddress().toString(), pck.getPort(), crPk2.mtuSize, crPk2.clientID);
							
							if(server.isPlayerConnected(player)){
								//Kick them
								server.getLogger().info("Unknown Player "+"("+player.getIP()+":"+player.getPort()+") disconnected: Another user is logged in with the same address as you.");
								server.getLogger().info(server.getPlayersConnected()+" players are connected.");
								player.close("Another is logged in with the same address as you.");
							}
							else{
								server.addPlayer(player);
							}
							
							break;
						default:
							server.getLogger().warning("Recived unsupported raknet packet! PID: %02X", pid);
					}
				}
				else if(pid >= RaknetsID.DATA_PACKET_0 &&  pid <= RaknetsID.DATA_PACKET_F){ // Custom Data Packet Range
					CustomPacket packet = new CustomPacket(pck.getData());
					packet.decode();
					server.getPlayer(pck.getAddress().toString().replace("/", ""), pck.getPort()).handlePacket(packet);
				}
				else if(pid == RaknetsID.ACK || pid == RaknetsID.NACK){
					Player player = server.getPlayer(pck.getAddress().toString().replace("/", ""), pck.getPort());
					if(player != null){
						player.handleAcknowledgePackets(pid == RaknetsID.ACK ? new ACKPacket(pck.getData()) : new NACKPacket(pck.getData()));
					}
				}
				else{ // Unknown Packet Received!! New Protocol Changes?
					server.getLogger().warning("Received Unknown Packet: 0x%02X",  pid);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning(){
		return isRunning;
	}

	public void end() throws Exception{
		if(!isRunning){
			throw new RuntimeException("Tried to stop non-running packet handler");
		}
		isRunning = false;
		socket.close();
		join();
	}
}
