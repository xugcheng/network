package securesocket;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;

/**
 * SSL握手完成的事件处理器 ,异步编程接口.
 * @author xugc
 *
 */
public class MyHandshakeCompletedListener implements HandshakeCompletedListener {
	
	@Override
	public void handshakeCompleted(HandshakeCompletedEvent handshakecompletedevent) {
		System.err.println("handshakeCompleted:"+handshakecompletedevent.getSocket());
	}
	
}
