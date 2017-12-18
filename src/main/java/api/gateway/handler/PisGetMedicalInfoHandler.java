package api.gateway.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.gateway.base.GatewayAbstractHandler;
import api.gateway.common.CommonResponse;
import api.gateway.handlerinterface.GatewayHandler;




@Component
public class PisGetMedicalInfoHandler extends GatewayAbstractHandler  implements GatewayHandler{



	@Override
	public void validParams(String[] requireds, Map<String, String> params) throws Exception {
		super.validParams(requireds, params);
		final String phone = params.get("phone");
		if (StringUtils.isEmpty(phone) || !StringUtils.isNumeric(phone) || phone.length() != 11) {
			throw new Exception(String.format("参数[%s]不合法", "phone"));
		}
	}

	public String handler(String service, Map<String, String> params,String [] resStrs,String [] reqStrs) throws Exception {
		final CommonResponse response = new CommonResponse();
		
		
		
		

		final String phone = params.get("phone");

		response.setResponseMsg("11");
	
			return response.toString();
		
	}
}
