package cn.online.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.online.pojo.BaseSearch;
import cn.online.service.impl.DiseaseSearchImpl;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-17 下午11:36:07 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

//@Controller
//@RequestMapping(value = "disease")
public class DiseaseSearchController {
	//@Resource
	private DiseaseSearchImpl diseaseSearch;

	//@RequestMapping("/search")
	public String search(HttpServletRequest request,Model model){
		
		String keywords = request.getParameter("keywords");
		System.out.println("keywords: " + keywords);
		List<Map<String, String>> list = null;
		if(StringUtils.hasLength(keywords)){
			BaseSearch baseSearch = new BaseSearch();
			baseSearch.setKeyWords(keywords);
			baseSearch.setType("1");
			list = diseaseSearch.search(baseSearch);
			
			model.addAttribute("diseases", list);
		} else {
			model.addAttribute("diseases", list);
		}
		return "diseases";
	}
}
