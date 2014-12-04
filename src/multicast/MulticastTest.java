package multicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import org.apache.log4j.Logger;

/*
 * 组播测试 : 在本机进行组播测试
 */
public class MulticastTest implements Runnable {
	
	private static Logger logger = Logger.getLogger("MulticastTest");
	private MulticastSocket mSocket;
	private DatagramPacket rece;
	private byte[] buffer;
	private String host = "230.0.0.1";
	private int port = 4800;
	
	public MulticastTest(){
		try {
			mSocket = new MulticastSocket(port);
			InetAddress group = InetAddress.getByName(host);
			mSocket.joinGroup(group);
			buffer = new byte[8192];
			rece = new DatagramPacket(buffer, buffer.length);
			Thread th = new Thread(this);
			th.start();
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				String line = userIn.readLine();
				if(line.equals(".")) break;
				byte[] data = line.getBytes();
				DatagramPacket dp = new DatagramPacket(data, data.length, group, port);
				mSocket.send(dp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MulticastTest();
	}

	@Override
	public void run() {
		while(true){
			try {
				mSocket.receive(rece);
				logger.debug("receive data:"+new String(rece.getData())+"from " +rece.getAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
