package udp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 构造函数test
 * @author xugc
 *
 */
public class DatagramPacketConstructorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//用于接收的数据报的构造函数
		byte[] buffer = new byte[8192];
		DatagramPacket dpR = new DatagramPacket(buffer, buffer.length);
		//用于发送的数据报的构造函数
		String s = "This is a test";
		try {
			InetAddress ia = InetAddress.getByName("www.ibiblio.org");
			byte[] data = s.getBytes("ASCII");
			int port = 7;
			DatagramPacket dpS = new DatagramPacket(data, data.length, ia,port);
			
			System.out.println("Address:"+dpS.getAddress());
			System.out.println("port:"+dpS.getPort());
			System.out.println("length:"+dpS.getLength());
			System.out.println("data:"+new String(dpS.getData(),dpS.getOffset(),dpS.getLength()));
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dpS.getData(), dpS.getOffset(), dpS.getLength());
			DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
