package com.tydic.uniform.hh.vo.resp;

import java.util.Map;

/**
 * <p></p>
 * @author ghp 2015年9月28日 下午5:29:26
 * @ClassName BaseResp  app出参的基类
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public class HhBaseResp {

	
	private Map<String,Object> mapInfo; 
	/**get/set方法-----------------------------------------------------------*/

	
    public Map<String, Object> getMapInfo() {
    	return mapInfo;
    }


	
    public void setMapInfo(Map<String, Object> mapInfo) {
    	this.mapInfo = mapInfo;
    }
}
