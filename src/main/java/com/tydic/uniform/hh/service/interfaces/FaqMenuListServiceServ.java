package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.FaqListVo;
import com.tydic.uniform.hh.vo.rep.PreListVo;

/**
 * 
 * <p></p>
 * @author fengweimin 2015年11月20日 14:41:22
 * @ClassName FaqMenuListServiceServ
 * @Description TODO 订单查询接口
 * @version V1.0   
 */
public interface FaqMenuListServiceServ {


	Map<String, Object> getfaqmenulist(FaqListVo faqlistvo);

	Map<String, Object> getprelist(PreListVo prelistvo);

}
