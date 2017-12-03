package cn.online.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.online.pojo.BaseSearch;
import cn.online.service.impl.DiseaseSearchImpl;
import cn.online.service.impl.MedicineSearchImpl;
import cn.online.service.impl.SymptomSearchImpl;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-17 下午11:36:07 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

//@Controller
//@RequestMapping(value = "symptom")
public class SymptomSearchController {
	//@Resource
	private SymptomSearchImpl symptomSearch;

	//@RequestMapping("/search")
	public String search(HttpServletRequest request,Model model){
		BaseSearch baseSearch = new BaseSearch();
		baseSearch.setKeyWords("感冒、流鼻涕");
		baseSearch.setType("3");
		List<String> list = symptomSearch.search(baseSearch);
		
		model.addAttribute("symptoms", list);
		return "symptoms";
	}
}
