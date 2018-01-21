package api.gateway.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcl.SocketTradeAction;
import com.tydic.uniform.common.vo.resp.JsonResponse;

import api.gateway.base.GatewayAbstractHandler;
import api.gateway.exception.HandlerException;
import api.gateway.exception.ParametersValidException;
import api.gateway.handlerinterface.GatewayHandler;
import api.gateway.util.BafUtil;




@Component
public class CommonHandler extends GatewayAbstractHandler  implements GatewayHandler{
	private static final Log logger = LogFactory.getLog(CommonHandler.class);


	@Override
	public void validParams(String[] requireds, Map<String, String> params) throws Exception {
		super.validParams(requireds, params);
		
	}
	@Override
	public String handler(String service, Map<String, String> params,String [] resStrs,String [] reqStrs) throws Exception {
		//final CommonResponse response = new CommonResponse();
		List<Map<String,String>> bafList;
        try{
/*        	{
        		Map<String, String> tmpMap = new HashMap<String, String>();
        		tmpMap.put("key", "哈哈");
        		tmpMap.put("name", "海豚医生");
        		List<Map<String,String>> tmpList = new ArrayList<Map<String,String>>();
        		tmpList.add(tmpMap);
        		bafList = tmpList;
        	}*/
        	bafList=BafUtil.executeBaf(service, params, resStrs, reqStrs);
		
		//response.setData(bafList);
        }catch (final Exception e) {
			throw new HandlerException(e.getMessage());
		}
        
        logger.info("bafList: " + bafList);
		//return response;
        return JsonResponse.toSuccessResult(bafList);
		
	}

	
	
}
