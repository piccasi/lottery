/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author Administrator
 * @date: 2015年10月14日 上午10:39:14
 * @Title: BillInfoServiceServ.java
 * @Package com.tydic.uniform.hh.service.interfaces
 * @Description: TODO
 */
package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.BillInfoVo;
import com.tydic.uniform.hh.vo.rep.HistoryBillInfoVo;
import com.tydic.uniform.hh.vo.rep.HistoryBillPwdVo;


/**
 * <p></p>
 * @author yiyaohong 2015年10月14日 上午10:39:14
 * @ClassName BillInfoServiceServ 会员账单查询服务接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public interface BillInfoServiceServ {
	Map<String,Object> billInfoService(BillInfoVo billinfovo);
	Map<String,Object> historyBillInfoService(HistoryBillInfoVo historyBillInfoVo);
	Map<String,Object> provPwd(HistoryBillPwdVo historyBillPwdVo);
}
