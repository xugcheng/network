package nonblockingio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class IntgenServer {
	
	public static Logger logger = Logger.getLogger("network");
	public static int DEFAULT_PORT = 1919;
	
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		logger.debug("Listening for connections on port "+port);
		ServerSocketChannel serverChannel;
		Selector selector;
		
		try {
			serverChannel = ServerSocketChannel.open();
			ServerSocket ss = serverChannel.socket();
			ss.bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		while(true){
			try {
				selector.select();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			while(iterator.hasNext()){
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					if(key.isAcceptable()){
						ServerSocketChannel server = (ServerSocketChannel)key.channel();
						SocketChannel client = server.accept();
						logger.debug("Accepted connection from "+client);
						client.configureBlocking(false);
						SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
						ByteBuffer buffer = ByteBuffer.allocate(4);
						buffer.putInt(0);
						buffer.flip();
						key2.attach(buffer);
					}else if(key.isWritable()){
						SocketChannel client = (SocketChannel)key.channel();
						ByteBuffer buffer = (ByteBuffer)key.attachment();
						if(!buffer.hasRemaining()){
							buffer.rewind();
							int value = buffer.getInt();
							buffer.clear();
							buffer.putInt(value+1);
							buffer.flip();
						}
						client.write(buffer);
					}
				} catch (Exception e) {
					key.cancel();
					try{
						key.channel().close();
					}catch (IOException ex) {
						
					}
				}
				
			}
		}
		
	}

}
