/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author Administrator
 * @date: 2015年10月9日 下午2:35:38
 * @Title: Test.java
 * @Package com.tydic.uniform.hh.service.impl
 * @Description: TODO
 */
package com.tydic.uniform.hh.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tydic.uniform.hh.service.interfaces.LogonInServiceServ;
import com.tydic.uniform.hh.service.interfaces.RegisterServiceServ;
import com.tydic.uniform.hh.vo.rep.LogonInVo;
import com.tydic.uniform.hh.vo.rep.RegisterVo;


/**
 * <p></p>
 * @author Administrator 2015年10月9日 下午2:35:38
 * @ClassName Test
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月9日
 * @modify by reason:{方法名}:{原因}
 */
public class Test {
	
	/**
	 * @author Administrator 2015年10月9日 下午2:35:38
	 * @Method: main 
	 * @Description: TODO
	 * @param args 
	 * @throws 
	 */
	
	public static void main(String[] args) {
		//LogonInServiceServ lo=new LogonInServiceImpl();
		//LogonInVo vo=new LogonInVo();
//		RegisterVo vo=new RegisterVo();
//		RegisterServiceServ re= new RegisterServiceImpl();
//		vo.setChannel_code("01");
//		vo.setExt_system("10002");
//		/*vo.setLogin_nbr("18716752345");
//		vo.setLogin_type("1");
//		vo.setPwd("e99a18c428cb38d5f260853678922e03");
//		lo.Logonin(vo);*/
//		vo.setCust_name("王小二");
//		vo.setPwd("e99a18c428cb38d5f260853678922e03");
//		vo.setService_nbr("15000010020");
//		re.register(vo);
		
		Date dateTime = new Date();                      
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
		String dateString = formatter.format(dateTime);  
		System.out.println(dateString);
	}
}
