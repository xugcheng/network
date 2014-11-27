package nonblockingio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class Chargen {
	
	public static int DEFAULT_PORT = 19;
	
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		String host = "localhost";
		SocketAddress address = new InetSocketAddress(host,port);
		try {
			SocketChannel client = SocketChannel.open(address);
			
			ByteBuffer buffer = ByteBuffer.allocate(74);
			WritableByteChannel out = Channels.newChannel(System.out);
//			//阻塞方式读取
//			while(client.read(buffer)!=-1){
//				buffer.flip();
//				out.write(buffer);
//				buffer.clear();
//			}
			//非阻塞方式读取
			client.configureBlocking(false);
			System.out.println("isBlocking:"+client.isBlocking());
			while(true){
				int n = client.read(buffer);
				if(n>0){
					buffer.flip();
					out.write(buffer);
					buffer.clear();
				}else if(n == -1){
					break;
				}
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
