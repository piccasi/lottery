package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;


import com.tydic.uniform.hh.vo.rep.SignVo;

/**
 * <p></p>
 * @author fangxiangxiang 2015年11月18日 
 * @ClassName SignRecordServiceServ  签到接口
 */


public interface SignServiceServ {
	 public Map<String,Object> Sign(SignVo signvo);
}
