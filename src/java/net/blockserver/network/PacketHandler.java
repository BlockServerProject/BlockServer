package net.blockserver.network;

import net.blockserver.Server;
import net.blockserver.network.data.CustomPacket;
import net.blockserver.network.login.ConnectedPingPacket;
import net.blockserver.network.login.ConnectionRequest1Packet;
import net.blockserver.network.login.ConnectionRequest2;
import net.blockserver.network.login.IncompatibleProtocolPacket;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

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

    public void sendResponse(DatagramPacket response) throws Exception {
        socket.Send(response);

    }

    public void run() {
        while(this.isRunning)
        {
            try {
                DatagramPacket pck = this.socket.Receive();
                if( pck.getData()[0] >= (byte)0x01 &&  pck.getData()[0] <= (byte)0x0d) { // Raknet Login Packets Range
                    this.server.getLogger().info("Login Packet Length %d", pck.getLength());
                    this.loginHandle(pck);
                }
                else if( pck.getData()[0] >= (byte)0x80 &&  pck.getData()[0] <= (byte)0x8f) // Custom Data Packet Range
                {
                    this.dataHandle(pck);
                }
                else // Unknown Packet Received!! New Protocol Changes?
                    this.server.getLogger().error("Received Unknown Packet: 0x%02X",  pck.getData()[0]);
            }
            catch(Exception e) {
                this.server.getLogger().fatal(e.getMessage());
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

	public void loginHandle(DatagramPacket udpPacket) throws Exception{
		byte PID = udpPacket.getData()[0];
		BaseLoginPacket packet;
		switch(PID){
		case 0x01:
			//ID_CONNECTED_PING_OPEN_CONNECTIONS (0x01)
			//Client to Broadcast
			packet = new ConnectedPingPacket(udpPacket, server);
			ByteBuffer response = packet.getResponse();
			
			DatagramPacket packet1c = new DatagramPacket(response.array(), response.capacity(), udpPacket.getAddress(), udpPacket.getPort());
			sendResponse(packet1c);
			server.getLogger().info("Sent 0x1c");
			break;
			
		
		case 0x05:
			//ID_OPEN_CONNECTION_REQUEST_1 (0x05)
			//Client to Server
			packet = new ConnectionRequest1Packet(udpPacket, server);
			
			ByteBuffer response6 = packet.getResponse();
			byte protocol = ((ConnectionRequest1Packet) packet).getProtocol();
			if(protocol != 5){
				//Wrong protocol
                server.getLogger().warning("Client "+udpPacket.getAddress().getHostName()+":"+udpPacket.getPort()+" is outdated, current protocol is 5");
				IncompatibleProtocolPacket pk = new IncompatibleProtocolPacket(udpPacket.getAddress(), udpPacket.getPort(), (byte) 5, server);
				sendResponse(pk.getPacket());
				
			}
			else{
			
				DatagramPacket packet06 = new DatagramPacket(response6.array(), response6.capacity(), udpPacket.getAddress(), udpPacket.getPort());
				sendResponse(packet06);

                //server.getLogger().info("Recived packet 0x05, sent 0x06");
			}
			
			break;
		
		case 0x07:
			//ID_OPEN_CONNECTION_REQUEST_2 (0x07)
			//Client to Server
			packet = new ConnectionRequest2(udpPacket, server);
			
			ByteBuffer response8 = packet.getResponse();
			
			DatagramPacket packet08 = new DatagramPacket(response8.array(), response8.capacity(), udpPacket.getAddress(), udpPacket.getPort());
			sendResponse(packet08);
			break;
			
		default:
			server.getLogger().warning("Recived unsupported login packet! PID: %02X", PID);
		}
	}
	
	public void dataHandle(DatagramPacket udpPacket){
		byte pid = udpPacket.getData()[0];
		BaseDataPacket packet;

        this.server.getLogger().info("Data packet: %02X", pid);

		switch(pid){
		case (byte) 0x84:
			packet = new CustomPacket(udpPacket, server);
			byte[] payload = udpPacket.getData();
			server.getLogger().info("Recived custom packet: " + Arrays.toString(payload));
		}
	}
}
