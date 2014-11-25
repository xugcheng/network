package weblog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class LookUpThread extends Thread {
	private List<Object> entries;
	private PooledWebLog webLog;
	
	public LookUpThread(List<Object> entries , PooledWebLog webLog){
		this.entries = entries;
		this.webLog = webLog;
	}
	
	@Override
	public void run(){
		String entry;
		while(true){
			synchronized (entries) {
				while(entries.size()==0){
					if(webLog.isFinished()){
						return;
					}
					try {
						entries.wait();
					} catch (Exception e) {
						
					}
				}
				entry = (String)(entries.remove(entries.size()-1));
			}
			int index = entry.indexOf(" ",0);
			String remoteHost = entry.substring(0,index);
			String theRest = entry.substring(index);
			
			try {
				remoteHost = InetAddress.getByName(remoteHost).getHostName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				webLog.log(remoteHost + theRest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.yield();
			Thread.yield();
		}
	}
}
