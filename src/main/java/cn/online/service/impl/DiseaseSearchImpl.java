package cn.online.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.online.controller.BaseSearchController;
import cn.online.pojo.BaseSearch;
import cn.online.service.IDiseaseSearch;
import cn.online.service.Search;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-4-20 下午9:06:33 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

@Service("diseaseSearch")
public class DiseaseSearchImpl extends AbstractSearch {

	//@Resource
	//private JdbcTemplate jdbcTemplate;

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
	
	public String searchByDiseaseDes() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return BaseSearchController.baseSearch();
	}
	
	public static void main(String[] args){
		BaseSearch baseSearch = new BaseSearch();
		baseSearch.setKeyWords("乙肝");
		baseSearch.setType("1");
		DiseaseSearchImpl symptomSearchImpl = new DiseaseSearchImpl();
		String ret = symptomSearchImpl.callBackend(baseSearch);
		
		List<Map<String, String>> ll = null;
		try {
			ll = symptomSearchImpl.convertTclToObject(Map.class,  ret);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		int count = 1;
		for(Map<String, String> map : ll){
			System.out.println("---------第 " + count + " 条------------" );
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
			}
			++count;
		}
		//System.out.println("ret: " + ret);
	}
}
