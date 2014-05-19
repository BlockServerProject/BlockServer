package blockserver.tests;
import java.net.*;
import java.io.*;

public class BlockServerClient {

	/**
	 * @param args
	 * @throws SocketException 
	 */
	private static String input(String prompt){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(prompt);
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		// TODO Auto-generated method stub
		boolean running = true;
		DatagramSocket socket = new DatagramSocket();
		while(running){
			String data = input("Data to send: ");
			byte[] buf = new byte[256];
			buf = data.getBytes();
			InetAddress address = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 19132);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(data == "stop"){
				running = false;
			}
		}
		
		
		

	}

}
