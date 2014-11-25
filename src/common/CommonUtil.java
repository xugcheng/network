package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommonUtil {
	
	public static String scan(String label) throws Exception
	{
		System.out.print(label);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			return br.readLine();
		}finally
		{
			br.close();
		}
		
		
	}
	
}
