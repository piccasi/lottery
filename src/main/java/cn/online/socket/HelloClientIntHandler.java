package cn.online.socket;

import io.netty.buffer.ByteBuf;  
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 上午8:23:26 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {  
    private static Logger logger = LoggerFactory.getLogger(HelloClientIntHandler.class);  
  
    // 接收server端的消息，并打印出来  
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        logger.info("HelloClientIntHandler.channelRead");  
        ByteBuf result = (ByteBuf) msg;  
        byte[] result1 = new byte[result.readableBytes()];  
        result.readBytes(result1);  
        System.out.println("Server said:" + new String(result1));  
        result.release();  
    }  
  
    // 连接成功后，向server发送消息  
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        logger.info("HelloClientIntHandler.channelActive");  
        String msg = "Are you ok?"; 
        msg = "00000198Pis_Get_Medical_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE ROLLBACK}} { {KEY_WORDS {houlongtong} } {TYPE 2} }";
        
	    /*String reqData;// = "喉咙痛";
	    String keyword = "蒲地蓝";
	    String type = "2";
	    String reqHeaderString = "Pis_Get_Medical_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE ROLLBACK}} { {KEY_WORDS {" + keyword + "} } {TYPE " + type + "} }";
	    
	    String padding = "";
	    String length = reqHeaderString.length() + "";
	    if(length.length() < 8){
	    	for(int i = 8 - length.length(); i > 0; --i ){
	    		padding += "0";
	    	}
	    }
	    msg = padding + (reqHeaderString.length() + 8) + reqHeaderString;*/
        
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());  
        encoded.writeBytes(msg.getBytes());  
        ctx.write(encoded);  
        ctx.flush();  
    }  
}
