package nonblockingio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    public static void main(String args[]){
        String hostname = "localhost";
        int port = 7;
        if(args.length > 0){
            hostname = args[0];
        }
        
        //定义一系列的变量
        PrintWriter  out = null;   //用于向网络写数据
        BufferedReader in = null;  //用于向网络读数据
        Socket socket = null;
        BufferedReader userIn = null; //用于获取键盘输入
        
        try {
            //实例化相应的变量
            socket = new Socket(hostname,port);    
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            userIn = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("connect to echo Server" + hostname);
            
            while(true){
                String theLine = userIn.readLine();
                if(theLine.equals(".")){
                    break;
                }
                out.println(theLine);
                System.out.println(in.readLine());
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.close();
    }
}