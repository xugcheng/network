package clientsocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketConstructorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			InetAddress address1 = InetAddress.getLocalHost();
			InetAddress address2 = InetAddress.getByName("192.168.8.149");
			
			Socket socket = new Socket(address2, 80, address1, 8080);
			System.out.println(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
