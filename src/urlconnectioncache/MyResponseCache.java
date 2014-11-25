package urlconnectioncache;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class MyResponseCache extends ResponseCache{
	
	@Override
	public CacheResponse get(URI uri, String rqstMethod,Map<String, List<String>> rqstHeaders) throws IOException {
		// TODO Auto-generated method stub
		// get the response from a cached file if available
//		   if (uri.equals(ParseUtil.toURI(uri1))) {
//		        return new MyCacheResponse(FNPrefix+"file1.cache");
//		   }
		return null;
	}

	@Override
	public CacheRequest put(URI uri, URLConnection conn) throws IOException {
		// TODO Auto-generated method stub
		// save cache to a file
		// 1. serialize headers into file2.cache
		// 2. write data to file2.cache
//		if (uri.equals(ParseUtil.toURI(uri2))) {
//	       return new MyCacheRequest(OutFNPrefix+"file2.cache",conn.getHeaderFields());
//		}
		return null;
	}
	
}
