package inetaddress;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostLookUp {
	
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入主机或者ip地址:");
		try {
			while(true){
				String host = in.readLine();
				if("exit".equalsIgnoreCase(host)){
					break;
				}
				System.out.println(host+"==>"+lookup(host));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String lookup(String host){
		InetAddress address;
		try {
			address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			return "Can't find this host : "+host;
		}
		if(isHostName(host)){
			return address.getHostAddress();
		}
		else {
			return address.getHostName();
		}
	}
	
	public static boolean isHostName(String host){
		if(host.indexOf(":")!=-1) return false;
		
		char[] charArray = host.toCharArray();
		for(char hostchar : charArray){
			if(!Character.isDigit(hostchar)){
				if(hostchar!='.') return true;
			}
		}
		
		return false;
	}
	
}
