package urlconnectioncache;

import java.net.ResponseCache;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//自己实现URLConnection cache以后:先注册,然后JVM会自动使用
		ResponseCache.setDefault(new MyResponseCache());
		
	}

}
