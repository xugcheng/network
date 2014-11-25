package proxyselector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class LocalProxySelector extends ProxySelector{

	private List<URI> failed = new ArrayList<URI>();
	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		// TODO Auto-generated method stub
		failed.add(uri);
	}

	@Override
	public List<Proxy> select(URI uri) {
		// TODO Auto-generated method stub
		List<Proxy> result = new ArrayList<Proxy>();
		
		if(failed.contains(uri) || "http".equalsIgnoreCase(uri.getScheme())){
			result.add(Proxy.NO_PROXY);
		}else {
			SocketAddress socketAddress = new InetSocketAddress("proxy.example.com", 80);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
			result.add(proxy);
		}
		
		return result;
	}

	public static void main(String[] args){
		ProxySelector.setDefault(new LocalProxySelector());
	}
}
