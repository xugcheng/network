package serversocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 重定向服务器Demo
 * @author xugc
 *
 */
public class Redirector implements Runnable {
	
	private int port;
	private String newSite;
	
	public Redirector(String newSite,int port){
		this.newSite = newSite;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Listening on the port "+port);
			System.out.println("Connections are redirected to "+newSite);
			while(true){
				try {
					Socket conn = server.accept();
					Thread t = new RediretThread(conn);
					t.start();
				} catch (IOException e) {
					
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	class RediretThread extends Thread
	{
		private Socket connection;
		
		public RediretThread(Socket s){
			this.connection = s;
		}
		
		@Override
		public void run(){
			try {
				Writer out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
				Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer request = new StringBuffer(80);
				while(true){
					int c = in.read();
					if(c=='\r' || c=='\n' || c==-1){
						break;
					}
					request.append((char)c);
				}
				String get = request.toString();
				System.out.println("GET:"+get);
				int firstPlace = get.indexOf(' ');
				int secondPlace = get.indexOf(' ', firstPlace+1);
				String theFile = get.substring(firstPlace+1, secondPlace);
				if(get.indexOf("HTTP")!=-1){
					out.write("HTTP/1.0 302 FOUND\r\n");
					out.write("Date:"+new Date()+"\r\n");
					out.write("Server:Redirector \r\n");
					out.write("Location:"+newSite+theFile+"\r\n");//重定向
					out.write("Content-Type:text/html\r\n\r\n");
				}
				//
				out.write("<html><head><title>redirector</title></head>");
				out.write("<body>");
				out.write("<h1>Document moved</h1>\r\n");
				out.write("The document "+theFile+" has been moved to \r\n<a href='"+newSite + theFile+"'>"+newSite+theFile+"</a>");
				out.write("</body>");
				out.write("</html>");
				out.flush();
			} catch (IOException e) {
				System.err.println("RedirectThread error:"+e);
			}
			
		}
	}

	

	public static void main(String[] args) {
		Thread t = new Thread(new Redirector("http://www.baidu.com", 8087));
		t.start();
	}
	
}

