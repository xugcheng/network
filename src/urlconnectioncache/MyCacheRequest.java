package urlconnectioncache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.util.List;
import java.util.Map;

public class MyCacheRequest extends CacheRequest {

	FileOutputStream fos;
	File file;
	
	public MyCacheRequest(String filename,Map<String, List<String>> rspHeaders){
		
		try {
			file = new File(filename);
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(rspHeaders);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void abort() {
		// TODO Auto-generated method stub
		try {
			fos.close();
			file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public OutputStream getBody() throws IOException {
		// TODO Auto-generated method stub
		return fos;
	}

}
