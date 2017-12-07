package com.tydic.uniform.hh.service.interfaces;

import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;

public interface IdentifyCodeService {
	public String sendCode(IdentifyCodeVo indentifyCodeVo);
	public String checkCode(IdentifyCodeVo indentifyCodeVo);
	public boolean checkCodeSync(IdentifyCodeVo ico);
	
}
