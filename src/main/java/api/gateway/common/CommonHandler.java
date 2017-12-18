package api.gateway.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tydic.uniform.common.vo.resp.JsonResponse;

import api.gateway.base.GatewayAbstractHandler;
import api.gateway.exception.HandlerException;
import api.gateway.exception.ParametersValidException;
import api.gateway.handlerinterface.GatewayHandler;
import api.gateway.util.BafUtil;




@Component
public class CommonHandler extends GatewayAbstractHandler  implements GatewayHandler{



	@Override
	public void validParams(String[] requireds, Map<String, String> params) throws Exception {
		super.validParams(requireds, params);
		
	}
	@Override
	public String handler(String service, Map<String, String> params,String [] resStrs,String [] reqStrs) throws Exception {
		//final CommonResponse response = new CommonResponse();
		List<Map<String,String>> bafList;
        try{
        	bafList=BafUtil.executeBaf(service, params, resStrs, reqStrs);
		
		//response.setData(bafList);
        }catch (final Exception e) {
			throw new HandlerException(e.getMessage());
		}
		//return response;
        return JsonResponse.toSuccessResult(bafList);
		
	}

	
	
}
