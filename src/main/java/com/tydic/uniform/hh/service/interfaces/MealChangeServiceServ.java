package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.MealChangeVo;

/**
 * <p></p>
 * @author Administrator 2015年10月13日 下午3:37:32
 * @ClassName MealChangeServiceServ
 * @Description 套餐变更
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月13日
 * @modify by reason:{方法名}:{原因}
 */
public interface MealChangeServiceServ {

	Map<String, Object> mealChangeService(MealChangeVo mealchangevo);

}
