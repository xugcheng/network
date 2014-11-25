package inetaddress;

public class SecurityManagerTest {
	public static void main(String[] args) {
		new SecurityManager().checkConnect("192.168.8.149", 8080);
	}

}
