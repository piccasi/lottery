package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.PlaceOrderVo;

public interface PlaceOrderServiceServ {

	Map<String, Object> placeorderserviceserv(PlaceOrderVo placeordervo);
	public Map submitOrder(Map req);
}
