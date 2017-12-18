package cn.online.socket;
/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 上午9:24:16 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.OutputStream;
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.InetSocketAddress;
import java.net.Socket;  

  
public class MyClient {  
    private static final int SERVER_PORT = 7979;
	static Socket server; 
    static String IP ="120.77.44.145";
    //static String IP ="127.0.0.1";
  
    public static void main(String[] args) throws Exception {  
/*        server = new Socket();
		server.connect(new InetSocketAddress(IP, 7979), 30000);

        BufferedReader in = new BufferedReader(new InputStreamReader(  
                server.getInputStream()));  
        PrintWriter out = new PrintWriter(server.getOutputStream());  
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
        //while (true) {  
            //String str = wt.readLine();  
        String  str = "00000198Pis_Get_Medical_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE ROLLBACK}} { {KEY_WORDS {houlongtong} } {TYPE 2} }";

            out.println(str);  
            out.flush();  
            if (str.equals("end")) {  
                break;  
            }  
            System.out.println(in.readLine());  
        //}  
        server.close(); */ 
    	
    	Socket socket = new Socket(IP, SERVER_PORT);
    	System.out.print("success!");
    	// 发送命令
    	OutputStream socketOut = socket.getOutputStream();
        String  str = "00000198Pis_Get_Medical_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE ROLLBACK}} { {KEY_WORDS {houlongtong} } {TYPE 2} }";
    	socketOut.write(str.getBytes("UTF-8"));

    	// 接收服务器的反馈
    	BufferedReader br = new BufferedReader(new InputStreamReader(
    	socket.getInputStream(), "UTF-8"));
    	String ret = br.readLine();
    	System.out.println("Server return : " + ret);
    }  
} 