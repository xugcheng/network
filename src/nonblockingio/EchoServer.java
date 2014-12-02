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

public class EchoServer {
	
	public static int DEAFULT_PORT = 7;
	public static Logger logger = Logger.getLogger("EchoServer");
	
	public static void main(String[] args) {
		int port = DEAFULT_PORT;
		logger.debug("Listening on the port "+port);
		
		ServerSocketChannel serverChannel;
		Selector selector;
		
		try {
			serverChannel = ServerSocketChannel.open();
			selector = Selector.open();
			ServerSocket ss = serverChannel.socket();
			ss.bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
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
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						logger.debug("Accepted connection from "+client);
						client.configureBlocking(false);
						SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
						ByteBuffer buffer = ByteBuffer.allocate(10);
						clientKey.attach(buffer);
					}
					else if(key.isReadable()){
						SocketChannel client = (SocketChannel)key.channel();
						ByteBuffer buffer =(ByteBuffer)key.attachment();
						logger.debug("Read Buffer1:"+buffer);
						int n=client.read(buffer);
						logger.debug("Read Buffer2:"+buffer+",n:"+n);
					}
					else if(key.isWritable()){
						SocketChannel client = (SocketChannel)key.channel();
						ByteBuffer buffer =(ByteBuffer)key.attachment();
						buffer.flip();
						client.write(buffer);
						buffer.compact();
					}
				} catch (Exception e) {
					logger.debug("就绪连接处理异常."+e);
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e1) {
						
					}
				}
			}
		}
		
	}

}
