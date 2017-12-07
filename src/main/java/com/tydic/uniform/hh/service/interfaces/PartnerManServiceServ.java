package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.PartnerVo;

public interface PartnerManServiceServ {
	/**
	 * 全民营销-获取我赚的话费信息
	 * @param partnerVo
	 * @return
	 */
	Map<String,Object> getTelPhoneCharege(PartnerVo partnerVo);
	
	/**
	 * 查询数据库内排行榜信息
	 * @param partnerVo
	 * @return
	 */
	Map<String,Object> getQmyxRankList(PartnerVo partnerVo);
	
	/**
	 * 获取分享连接地址
	 * @param partnerVo
	 * @return
	 */
	Map<String,Object> getShareUrl(PartnerVo partnerVo);
	
	
	
}
