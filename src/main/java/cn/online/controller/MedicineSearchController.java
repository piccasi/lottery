package cn.online.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.online.pojo.BaseSearch;
import cn.online.service.impl.MedicineSearchImpl;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-17 下午11:36:07 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

//@Controller
//@RequestMapping(value = "medicine")
public class MedicineSearchController {
	//@Resource
	private MedicineSearchImpl medicineSearch;

	//@RequestMapping("/search")
	public String search(HttpServletRequest request,Model model){
		String keywords = request.getParameter("keywords");
		System.out.println("keywords: " + keywords);
		BaseSearch baseSearch = new BaseSearch();
		baseSearch.setKeyWords("蒲地蓝");
		baseSearch.setType("2");
		List<Map<String, String>> list = medicineSearch.search(baseSearch);
		
		model.addAttribute("medicines", list);
		return "medicines";
	}
}
