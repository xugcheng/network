package weblog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.CommonUtil;

public class PooledWebLog {
	
	private BufferedReader in;
	private BufferedWriter out;
	private int numberOfThreads;
	private List<Object> entries = Collections.synchronizedList(new ArrayList<Object>());
	private boolean finished = false;
	
	public PooledWebLog(InputStream in,OutputStream out,int numberOfThreads){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new BufferedWriter(new OutputStreamWriter(out));
		this.numberOfThreads = numberOfThreads;
	}
	
	public boolean isFinished(){
		return this.finished;
	}
	
	public int getNumberOfThreads(){
		return this.numberOfThreads;
	}
	
	public void log(String entry) throws IOException{
		out.write(entry + System.getProperty("line.separator")+"\r\n");
		out.flush();
	}
	
	public void processLogFile(){
		long start = System.currentTimeMillis();
		for(int i=0;i<numberOfThreads;i++){
			Thread t = new LookUpThread(entries, this);
			t.start();
		}
		try {
			String entry = in.readLine();
			while(entry!=null){
				if(entries.size()>numberOfThreads){
					try {
						Thread.sleep((long)1000.0/numberOfThreads);
					} catch (Exception e) {
						
					}
					continue;
				}
				synchronized (entries) {
					entries.add(0, entry);
					entries.notifyAll();
				}
				entry = in.readLine();
				Thread.yield();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		this.finished = true;
		//结束仍在等待的线程.
		synchronized (entries) {
			entries.notifyAll();
		}
		long end = System.currentTimeMillis();
		System.out.print("start:"+start+",end:"+end+",time:"+(end-start));
	}
	
	public static void main(String[] args) {
		
		try {
			String logfilePath = CommonUtil.scan("日志文件地址:");
			PooledWebLog pooledWebLog = new PooledWebLog(new FileInputStream(logfilePath), System.out, 10);
			pooledWebLog.processLogFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
