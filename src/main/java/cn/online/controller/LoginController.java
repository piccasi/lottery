package cn.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.online.pojo.LogonInVo;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.HhControllerMappings;
import com.tydic.uniform.hh.constant.HhUrlsMappings;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-17 下午11:36:07 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

@Controller
@RequestMapping(value = HhControllerMappings.LOGONIN)
public class LoginController {


	@RequestMapping(value = HhUrlsMappings.LOGIN, method = RequestMethod.POST)
	@ResponseBody
	//public String loginon(@RequestBody String str){
	public String loginon(@RequestBody LogonInVo str){
		String ret = JsonResponse.toSuccessResult(null);

		return ret;
	}
}
