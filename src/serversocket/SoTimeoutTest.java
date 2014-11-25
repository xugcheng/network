package serversocket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SoTimeoutTest {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8084);
			System.out.println("receive_buffer_size:"+server.getReceiveBufferSize());
			System.out.println("so_timeout:"+server.getSoTimeout());
			server.setSoTimeout(3000);
			Socket conn = null;
			try {
				conn = server.accept();
			} 
			catch (InterruptedIOException e) {
				System.out.println("server timeout:"+server.getSoTimeout());
			}
			catch (Exception e) {
				// TODO: handle exception
			} finally
			{
				if(conn!=null){
					conn.close();
				}
				server.close();
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
