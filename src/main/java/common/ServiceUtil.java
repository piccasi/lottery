package common;

import com.tcl.BafAuthInfo;
import com.tcl.BafCommInfo;
import com.tcl.CommonResult;
import com.tcl.SelfTest;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-9 下午9:56:46 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class ServiceUtil {
	public static SelfTest getSelfTest() {
		BafAuthInfo authInfo = new BafAuthInfo("A", "A00", "22342", "43643");// new
		CommonResult commonResult = new CommonResult();// new
		BafCommInfo commonInfo = new BafCommInfo("10011", "SUBMIT", authInfo);// new
		SelfTest manager = new SelfTest();
		manager.setCommInfo(commonInfo);
		manager.setCommonResult(commonResult);
		return manager;
	}
}
