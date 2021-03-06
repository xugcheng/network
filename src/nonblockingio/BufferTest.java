package nonblockingio;

import java.nio.Buffer;
import java.nio.CharBuffer;

/**
 * 缓冲区测试
 * @author xugc
 *
 */
public class BufferTest {
	
	public static void main(String[] args) {
		CharBuffer charBuffer = CharBuffer.allocate(10);
		state(charBuffer);
		charBuffer.put('a');
		charBuffer.put('b');
		charBuffer.put('c');
		charBuffer.put('d');
		charBuffer.put('e');
		charBuffer.put('1');
		charBuffer.put('2');
		charBuffer.put('3');
		charBuffer.put('4');
		//charBuffer.put('5');
		charBuffer.flip();
		state(charBuffer);
		charBuffer.rewind();
		state(charBuffer);
		charBuffer.clear();
		state(charBuffer);
		char[] chars = "hijklmn789".toCharArray();
		charBuffer.put(chars);
		charBuffer.flip();
		System.out.println("src:");
		state(charBuffer);
		CharBuffer duplicate = charBuffer.duplicate();
		System.out.println("duplicate:");
		state(duplicate);
		duplicate.put('x');
		System.out.println("des.put('c')");
		System.out.println("src:");
		state(charBuffer);
		System.out.println("duplicate:");
		state(duplicate);
		CharBuffer slice = duplicate.slice();
		System.out.println("slice:"+slice);
	}
	
	public static void state(Buffer buffer){
		System.out.println(buffer+"[position:"+buffer.position()+",limit:"+buffer.limit()+"]");
	}

}
