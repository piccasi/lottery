package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.PrivilegeDeductServiceVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeInfoVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeListVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeQrcodeVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeSmsVo;

/**
 * 
 * <p></p>
 * @author panxinxing 2015年11月16日 上午11:35:58
 * @ClassName PrivilegeListServiceServ
 * @Description TODO 航空特权查询接口
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月16日
 * @modify by reason:{方法名}:{原因}
 */
public interface PrivilegeListServiceServ {

	Map<String, Object> getPrivilegeList(PrivilegeListVo privilegeListVo);
	Map<String, Object> getPrivilegeInfo(PrivilegeInfoVo privilegeInfoVo);
	Map<String, Object> getPrivilegeQrcode(PrivilegeQrcodeVo privilegeQrcodeVo);
	Map<String, Object> provPrivilegeSms(PrivilegeSmsVo privilegeSmsVo);
	Map<String, Object> deductServiceInfo(PrivilegeDeductServiceVo privilegeDeductServiceVo);
	Map<String, Object> queryQrcodeDeduct(PrivilegeSmsVo privilegeSmsVo);
}
