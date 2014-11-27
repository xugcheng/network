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

public class ChargenServer {
	public static Logger logger = Logger.getLogger("network");
	public static int DEFAULT_PORT = 19;
	
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		logger.debug("Listening for connections on port "+port);
		//初始化常见的可打印字节数组 : ' '--32 , '~'--126
		byte[] rotation = new byte[95*2];
		for(byte i=' ';i<='~';i++){
			rotation[i-' '] = i;
			rotation[i-' '+95] = i;
		}
		for(int i=0;i<rotation.length;i++){
			System.out.print((char)rotation[i]);
		}
		System.out.println();	
		//初始化服务器通道配置
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
			logger.error("init server channel failed :"+e);
			return;
		}
		//
		while(true){
			try {
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			
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
						ByteBuffer buffer = ByteBuffer.allocate(74);
						buffer.put(rotation,0,72);
						buffer.put((byte)'\r');
						buffer.put((byte)'\n');
						buffer.flip();
						key2.attach(buffer);
					}else if(key.isWritable()){
						SocketChannel client = (SocketChannel)key.channel();
						ByteBuffer buffer = (ByteBuffer)key.attachment();
						logger.debug("Writable connection from "+client);
						if(!buffer.hasRemaining()){
							logger.debug("buffer state"+buffer.toString());
							buffer.rewind();
							int first = buffer.get();
							logger.debug("get:"+buffer.get()+",getInt:"+buffer.getInt());
							buffer.rewind();
							int position = first - ' '+1;
							buffer.put(rotation,position,72);
							buffer.put((byte)'\r');
							buffer.put((byte)'\n');
							buffer.flip();
						}
						client.write(buffer);
					}
				} catch (IOException e) {
					logger.error(e);
					key.cancel();
					try {
						key.channel().close();
					} catch (Exception e2) {
						
					}
				}
			}//--end of while
		}//--end of while
		logger.debug("server stoped.");
	}

}
