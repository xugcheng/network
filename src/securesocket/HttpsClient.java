package securesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class HttpsClient {
	
	public static void main(String[] args) {
		//String host = "www.usps.com";
		String host = "api.weixin.qq.com";
		String path = "/cgi-bin/token?grant_type=client_credential&appid=wx1473a4a386436476&secret=9636ec9decc5e939be751ab66efdb373";
		int port = 443;
		
		SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		try {
			SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
			//启用所有密码组
			String[] supported = socket.getSupportedCipherSuites();
			String[] enabled=socket.getEnabledCipherSuites();
			System.out.println("supportedCipherSuites:"+Arrays.asList(supported));
			System.out.println("enabledCipherSuites:"+Arrays.asList(enabled));
			System.out.println("enableSessionCreation:"+socket.getEnableSessionCreation());
			System.out.println("useClientMode:"+socket.getUseClientMode());
			System.out.println("needClientAuth:"+socket.getNeedClientAuth());
			socket.setEnabledCipherSuites(supported);
			//
			socket.addHandshakeCompletedListener(new MyHandshakeCompletedListener());
			Writer out = new OutputStreamWriter(socket.getOutputStream());
			//https在GET中写入完整URL
			out.write("GET http://"+host+path+"/ HTTP/1.1\r\n");
			out.write("Host:"+host+"\r\n");
			out.write("\r\n");
			out.flush();
			
			//读取响应
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s;
			while(!(s=in.readLine()).equals("")){
				System.out.println(s);
			}
			System.out.println();
			
			//
			int c;
			while((c=in.read())!=-1){
				System.out.write(c);
			}
			System.out.println();
			
			in.close();
			out.close();
			socket.close();
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
