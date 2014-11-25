package clientsocket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class DaytimeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket socket = null;
		try {
			String hostname = "192.168.8.149";
			socket = new Socket(hostname,8082);
			InputStream in = socket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			StringBuffer time = new StringBuffer();
			byte[] bytes = new byte[1024];
			while((bis.read(bytes,0,1024))!=-1){
				time.append(new String(bytes,"utf-8"));
			}
			System.out.println(time.toString().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(socket!=null){
					socket.close();
				}
			} catch (IOException e2) {
				
			}
		}
	}
	
}
