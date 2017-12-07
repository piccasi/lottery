package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.MessageCheckServiceServ;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.MessageCheckVo;

/**
 * <p></p>
 * @author ghp 2015年10月20日 下午8:36:17
 * @ClassName MessageCheckServiceImpl
 * @Description TODO  验证码校验
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月20日
 * @modify by reason:{方法名}:{原因}
 */
@Service("MessageCheckServiceServ")
public class MessageCheckServiceImpl extends AbstractService implements MessageCheckServiceServ{
	private static Logger log = Logger.getLogger(MessageCheckServiceImpl.class);

	@Override
	public Map<String, Object> accountorder(MessageCheckVo messagecheckvo) {
		// 生成验证码
		String validateCode =  messagecheckvo.getValidatecode();
		// 获取手机号
		String mobile = messagecheckvo.getNumber();
		//

		boolean flag  = ValidateCodeUtil.isSuccess(mobile, validateCode);
		Map<String, Object> map = new HashMap<String, Object>();
		   if(flag){
			   map.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
		    	
		    }else {
		    	map.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	map.put(HhConstants.MESSAGE, "验证码有误");//失败描述
		    }
		return map;
	}
	/**常量------------------------------------------------------------------*/

	/**类变量----------------------------------------------------------------*/

	/**get/set方法-----------------------------------------------------------*/
}
