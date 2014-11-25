package serversocket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaytimeServer {
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH(hh):mm:ss SSS E D F");
		
		try {
			ServerSocket server = new ServerSocket(8082);
			Socket conn = null;
			while(true){
				try {
					conn = server.accept();
					Writer out = new OutputStreamWriter(conn.getOutputStream());
					out.write(sdf.format(new Date())+"\r\n");
					out.flush();
					out.close();
				} catch (IOException e) {
					
				} finally{
					try {
						if(conn!=null){
							conn.close();
						}
					} catch (IOException e2) {
						
					}
					
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
