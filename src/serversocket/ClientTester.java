package serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientTester {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8085);
			System.out.println("Listening on the port :" +server.getLocalPort());
			
			while(true){
				Socket connection = null;
				try {
					connection = server.accept();
					System.out.println("Connection established with "+ connection);
					InputThread inputThread = new InputThread(connection.getInputStream());
					OutputThread outputThread = new OutputThread(connection.getOutputStream());
					inputThread.start();
					outputThread.start();
					try {
						inputThread.join();
						outputThread.join();
					} catch (InterruptedException e) {
						
					}
				} catch (Exception e) {
					System.err.println("main1:"+e);
				} finally {
					try {
						if(connection!=null){
							connection.close();
						}
					} catch (Exception e2) {
						
					}
				}
				
			}
			
			
		} catch (IOException e) {
			System.err.println("main2:"+e);
		}
	}

}

/**
 * 输入流处理线程
 * @author xugc
 *
 */
class InputThread extends Thread
{
	private InputStream in;
	
	public InputThread(InputStream in){
		this.in = in;
	}
	
	/**
	 * 在控制台打印输入流,完成后关闭输入流
	 */
	@Override
	public void run(){
		try {
			byte[] bytes = new byte[1024];
			while((in.read(bytes, 0, 1024))!=-1){
				System.out.print(new String(bytes,"utf-8").trim());
			}
			System.out.print("\r\n");
		} catch (IOException e) {
			System.err.println("in1:"+e);
		}
		try {
			in.close();
		} catch (IOException e) {
			System.err.println("in2:"+e);
		}
	}
}

/**
 * 输出流处理线程
 * @author xugc
 *
 */
class OutputThread extends Thread
{
	private Writer out;
	
	public OutputThread(OutputStream out){
		this.out = new OutputStreamWriter(out);
	}
	
	/**
	 * 从控制太读取键盘输入,写入的输出流中,完成后关闭输出流.
	 */
	@Override
	public void run(){
		String line ;
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while(true){
				line = bReader.readLine();
				if(line.equals(".")){
					break;
				}
				out.write(line+"\r\n");
				out.flush();
			}
		} catch (IOException e) {
			System.err.println("o1:"+e);
		}
		
		try {
			out.close();
		} catch (IOException e) {
			System.err.println("o2:"+e);
		}
		
		
	}
}