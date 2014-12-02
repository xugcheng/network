package clientsocket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket socket = null;
		Writer out = null;
		try {
			socket = new Socket("localhost", 7);
			OutputStream raw = socket.getOutputStream();
			BufferedOutputStream buffer = new BufferedOutputStream(raw);
			out = new OutputStreamWriter(buffer, "ascii");
			out.write("GET / HTTP 1.0\r\n\r\n");
			InputStream in = socket.getInputStream();
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=in.read())!=-1){
				sb.append((char)c);
			}
			System.out.print(sb.toString());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
