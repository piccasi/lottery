/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author huangwx
 * @date: 2015年9月1日 下午5:40:42
 * @Title: Constants.java
 * @Package com.tydic.uniform.jd.constant
 * @Description: TODO
 */
package com.tydic.uniform.common.constant;

/**
 * <p>
 * </p>
 * @author huangwx 2015年9月1日 下午5:40:42
 * @ClassName Constants
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月1日
 * @modify by reason:{方法名}:{原因}
 */
public class Constants {

    /** -------------------------BOSS接口失败成功标记常量----------------------------------------- */
	public static String SUCCESS = "1";// 成功
	public static String ERROR = "0";// 失败
	/** -------------------------APP调用接口失败标记----------------------------------------- */
	public static String CODE = "CODE";// 编码
	public static String MESSAGE = "MESSAGE";// 信息
	public static String SUCCESSCODE = "0000";// 成功
	public static String SUCCESSMESSAGE = "成功!";// 失败
	public static String ERRORCODE = "9999";// 成功
	public static String ERRORMESSAGE = "系统出错，请联系管理员!";// 失败

    /**
     * 保存在session中的用户的key值
     */
    public static final String USER_SESSION_KEY = "_USER_SESSION_KEY_";

    public static final String XMLHTTP_REQUEST = "XMLHttpRequest";

}
