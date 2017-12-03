package cn.online.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.online.pojo.Medicine;
import cn.online.service.IDiseaseSearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-4-20 下午9:09:00 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

//@Controller
//@RequestMapping("disease")
//@RequestMapping(value = "disease")
public class DiseaseController {
	//@Resource
	private IDiseaseSearch diseaseSearch;
	
	//@RequestMapping("search")
	//@ResponseBody
	public String toIndex(HttpServletRequest request) throws UnsupportedEncodingException{
		//int userId = Integer.parseInt(request.getParameter("id"));
		String ret = diseaseSearch.searchByDiseaseDes();
		//model.addAttribute("user", user);
		System.out.println("toIndex ret: " + ret);
		return ret;
	}
	
	@RequestMapping("/show")
	public String toIndex(HttpServletRequest request,Model model) throws UnsupportedEncodingException{
		//int userId = Integer.parseInt(request.getParameter("id"));
		//User user = this.userService.getUserById(userId);
		String ret = diseaseSearch.searchByDiseaseDes();
		
		List<Medicine> medicines = new ArrayList<Medicine>();
		JSONArray jsonArray = JSONArray.parseArray(ret);
		Iterator<Object> iterator = jsonArray.iterator();
		while(iterator.hasNext()){
			Medicine medicine = new Medicine();
			JSONObject jsonObject = (JSONObject) iterator.next();
			medicine.setName((String)jsonObject.get("name"));
			medicine.setDesc((String)jsonObject.get("desc"));
			medicine.setImg((String)jsonObject.get("img"));
			medicines.add(medicine);
		}
		
		model.addAttribute("medicines", medicines);
		return "disease";
	}

}
