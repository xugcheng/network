package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

/**
 * discard协议server
 * @author xugc
 *
 */
public class UDPDiscardServer {
	
	public final static int DEFAULT_PORT = 9;
	public final static int MAX_PACKET_SIZE = 65507;
	public static Logger logger = Logger.getLogger("UDPDiscardServer");
	
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		
		try {
			DatagramSocket server = new DatagramSocket(port);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			while(true){
				try {
					server.receive(packet);
					String s = new String(packet.getData(),packet.getOffset(),packet.getLength());
					logger.debug(String.format("%s at port %s says %s", packet.getAddress(),packet.getPort(),s));
					packet.setLength(buffer.length);
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
