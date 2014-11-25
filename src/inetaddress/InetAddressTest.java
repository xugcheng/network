package inetaddress;

import java.io.IOException;
import java.net.InetAddress;

public class InetAddressTest {
	
	public static void status(InetAddress address){
		System.out.println("******************");
		System.out.println("isAnyLocalAddress:"+address.isAnyLocalAddress());
		System.out.println("isLinkLocalAddress:"+address.isLinkLocalAddress());
		System.out.println("isLoopbackAddress:"+address.isLoopbackAddress());
		try {
			System.out.println("isReachable:"+address.isReachable(1000));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("******************");
	}
	
	
	public static void main(String[] args) throws Exception{
		InetAddress inetAddress = InetAddress.getLocalHost();
		System.out.println(inetAddress);
		
		InetAddress address1 = InetAddress.getByName("www.baidu.com");
		System.out.println(address1);
		
		InetAddress address2 = InetAddress.getByName("192.168.8.149");
		System.out.println(address2);
		
		InetAddress address3 = InetAddress.getByName("127.0.0.1");
		System.out.println(address3);
		
		byte[] ip = address2.getAddress();
		for(byte ipSegment: ip){
			System.out.print(ipSegment+".");
		}
		System.out.println();
		
		for(byte ipSegment: ip){
			//int newIpSegment = (ipSegment < 0) ? 256+ipSegment : ipSegment;
			int newIpSegment = (256+ipSegment) % 256; 
			System.out.print(newIpSegment+".");
		}
		System.out.println();
		System.out.println("Byte.MAX_VALUE:"+Byte.MAX_VALUE);
		System.out.println("Byte.MIN_VALUE:"+Byte.MIN_VALUE);
		status(inetAddress);
		status(address1);
		status(address2);
		status(address3);
		
	}
	
	
}
