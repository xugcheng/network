package securesocket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SecureOrderTaker {
	public final static int DEFAULT_PORT = 443;
	public final static String algorithm = "SSL";
	
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		
		try {
			SSLContext context = SSLContext.getInstance(algorithm);
			//参考实现只支持X.509密钥
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			//sun的默认密钥库类型
			KeyStore ks = KeyStore.getInstance("JKS");
			//
			char[] password = "123456".toCharArray();
			ks.load(new FileInputStream("resource/jnp3e.keys"), password);
			kmf.init(ks, password);
			//
			context.init(kmf.getKeyManagers(), null, null);
			SSLServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket server = (SSLServerSocket)factory.createServerSocket(port);
			//
			String[] supported = server.getSupportedCipherSuites();
			String[] anonCipherSuitesSupported = new String[supported.length];
			int numAnonCipherSuitesSupported = 0;
			for(int i=0;i<supported.length;i++){
				if(supported[i].indexOf("_anon_")>0){
					anonCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
				}
			}
			String[] oldEnabled = server.getEnabledCipherSuites();
			String[] newEnabled = new String[oldEnabled.length+numAnonCipherSuitesSupported];
			System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
			System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSupported);
			server.setEnabledCipherSuites(newEnabled);
			try {
				while(true){
					Socket conn = server.accept();
					InputStream in = conn.getInputStream();
					int c;
					while((c=in.read())!=-1){
						System.out.write(c);
					}
					conn.close();
				}
			} catch (IOException e) {
				
			}
			
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
