package cn.online.controller;

import java.util.HashMap;
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

@Controller
@RequestMapping(value = "health")
public class SearchController {
	@Resource
	private DiseaseSearchImpl diseaseSearch;
	
	@Resource
	private MedicineSearchImpl medicineSearch;
	
	@Resource
	private SymptomSearchImpl symptomSearch;

	@RequestMapping("/search")
	public String search(HttpServletRequest request,Model model){
		String type = request.getParameter("type");
		String keywords = request.getParameter("keywords");
		System.out.println("type: " + type);
		System.out.println("keywords: " + keywords);
		
		if (StringUtils.hasLength(type)) {
			BaseSearch baseSearch = new BaseSearch();
			baseSearch.setActionName("Pis_Get_Medical_Info");
			baseSearch.setKeyWords(keywords);
			baseSearch.setType(type);
			model.addAttribute("type", type);
			String placeholder = "请输入病症"; 
			if("1".equals(type)){
				placeholder = "请输入疾病名称"; 
				List<Map<String, String>> list = null;
				System.out.println("come 1");
				if(StringUtils.hasLength(keywords)){
					System.out.println("come 2");
					list = diseaseSearch.search(baseSearch);
				}
				System.out.println("come 3");
				
				model.addAttribute("diseases", list);
			} else if("2".equals(type)){
				placeholder = "请输入药品名称"; 
				List<Map<String, String>> list = null;
				if(StringUtils.hasLength(keywords)){
					list = medicineSearch.search(baseSearch);
				}
				model.addAttribute("medicines", list);
			} else if("3".equals(type)){
				List<String> list = null;
				Map<String, Map<String, String>> disMap = new HashMap<String, Map<String, String>>();
				if(StringUtils.hasLength(keywords)){
					list = symptomSearch.search(baseSearch);
					
					for(String dis : list){
						BaseSearch bs = new BaseSearch();
						bs.setKeyWords(dis);
						bs.setType("1");
						List<Map<String, String>> ret =  diseaseSearch.search(bs);
						disMap.put(dis, ret.get(0));
					}
				}
				model.addAttribute("symptoms", disMap);
			} else if("4".equals(type)){
				return "analysis";
			}
			model.addAttribute("placeholder", placeholder);
			return "search";
		}
		return "error";
	}
}
