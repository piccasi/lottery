package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.FirstInstallLiftSmsVo;
import com.tydic.uniform.hh.vo.rep.FirstInstallLiftVo;


public interface FirstInstallLiftServ {

	Map<String, Object> getFirstInstallLiftSmsServ(FirstInstallLiftSmsVo firstInstallLiftSmsVo);
	Map<String, Object> validateFirstInstallLiftSmsServ(FirstInstallLiftVo firstInstallLiftVo);
}
