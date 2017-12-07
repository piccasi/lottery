package api.gateway.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public CommonResponse handler(Map<String, String> params,String [] resStrs,String [] reqStrs) throws Exception {
		final CommonResponse response = new CommonResponse();
        try{
		List<Map<String,String>> bafList=BafUtil.executeBaf(params, resStrs, reqStrs);
		
		response.setData(bafList);
        }catch (final Exception e) {
			throw new HandlerException(e.getMessage());
		}
		return response;
		
	}

	
	
}
