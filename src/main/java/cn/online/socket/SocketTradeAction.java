package cn.online.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.online.socket.SystemException;;

/**
 * ��b���� : Dec 17, 2008 2:36:01 PM<br>
 * ���� : yaoq<br>
 * ģ�� : <br>
 * ���� : <br>
 * �޸���ʷ: ��� ���� �޸��� �޸�ԭ�� <br>
 * 1 <br>
 * 2 <br>
 */
public class SocketTradeAction implements Action {
    private static final Log logger = LogFactory.getLog(SocketTradeAction.class);

    private static int BAF_PORT;
    private static String BAF_IP;

    public String execute(String serviceName, String inMessage) {
        return executeBaf(inMessage);
    }

    private static int readBytes(InputStream in, byte[] buf, int offset, int minimum, int maxLen) throws IOException {
        int ptr = in.read(buf, offset, maxLen);
        if (ptr < minimum) {
            if (ptr == -1) {
                throw new SystemException("9123", "EOS reached. No data available");
            }
            while (ptr < minimum) {
                int count = in.read(buf, offset + ptr, maxLen - ptr);
                if (count < 0) {
                    throw new SystemException("9123", "EOS reached. No data available");
                }
                ptr += count;
            }
        }
        return ptr;
    }

    /**
     * 
     * @return ���ؽ��tcl��
     */
    public static String executeBaf(final String tclStr) {
        if (BAF_PORT == 0 || BAF_IP == null) {
            throw new SystemException("9123", "SocketTradeAction,��������BAF_PORT��BAF_IP");
        }

        OutputStream out = null;
        InputStream in = null;
        Socket socket = null;

        try {
            String sendMessage = getSendMessage(tclStr);
            logger.info("发送数据长度: " + sendMessage.length());
            logger.info("发送数据: " + sendMessage);

            try {
                socket = new Socket(BAF_IP, BAF_PORT);
            }
            catch (Exception e) {
                String errMess = String.format("IP:%s PORT:%s ERR:%s", BAF_IP, BAF_PORT, e.getMessage());
                logger.error(errMess);
                throw new SystemException("9878", errMess, e);
            }

            socket.setSoTimeout(30000);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            out.write(sendMessage.getBytes("GBK"));
            System.out.println("zhoumz===================");
            //System.out.println(sendMessage.getBytes());
            System.out.println(sendMessage);
            System.out.println("zhoumz222===================");
            // ��ȡ���ذ�
            byte[] hBytes = new byte[8];// ��ͷ��Ϣ
            readBytes(in, hBytes, 0, 8, 8);

            String hInfo = new String(hBytes);
            logger.info("返回header数据：" + hInfo);

            int blen = Integer.parseInt(hInfo) - 8;
            System.out.println("blen: " + blen );

            byte[] bBytes = new byte[blen];// ������Ϣ
            readBytes(in, bBytes, 0, blen, blen);

            String bInfo = new String(bBytes, "GBK");// ���崮
            logger.info("返回body长度：" + bInfo.length());
            logger.info("返回body数据：" + bInfo);
            String retclpack = bInfo;
            boolean error = isError(retclpack);

            if (error) {
                throw new SystemException("9878", retclpack);
            }

            return retclpack;
        }
        catch (SocketException e) {
            throw new SystemException("9878", "����baf��socketl�Ӵ���", e);
        }
        catch (SocketTimeoutException e) {
            throw new SystemException("9879", "socketl�ӳ�ʱ", e);
        }
        catch (IOException e) {
            throw new SystemException("9878", "io errror", e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (Exception e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (Exception e) {
                }
            }
        }

    }

    private static String getSendMessage(final String tclStr) {
    	String sendStr = "";
    	try {
			sendStr   =   java.net.URLEncoder.encode(tclStr,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*try {
			sendStr = new String(tclStr.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
    	
        int tclpacklen = 0;
		try {
			tclpacklen = tclStr.getBytes("GBK").length + 8;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("tclpacklen: " + tclpacklen);
        StringBuilder sendMessage = new StringBuilder(String.format("%08d%s", tclpacklen, tclStr));

        return sendMessage.toString();
    }

    /**
     * ����tcl�ķ��ر���
     * 
     * @param strReTcl
     * @return
     */
    public static boolean isError(final String strReTcl) {
        String retcl = strReTcl.trim();
        return retcl.startsWith("BMS-") || retcl.startsWith("ACC-");
    }

    public static int getBAF_PORT() {
        return BAF_PORT;
    }

    public static void setBAF_PORT(int baf_port) {
        BAF_PORT = baf_port;
    }

    public static String getBAF_IP() {
        return BAF_IP;
    }

    public static void setBAF_IP(String baf_ip) {
        BAF_IP = baf_ip;
    }
}

