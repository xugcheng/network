package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * discard协议client, 把用户控制台的输入行,用UDP数据报发送.
 * @author xugc
 *
 */
public class UDPDiscardClient {
	
	public static void main(String[] args) {
		String host = "localhost";
		int port = 9;
		
		try {
			InetAddress server = InetAddress.getByName(host);
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket ds = new DatagramSocket();
			while(true){
				String line = userInput.readLine();
				if(line.equals(".")) break;
				byte[] data = line.getBytes("UTF-8");
				DatagramPacket dp = new DatagramPacket(data, data.length, server, port);
				ds.send(dp);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
