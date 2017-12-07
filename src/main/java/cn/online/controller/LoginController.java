package cn.online.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import cn.online.pojo.LogonInVo;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-17 下午11:36:07 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

@Controller
@RequestMapping(value = "portal")
public class LoginController {


	@RequestMapping("/login2")
	@ResponseBody
	//public String loginon(@RequestBody String str){
	public String loginon(@RequestBody LogonInVo str){
		String ret = JsonResponse.toSuccessResult(null);

		return ret;
	}
}
