package udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPPoke {
	
	private int bufferSize = 8192;
	private DatagramPacket packet;
	private DatagramSocket socket;
	
	public UDPPoke(InetAddress address,int port,int bufferSize,int timeout) throws SocketException{
		packet = new DatagramPacket(new byte[1], 1, address, port);
		packet.setData("1".getBytes());
		this.bufferSize = bufferSize;
		socket = new DatagramSocket(0);
		socket.connect(address, port);
		socket.setSoTimeout(timeout);
	}
	
	public UDPPoke(InetAddress address,int port ,int bufferSize) throws SocketException{
		this(address, port, bufferSize, 30000);
	}
	
	public UDPPoke(InetAddress address,int port) throws SocketException{
		this(address, port, 8192,30000);
	}
	
	public byte[] poke(){
		byte[] response = null;
		try {
			socket.send(packet);
			DatagramPacket in = new DatagramPacket(new byte[bufferSize], bufferSize);
			socket.receive(in);
			int numBytes = in.getLength();
			response = new byte[numBytes];
			System.arraycopy(in.getData(), in.getOffset(), response, 0, response.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String hostname = "localhost";
		InetAddress host = null;
		int port = 9;
		
		try {
			host = InetAddress.getByName(hostname);
			UDPPoke poke = new UDPPoke(host, port);
			byte[] response = poke.poke();
			if(response == null){
				System.err.println("No response.");
				return;
			}
			String result = "";
			try {
				result = new String(response,"ASCII");
			} catch (UnsupportedEncodingException e) {
				result = new String(response,"8859_1");
			}
			System.out.println("result:"+result);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
