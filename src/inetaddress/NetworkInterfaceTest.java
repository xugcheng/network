package inetaddress;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkInterfaceTest {
	
	public static void main(String[] args) {
		try {
			Enumeration<NetworkInterface> enums = NetworkInterface.getNetworkInterfaces();
			while(enums.hasMoreElements()){
				System.out.println(enums.nextElement().toString());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
