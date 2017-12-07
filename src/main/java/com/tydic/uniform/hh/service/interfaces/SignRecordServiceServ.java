package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;


import com.tydic.uniform.hh.vo.rep.SignRecordVo;

/**
 * <p></p>
 * @author fangxiangxiang 2015年11月17日 
 * @ClassName SignRecordServiceServ  签到记录接口
 */


public interface SignRecordServiceServ {
	 public Map<String,Object> Signrecord(SignRecordVo signrecordvo);
}
