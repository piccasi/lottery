package api.gateway.handlerinterface;

import java.util.Map;

import api.gateway.common.CommonResponse;



public interface GatewayHandler {
	public void validParams(String[] requireds, Map<String, String> params) throws Exception;
	public void checkSign(String[] signs, Map<String, String> params) throws Exception;
	public abstract String handler(String service, Map<String, String> params,String [] resStrs,String [] reqStrs) throws Exception;
}
