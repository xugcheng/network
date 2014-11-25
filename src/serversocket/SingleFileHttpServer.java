package serversocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单个文件的http服务器(demo)
 * @author xugc
 *
 */
public class SingleFileHttpServer extends Thread {
	
	private byte[] head;
	private byte[] content;
	private int port = 80;
	
	public SingleFileHttpServer(String data,String encoding,String MIMEType,int port) throws UnsupportedEncodingException{
		this(data.getBytes(encoding), encoding, MIMEType, port);
	}
	
	public SingleFileHttpServer(byte[] data,String encoding,String MIMEType,int port) throws UnsupportedEncodingException{
		this.content = data;
		this.port = port;
		StringBuffer headerBuf = new StringBuffer();
		headerBuf.append("HTTP/1.0 200 OK\r\n");
		headerBuf.append("Server: OneFile 1.0\r\n");
		headerBuf.append("Content-Length:"+content.length+"\r\n");
		headerBuf.append("Content-Type:"+MIMEType+"\r\n\r\n");
		this.head = headerBuf.toString().getBytes("ASCII");
	}
	
	@Override
	public void run(){
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Listening on port:"+port+".");
			System.out.println("Data to be sent:");
			System.out.println(content);
			while(true){
				Socket conn = null;
				try {
					conn = server.accept();
					OutputStream out = new BufferedOutputStream(conn.getOutputStream());
					InputStream in = new BufferedInputStream(conn.getInputStream());
					StringBuffer request = new StringBuffer(80);
					//只读取一行数据,用于判断schema
					while(true){
						int c = in.read();
						if(c=='\r' || c=='\n' || c==-1) {
							break;
						}
						request.append((char)c);
					}
					if(request.toString().indexOf("HTTP/")!=-1){
						out.write(head);
					}
					out.write(content);
					out.flush();
				} catch (Exception e) {
					
				} finally{
					try {
						if(conn!=null){
							conn.close();
						}
					} catch (Exception e2) {
						
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Can't start the server :"+e);
		}
	}
	
	public static void main(String[] args) {
		String encoding = "UTF-8";
		String MIMEType = "txt/html";
		int port = 8086;
		try {
			FileInputStream fis = new FileInputStream("1/1.txt");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c = 0;
			while((c=fis.read())!=-1){
				out.write(c);
			}
			byte[] data = out.toByteArray();
			Thread t = new SingleFileHttpServer(data, encoding, MIMEType, port);
			t.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
