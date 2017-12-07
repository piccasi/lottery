package com.tydic.uniform.common.vo.resp;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.util.StringUtil;


/**
 * 返回报文工具类
 * @author fusionZ
 *
 */
public class JsonResponse {
	private String json = null;
	public String getMessage( ) {
		return MSG;
	}
	public int getCode( ) {
		return code;
	}
	public boolean isSuccess( ) {
		return STATUS == 0;
	}
	
	public Map<Object, Object> getDataMap( ) {
		return dataMap;
	}

	private String MSG = null;
	private int code = 0;
	private int STATUS = 0;
	private Map<Object, Object> dataMap = null;
	
	private JsonResponse(){}
	
	private JsonResponse(String response){
		this.json = response;
		parseResult();
	}
	
	public static JsonResponse newInstance(String response) {
		return new JsonResponse(response);
	}
	
	public enum SUCCESS{
		OK {
			public int value(){
				return 0;
			};
		},FAILURE {
			@Override
			public int value( ) {
				return 1;
			}
		};
		abstract public int value();
	};	
	
	
	/** 获得错误代码 */
	private static HashMap<Integer, CODE> map = new HashMap<Integer, CODE>();
	static {
		for (CODE err : CODE.values())
			map.put(err.value(), err);
	}
	
	public static CODE getByCode(int code) {
		return map.get(code);
	}
	
	/**
	 * 返回结果jso串
	 * @param success
	 * @param code
	 * @param parameter
	 * @param result
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String toResult(SUCCESS success,CODE code,String parameter,Object result){
		LinkedHashMap<String, Object> ret = new LinkedHashMap<String, Object>();
		ret.put("STATUS", String.valueOf(success.value()));//
		ret.put("MSG", code.desc().toString() + (parameter != null && code == code.PARAMETER_ERROR ? " parameter:" + parameter : ""));
		ret.put("DATA", result == null ? new HashMap<Object, Object>() : result);
		ret.put("CODE", String.valueOf(code.value()));
		return DesEncryptUtil.encrypt(JSONObject.toJSONString(ret));
	}
	
	/**
	 * 返回正确JSON串
	 * @param code
	 * @param errMsg
	 * @return
	 */
	public static String toSuccessResult(Object result){
		LinkedHashMap<String, Object> ret = new LinkedHashMap<String, Object>();
		ret.put("STATUS", String.valueOf(SUCCESS.OK.value()));//
		ret.put("MSG", "");
		ret.put("DATA", result == null ? new HashMap<Object, Object>() : result);
		ret.put("CODE", "");
		return DesEncryptUtil.encrypt(JSONObject.toJSONString(ret));
	}
	
	/**
	 * 返回错误JSON串
	 * @param code
	 * @param errMsg
	 * @return
	 */
	public static String toErrorResult(CODE code, String errMsg){
		LinkedHashMap<String, Object> ret = new LinkedHashMap<String, Object>();
		ret.put("STATUS", String.valueOf(SUCCESS.FAILURE.value()));//
		ret.put("MSG", StringUtil.isNullOrBlank(errMsg) ? code.getDisplayMsg() : errMsg);
		ret.put("DATA", new HashMap<Object, Object>(1));
		ret.put("CODE", String.valueOf(code.value()));
		return DesEncryptUtil.encrypt(JSONObject.toJSONString(ret));
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void parseResult() {

		// 判断是否为空
		if (StringUtil.isNullOrBlank(json)) {
			return;
		}

		try {
			// 将Json字符串转成Map
			Map responseMap = JSONObject.parseObject(json,HashMap.class);//.toBean(json,HashMap.class);

			// 获取success
			if (responseMap.containsKey("STATUS")) {
				STATUS = Integer.parseInt(String.valueOf(responseMap.get("STATUS")));
			}

			// 获取code
			if (responseMap.containsKey("CODE")) {
				code = Integer.parseInt(String.valueOf(responseMap.get("CODE")));
			}

			// 获取message
			if (responseMap.containsKey("MSG")) {
				MSG = String.valueOf(responseMap.get("MSG"));
			}

			// 获取data
			if (responseMap.containsKey("DATA")) {
				dataMap = (Map) responseMap.get("DATA");
			}
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		/*System.out.println(toResult(SUCCESS.OK, CODE.OK, null, null));
		System.out.println(toResult(SUCCESS.OK, CODE.PARAMETER_ERROR, null, new ArrayList<String>()));
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("aa", "aa");
		map.put("bb", "11");
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("aa", "aa");
		map1.put("bb", "11");
		map1.put("dd", new ArrayList());
		map.put("cc", map1);
		
		list.add(map);
		System.out.println(toResult(SUCCESS.OK, CODE.OK, null, map1));*/
		// 错误列表  ==> 形成文档
//		for (CODE err : CODE.values()) {
//			System.out.println(err.value() + "\t" + err.getMessage());
//		}
		
//		String ret = toErrorResult(CODE.NOT_LOGGED, null);
//		System.out.println("ret: " + ret);
//		ret = DesEncryptUtil.decrypt(ret);
//		System.out.println("ret: " + ret);
		
		Map<String,Object> a = new HashMap<String,Object>();
		a.put("systemuserid", "2");
		a.put("org_id", "1.0.1");
		System.out.println(DesEncryptUtil.encrypt(a));
		System.out.println(DesEncryptUtil.decrypt("sg3GcK5rAajwnSw+KRlhQw+5T5PjewUqjcgfyGfOnXf/PC8fmbFqLoXyghnf1FOxHTTqqsdLyqVp5NVoE1KCDFvDwIsUXc4r4VWvvwDrBYzftFTzz2ySpR7l1su4ym38DGb3iF4LV0cC9dw2y3H8RCx2cRNIZb7lulVxUpdm6+IkMi3j7BmF5XFISX432X4VCZOYcK+OBDuP6zudcLXeVIfBiUkZws7CyB6N97qU1ltuAQ9JTVOMDf7NlTyNlAHwGd6HfQWdpUxZ9f8LYMKI6xgBK+zA+TnWK4NHRz68AbW8TLyJL1baOakbzcdx0LfGMgkmN+S/l+z6DjukDIc1EPVhUGS1rTWlw43bTYvvtQ9PSh1x9g5z6A=="));
	}
	
}

