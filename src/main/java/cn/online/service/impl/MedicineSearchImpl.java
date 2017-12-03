package cn.online.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.online.pojo.BaseSearch;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-9 下午10:59:53 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service("medicineSearch")
public class MedicineSearchImpl extends AbstractSearch  {
	public List<Map<String, String>> search(BaseSearch baseSearch){
		String ret = callBackend(baseSearch);
		
		List<Map<String, String>> retList = null;
		try {
			retList = convertTclToObject(Map.class,  ret);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		int count = 1;
		for(Map<String, String> map : retList){
			System.out.println("---------第 " + count + " 条------------" );
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
			}
			++count;
		}
		return retList;
	}
	

	public static void main(String[] args){
		BaseSearch baseSearch = new BaseSearch();
		baseSearch.setKeyWords("蒲地蓝");
		baseSearch.setType("2");
		MedicineSearchImpl symptomSearchImpl = new MedicineSearchImpl();
		symptomSearchImpl.search(baseSearch);

		//System.out.println("ret: " + ret);
	}

}
