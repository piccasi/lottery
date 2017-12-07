package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.Judge170NumberVo;
import com.tydic.uniform.hh.vo.rep.JudgeSameMemberVo;

public interface Judge170NumberServiceServ {

	Map<String, Object> Judge170Numberserviceserv(Judge170NumberVo judge170numbervo);
	Map<String, Object> judgeSameMemberserv(JudgeSameMemberVo judgeSameMemberVo);
}
