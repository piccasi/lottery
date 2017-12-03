package cn.online.socket;
/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 上午10:41:23 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
 
public class TCPClientB {
    public static void main(String[] args) {
        Socket s=null;
        BufferedReader br=null;
        PrintWriter pw=null;
        try {
            s=new Socket("120.77.44.145", 7979);
            BufferedReader brin=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("please input your words:");
            String str=brin.readLine();
             
            pw=new PrintWriter(s.getOutputStream(), true);
            pw.println(str);
            pw.flush();
             
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            str=br.readLine();
            System.out.println(str);
        }  catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br!=null)try{br.close();}catch(IOException e){}
            if(s!=null)try{s.close();}catch(IOException e){}
        }
 
    }
 
}
