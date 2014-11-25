package urlconnectioncache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.CacheResponse;
import java.util.List;
import java.util.Map;

public class MyCacheResponse extends CacheResponse{

	FileInputStream fis;
	Map<String,List<String>> headers;
	
	@SuppressWarnings("unchecked")
	public MyCacheResponse(String filename)
	{
		try {
			fis = new FileInputStream(new File(filename));
			ObjectInputStream ois = new ObjectInputStream(fis);
			headers = (Map<String, List<String>>)ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public InputStream getBody() throws IOException {
		// TODO Auto-generated method stub
		return fis;
	}
	
	@Override
	public Map<String, List<String>> getHeaders() throws IOException {
		// TODO Auto-generated method stub
		return headers;
	}
	
}
