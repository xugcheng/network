package urlencoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

public class UrlEncoderTest {
	
	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("This String has space.*_-","UTF-8"));
			System.out.println(URLEncoder.encode("/ \\ : ; \" ' ? < >","utf-8"));
			String src = "http://www.qq.com";
			String des = "http%3A%2F%2Fwww.qq.com";
			System.out.println(src+"==>"+URLEncoder.encode(src,"UTF-8"));
			System.out.println(des+"<=="+URLDecoder.decode(des,"UTF-8"));
			
			Properties properties = System.getProperties();
			Enumeration<Object> keys = System.getProperties().keys();
			while(keys.hasMoreElements()){
				Object key = keys.nextElement();
				System.out.println(key+"="+properties.get(key));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
