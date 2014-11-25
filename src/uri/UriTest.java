package uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UriTest {
	
	public static void main(String[] args) {
		try {
			URI uri = new URI("http://192.168.8.149:8080/jemp-smg/servlet/videopushtest?id=1");
			URL url = uri.toURL();
			System.out.println("URI:"+uri);
			System.out.println("URL:"+url);
			
			URI uri2 = new URI("image/1.jpg");
			URI absolute = uri.resolve(uri2);
			URI relative = uri.relativize(absolute);
			System.out.println("absolute:"+absolute);
			System.out.println("relative:"+relative);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
