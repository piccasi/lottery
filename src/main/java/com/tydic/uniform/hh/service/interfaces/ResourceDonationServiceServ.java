package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.ResourceDonationVo;

/**
 * 
 * <p></p>
 * @author Administrator 2015年10月14日 上午11:47:04
 * @ClassName ResourceDonationServiceServ
 * @Description 资源转赠
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public interface ResourceDonationServiceServ {

	Map<String, Object> resourceDonation(ResourceDonationVo resourcedonationvo);

}
