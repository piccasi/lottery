package api.gateway.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcl.BafAuthInfo;
import com.tcl.CommonResult;
import com.tcl.ListBafVariantsImpl;
import com.tcl.MapBafVariantsImpl;
import com.tcl.OpMode;
import com.tcl.SocketTradeAction;
import com.tcl.StandBafVariants;
import com.tcl.TclMsg;

import common.TradeManager;

public class BafUtil {
public static List<Map<String,String>> executeBaf(String service, Map<String, String> params,String [] resStrs,String [] reqStrs){
	Map<String,String> bafIpAndPortMap=	ControllerUtils.getBafIpAndPort();
	String ip=bafIpAndPortMap.get("baf.objectFactory.socket.url");
	String port=bafIpAndPortMap.get("baf.objectFactory.socket.port");
	CommonResult commonResult = new CommonResult();
	TradeManager manager = new TradeManager("10011", OpMode.ROLLBACK,new BafAuthInfo("A","A00","22342","43643"), commonResult,null,null);
	manager.removeAllAction();
	ListBafVariantsImpl bafVariants = new ListBafVariantsImpl();
	for(int i=0; i<resStrs.length;i++){
		bafVariants.addParameter(resStrs[i], params.get(resStrs[i]));
	}
	//manager.addAction(params.get("SUBJECT"), bafVariants);
	manager.addAction(service, bafVariants) ;
	SocketTradeAction.setBAF_IP(ip);
	SocketTradeAction.setBAF_PORT(Integer.valueOf(port));
	String bafTcl=manager.getTclStr();
	String ret = SocketTradeAction.executeBaf(bafTcl);
	StandBafVariants result = MapBafVariantsImpl.getInstance(ret);
	ret = result.getParameter("ROBOT_OUTPUT");
	List<Map<String,String>> bafRetList=getBafResult(ret,reqStrs);
	return bafRetList;
}
public static List<Map<String,String>> getBafResult(String str,String[] reqStrs){
	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	if (str != null) {
		TclMsg tclmsg = new TclMsg(str);
		int msgLength = tclmsg.getLength();
		for (int i = 0; i < msgLength; i++) {
			Map map = new HashMap();
			
			TclMsg tclMsgs = tclmsg.getTclMsg(i);
			String TclMsgStr = tclMsgs.toTclStr();
			System.out.println("TclMsgStr: " + TclMsgStr);
			for(int j=0;j<reqStrs.length;j++){
				map.put(reqStrs[j], MapBafVariantsImpl.getInstance(TclMsgStr).getParameter(reqStrs[j]));
			}
			list.add(map);
		}
	}
	return list;
}
}
