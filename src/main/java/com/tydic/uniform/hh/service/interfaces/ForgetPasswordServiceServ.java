/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author Administrator
 * @date: 2015年10月12日 下午3:03:13
 * @Title: ForgetPasswordServiceServ.java
 * @Package com.tydic.uniform.hh.service.interfaces
 * @Description: TODO
 */
package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.ForgetPasswordVo;


/**
 * <p></p>
 * @author yiyaohong 2015年10月12日 下午3:03:13
 * @ClassName ForgetPasswordServiceServ 忘记密码接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月12日
 * @modify by reason:{方法名}:{原因}
 */
public interface ForgetPasswordServiceServ {
	Map<String,Object> forgetPasswordService(ForgetPasswordVo forgetpasswordvo);
}
