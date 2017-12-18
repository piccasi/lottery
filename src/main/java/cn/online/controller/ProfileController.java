package cn.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.online.pojo.Profile;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.HhControllerMappings;
import com.tydic.uniform.hh.constant.HhUrlsMappings;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-12-8 上午11:21:17 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

@Controller
@RequestMapping(value = HhControllerMappings.PROFILE)
public class ProfileController {


	@RequestMapping(value = HhUrlsMappings.PROFILEVIEW, method = RequestMethod.POST)
	@ResponseBody
	//public String loginon(@RequestBody String str){
	public String view(@RequestBody Profile pro){
		String ret = JsonResponse.toSuccessResult(pro);

		return ret;
	}
}

