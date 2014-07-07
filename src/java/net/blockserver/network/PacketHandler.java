package net.blockserver.network;

import net.blockserver.Server;
import net.blockserver.network.data.ClientConnectPacket;
import net.blockserver.network.data.CustomPacket;
import net.blockserver.network.data.InternalPacket;
import net.blockserver.network.login.*;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.nio.ByteBuffer;

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
                if( pid >= (byte)0x01 &&  pid <= (byte)0x0d) { // Raknet Login Packets Range

                    BaseLoginPacket packet;
                    switch(pid){
                        case 0x01: //ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
                            packet = new ConnectedPingPacket(pck, server);
                            ByteBuffer response = packet.getResponse();

                            DatagramPacket packet1c = new DatagramPacket(response.array(), response.capacity(), pck.getAddress(), pck.getPort());
                            sendPacket(packet1c);
                            break;


                        case 0x05: //ID_OPEN_CONNECTION_REQUEST_1 (0x05)
                            packet = new ConnectionRequest1Packet(pck, server);

                            ByteBuffer response6 = packet.getResponse();
                            byte protocol = ((ConnectionRequest1Packet) packet).getProtocol();
                            if(protocol != 5){
                                //Wrong protocol
                                server.getLogger().warning("Client "+pck.getAddress().getHostName()+":"+pck.getPort()+" is outdated, current protocol is 5");
                                IncompatibleProtocolPacket pk = new IncompatibleProtocolPacket(pck.getAddress(), pck.getPort(), (byte) 5, server);
                                sendPacket(pk.getPacket());

                            }
                            else{
                                DatagramPacket packet06 = new DatagramPacket(response6.array(), response6.capacity(), pck.getAddress(), pck.getPort());
                                sendPacket(packet06);
                            }

                            break;

                        case 0x07: //ID_OPEN_CONNECTION_REQUEST_2 (0x07)
                            packet = new ConnectionRequest2(pck, server);

                            ByteBuffer response8 = packet.getResponse();

                            DatagramPacket packet08 = new DatagramPacket(response8.array(), response8.capacity(), pck.getAddress(), pck.getPort());
                            sendPacket(packet08);
                            break;

                        default:
                            server.getLogger().warning("Recived unsupported login packet! PID: %02X", pid);
                    }
                }
                else if( pid >= (byte)0x80 &&  pid <= (byte)0x8f) // Custom Data Packet Range
                {
                    this.server.getLogger().info("Data packet: %02X", pid);

                    CustomPacket packet = new CustomPacket(pck.getData());
                    packet.decode();
                }
                else // Unknown Packet Received!! New Protocol Changes?
                    this.server.getLogger().warning("Received Unknown Packet: 0x%02X",  pid);
            }
            catch(Exception e) {
                e.printStackTrace();
                //this.server.getLogger().fatal("", e.getStackTrace());
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
