package clientsocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LowerPoretSanner {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "localhost";
		try {
			InetAddress address = InetAddress.getByName(host);
			for(int i=1;i<1024;i++){
				try {
					Socket socket = new Socket(address, i);
					System.out.println(i+" "+socket);
				} catch (IOException e) {
					System.err.println(i+" : "+e);
					continue;
				}
			}
			
		} catch (UnknownHostException e1) {
			System.err.println(e1);
		}
	}

}
