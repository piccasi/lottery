package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.PasswordModifyVo;

/**
 * <p></p>
 * @author Administrator 2015年10月8日 下午3:41:37
 * @ClassName PasswordModifyServiceServ 用户密码修改或变更
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public interface PasswordModifyServiceServ {
	Map<String,Object> passwordModify(PasswordModifyVo passwordmodifyVo);
}
