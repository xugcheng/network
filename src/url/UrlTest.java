package url;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlTest {
	
	public static void main(String[] args) {
		try {
			URL url = new URL("http://192.168.8.149:8080/jemp-smg/jemp/jempsystemweb/javascript/chat.js?id=1#chapter1");
			System.out.println("url:"+url);
			System.out.println("protocol:"+url.getProtocol());
			System.out.println("host:"+url.getHost());
			System.out.println("port:"+url.getPort());
			System.out.println("path:"+url.getPath());
			System.out.println("file:"+url.getFile());
			System.out.println("query:"+url.getQuery());
			System.out.println("ref:"+url.getRef());
			System.out.println("content:"+url.getContent().toString());
			
			URL url2 = new URL(url, "application.js");
			System.out.println("url2:"+url2);
			
			File file = new File("c:\\test.log");
			System.out.println(file.toURL());
			System.out.println(file.toURI());
			System.out.println(file.getAbsolutePath());
			
		} catch (MalformedURLException e) {
			System.err.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
