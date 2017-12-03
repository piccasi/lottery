package cn.online.socket;
/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 上午10:53:11 
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
import java.net.UnknownHostException;  
  
public class BugClient {  
    Socket soc;  
      
    public static void main(String[] args){  
        new BugClient().send();  
    }  
  
    private void send() {  
        try {  
            soc = new Socket("120.77.44.145", 7979);  
            PrintWriter socOut = new PrintWriter(soc.getOutputStream(),true);  
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
            BufferedReader netIn = new BufferedReader(new InputStreamReader(soc.getInputStream()));  
              
            String line = in.readLine();  
            System.out.println("input: "+line);  
            // 若使用了非 autoflush方式，即使显示flush的话，更是连服务端都收不到消息!  
              
            //由于消息没有"立即"传输到服务端， 会使得server在 阻塞在 in.readline()那一行.  
            socOut.println(line);  
            socOut.flush();  
              
            //若将此行注释掉，即客户段放弃接收服务段消息，  
            //那么这个客户段程序可以跑完，服务端将接收到消息，造成 socOut.flush()好像立即起了作用的假像  
            //实际上，并不是 flush()那行代码起了作用，而是程序结束时，对 out流做清理工作，使得消息最终发送了过去  
            /** 
             * 启用一下两行代码,由于 服务段已经被阻塞了，更别说响应，则客户段将阻塞在 netIn.readline(); 
             */  
            System.out.println(netIn.readLine());  
              
            soc.close();  
        } catch (UnknownHostException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}
