package net.blockserver.network;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import net.blockserver.Server;
import net.blockserver.network.raknet.ACKPacket;
import net.blockserver.network.raknet.ConnectedPingPacket;
import net.blockserver.network.raknet.ConnectionRequest1Packet;
import net.blockserver.network.raknet.ConnectionRequest2;
import net.blockserver.network.raknet.CustomPacket;
import net.blockserver.network.raknet.IncompatibleProtocolPacket;
import net.blockserver.network.raknet.NACKPacket;
import net.blockserver.player.Player;

public class PacketHandler extends Thread
{
	private UDPSocket socket;
	private Server server;


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
        this.socket.SendTO(buffer, ip, port);
    }

    public void sendPacket(DatagramPacket pck) throws Exception {
        this.socket.Send(pck);
    }

    public void run() {
        while(this.isRunning)
        {
            try {
                DatagramPacket pck = this.socket.Receive();
                byte pid = pck.getData()[0];
                if( pid >= RaknetsID.UNCONNECTED_PING &&  pid <= RaknetsID.ADVERTISE_SYSTEM) // raknet Login Packets Range
                {
                    switch(pid)
                    {
                        case RaknetsID.UNCONNECTED_PING: //ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
                        case RaknetsID.UNCONNECTED_PING_OPEN_CONNECTIONS: // (0x02)
                        {
                            ConnectedPingPacket packet = new ConnectedPingPacket(pck, server);
                            ByteBuffer response = packet.getResponse();

                            DatagramPacket packet1c = new DatagramPacket(response.array(), response.capacity(), pck.getAddress(), pck.getPort());
                            sendPacket(packet1c);
                        }
                        break;


                        case RaknetsID.OPEN_CONNECTION_REQUEST_1: //ID_OPEN_CONNECTION_REQUEST_1 (0x05)
                        {
                            ConnectionRequest1Packet packet = new ConnectionRequest1Packet(pck, server);

                            ByteBuffer response6 = packet.getResponse();
                            byte protocol = ((ConnectionRequest1Packet) packet).getProtocol();
                            if (protocol != RaknetsID.STRUCTURE) {
                                //Wrong protocol
                                server.getLogger().warning("Client " + pck.getAddress().getHostName() + ":" + pck.getPort() + " is outdated, current protocol is 5");
                                IncompatibleProtocolPacket pk = new IncompatibleProtocolPacket(pck.getAddress(), pck.getPort(), (byte) RaknetsID.STRUCTURE, server);
                                sendPacket(pk.getPacket());

                            } else {
                                DatagramPacket packet06 = new DatagramPacket(response6.array(), response6.capacity(), pck.getAddress(), pck.getPort());
                                sendPacket(packet06);
                            }
                        }
                        break;

                        case RaknetsID.OPEN_CONNECTION_REQUEST_2: //ID_OPEN_CONNECTION_REQUEST_2 (0x07)
                        {
                            ConnectionRequest2 packet = new ConnectionRequest2(pck, server);

                            ByteBuffer response8 = packet.getResponse();

                            DatagramPacket packet08 = new DatagramPacket(response8.array(), response8.capacity(), pck.getAddress(), pck.getPort());
                            sendPacket(packet08);

                            Player player = new Player(pck.getAddress().toString(), pck.getPort(), packet.mtuSize, packet.clientID);
                            server.addPlayer(player);
                        }
                        break;

                        default:
                            server.getLogger().warning("Recived unsupported raknet packet! PID: %02X", pid);
                    }
                }
                else if(pid >= RaknetsID.DATA_PACKET_0 &&  pid <= RaknetsID.DATA_PACKET_F) // Custom Data Packet Range
                {

                    CustomPacket packet = new CustomPacket(pck.getData());
                    packet.decode();
                    server.getPlayer(pck.getAddress().toString().replace("/", ""), pck.getPort()).handlePacket(packet);
                	
                }
                else if(pid == RaknetsID.ACK || pid == RaknetsID.NACK)
                {
                    Player player = server.getPlayer(pck.getAddress().toString().replace("/", ""), pck.getPort());
                    if(player != null)
                    {
                        player.handleAcknowledgePackets(pid == RaknetsID.ACK ? new ACKPacket(pck.getData()) : new NACKPacket(pck.getData()));
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

    public void Stop() throws Exception
    {
        if(!this.isRunning)
            throw new Exception("The packet handler is not running");

        this.isRunning = false;
        this.join();
    }
}
