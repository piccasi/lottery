package com.tcl;

import com.tcl.CommonResult;
import com.tcl.BafAuthInfo;
import com.tcl.BafCommInfo;
import com.tcl.MapBafVariantsImpl;
import com.tcl.StandBafVariants;
import com.tcl.Constants;
import com.tcl.SystemException;
import com.tcl.SelfTest;

public class ZmzTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BafAuthInfo authInfo = new BafAuthInfo("A", "A00", "22342", "43643");//new
		CommonResult commonResult = new CommonResult();//new
		BafCommInfo commonInfo=new BafCommInfo("10011", "SUBMIT", authInfo);//new
		SelfTest manager=new SelfTest();
		manager.setCommInfo(commonInfo);//new
		manager.setCommonResult(commonResult);//new
		BmsUserSug bmsUserSugDef = new BmsUserSug();
        bmsUserSugDef.setRegionId("A");
        bmsUserSugDef.setServiceType("1001");
        bmsUserSugDef.setSubscriptionId("131234234");
        bmsUserSugDef.setRemark("zhoumz");
        manager.removeAllAction();//new
        manager.addAction(bmsUserSugDef);//new
      //  manager.addAction(bmsUserSugDef);
        
        if (manager.execute() != Constants.OK) {
            throw new Exception(manager.commonResult.getMessage());
        }
        StandBafVariants result = MapBafVariantsImpl.getInstance(manager.commonResult.getMessage());
        String account_id = result.getParameter("ACCOUNT_ID");
      
        System.out.println(account_id+"===========================zmz");
        
       
        /*String aa=manager.executeBaf(bmsUserSugDef);*/
        //System.out.println("zhoumz===============================");
    	//System.out.println(aa);
    	//StandBafVariants result = MapBafVariantsImpl.getInstance(aa);
		//String account_id = result.getParameter("ACCOUNT_ID");
		//result.addParameter("ZHOUMZ", "123456");
		//String zmz = result.getParameter("ZHOUMZ");
		//System.out.println(account_id+"    "+zmz);

	}

}
