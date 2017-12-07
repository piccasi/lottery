package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;


import com.tydic.uniform.hh.vo.rep.RegisterVo;
/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:40:06
 * @ClassName RegisterServiceServ 用户注册接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public interface RegisterServiceServ {
	Map<String,Object> register(RegisterVo registervo);
}
