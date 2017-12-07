package api.gateway.test;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tcl.BafAuthInfo;
import com.tcl.CommonResult;
import com.tcl.ListBafVariantsImpl;
import com.tcl.MapBafVariantsImpl;
import com.tcl.OpMode;
import com.tcl.SocketTradeAction;
import com.tcl.StandBafVariants;
import com.tcl.TclMsg;

import common.TradeManager;



public class TestTcl {
public static void main(String[] args) {
/*	String tclStr = "Pis_Get_Medical_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE ROLLBACK}} { {KEY_WORDS {蒲地蓝} } {TYPE 2} }";
	SocketTradeAction.setBAF_IP("120.77.44.145");
	SocketTradeAction.setBAF_PORT(7979);
	String ret = SocketTradeAction.executeBaf(tclStr);*/
	CommonResult commonResult = new CommonResult();
	 TradeManager manager = new TradeManager("10011", OpMode.ROLLBACK,new BafAuthInfo("A","A00","22342","43643"), commonResult,null,null);
	 ListBafVariantsImpl bafVariants = new ListBafVariantsImpl();
     bafVariants.addParameter("KEY_WORDS", "蒲地蓝");
     bafVariants.addParameter("TYPE", "2");
 
    

     manager.addAction("Pis_Get_Medical_Info", bafVariants) ;
    String iii= manager.getTclStr();
    System.out.println(iii);
    SocketTradeAction.setBAF_IP("120.77.44.145");
	SocketTradeAction.setBAF_PORT(7979);
	String ret = SocketTradeAction.executeBaf(iii);
	 System.out.println(ret);
	 
	 StandBafVariants result = MapBafVariantsImpl.getInstance(ret);
		
		ret = result.getParameter("RESULT");
		System.out.println("ret1: " + ret);
		System.out.println("ret2: " + ret.substring(1, ret.length() - 1));
		List<Map<String,String>> mapList=getSearchResult(ret);
		for(int i=0;i<mapList.size();i++){
			Map map=mapList.get(i);
			System.out.println("name: " + map.get("name"));
			System.out.println("desc: " + map.get("desc"));
			System.out.println("img: " + map.get("img"));
		}
    
}
public static List<Map<String,String>> getSearchResult(String str){
	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	if (str != null) {
		TclMsg tclmsg = new TclMsg(str);
		int msgLength = tclmsg.getLength();
		for (int i = 0; i < msgLength; i++) {
			Map map = new HashMap();
			
			TclMsg tmp = tclmsg.getTclMsg(i);
			String ttString = tmp.toTclStr();
			System.out.println("ttString: " + ttString);
			
			map.put("name", MapBafVariantsImpl.getInstance(ttString).getParameter("DRUG_NAME"));
			map.put("desc", MapBafVariantsImpl.getInstance(ttString).getParameter("DRUG_DESC"));
			map.put("img", MapBafVariantsImpl.getInstance(ttString).getParameter("DRUG_IMG"));
			list.add(map);
		}
	}
	return list;
}
}
