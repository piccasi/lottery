package cn.online.socket;


import io.netty.bootstrap.Bootstrap;  
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.ChannelOption;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioSocketChannel;  

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 上午8:10:45 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class HelloClient {  
  public void connect(String host, int port) throws Exception {  
      EventLoopGroup workerGroup = new NioEventLoopGroup();  

      try {  
          Bootstrap b = new Bootstrap();  
          b.group(workerGroup);  
          b.channel(NioSocketChannel.class);  
          b.option(ChannelOption.SO_KEEPALIVE, true);  
          b.handler(new ChannelInitializer<SocketChannel>() {  
              @Override  
              public void initChannel(SocketChannel ch) throws Exception {  
                  ch.pipeline().addLast(new HelloClientIntHandler());  
              }  
          });  

          // Start the client.  
          ChannelFuture f = b.connect(host, port).sync();          

          // Wait until the connection is closed.  
          f.channel().closeFuture().sync();  
      } finally {  
          workerGroup.shutdownGracefully();  
      }  

  }  

  public static void main(String[] args) throws Exception {  
      HelloClient client = new HelloClient();  
      client.connect("120.77.44.145", 7979);  
  }  
} 
