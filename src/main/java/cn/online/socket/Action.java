package cn.online.socket;
/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 下午5:17:01 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface Action {
    public static String CHANNEL_PMS = "A1";
    public static String CHANNEL_BMS = "A2"; 
    public static String CHANNEL_REPORT = "A3";
    public String execute(String serviceName, String inMessage);
}