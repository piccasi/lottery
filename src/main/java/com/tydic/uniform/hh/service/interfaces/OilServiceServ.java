package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

public interface OilServiceServ {

	public String query(Map<String, String> map);

	public String order(String mobile, String code, String ofrId,String org_id);

}
