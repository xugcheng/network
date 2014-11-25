package serversocket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8083);
			System.out.println(server);
			System.out.println(server.getLocalSocketAddress());
			while(true){
				Socket connection = server.accept();
				try {
					OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
					out.write("You are connected to this server.");
					out.flush();
				} catch (Exception e) {
					//
				} finally{
					try {
						if(connection!=null){
							connection.close();
						}
					} catch (Exception e2) {
						//
					}
					
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
