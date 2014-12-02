package udp;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPPortScanner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int port=1024;port<65536;port++){
			try {
				DatagramSocket datagramSocket = new DatagramSocket(port);
				datagramSocket.close();
			} catch (SocketException e) {
				System.out.println("There is a server on the port "+port);
			}
		}

	}

}
