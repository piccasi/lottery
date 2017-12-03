package cn.online.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.online.pojo.BaseSearch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.ServiceUtil;
import com.tcl.MapBafVariantsImpl;
import com.tcl.SelfTest;
import com.tcl.StandBafVariants;
import com.tcl.TclMsg;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-6 上午10:50:24 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class BaseSearchController {
	
	public static String baseSearch() throws UnsupportedEncodingException{
		BaseSearch actionModel = new BaseSearch();
		//String cust_id = getCustomerIdByUid(uid);
		actionModel.setKeyWords("蒲地蓝");
		actionModel.setType("2");
		SelfTest manager = ServiceUtil.getSelfTest();
		manager.removeAllAction();
		manager.addAction(actionModel);
		try {
			manager.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*String tclStr = "Pis_Get_Medical_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE ROLLBACK}} { {KEY_WORDS {蒲地蓝} } {TYPE 2} }";
		SocketTradeAction.setBAF_IP("120.77.44.145");
		SocketTradeAction.setBAF_PORT(7979);
		String ret = SocketTradeAction.executeBaf(tclStr);*/
		
		String ret = manager.commonResult.getMessage();
		System.out.println("ret: " + ret);
		//ret = "{{ name :'蒲地蓝消炎口服液',desc :'清热解毒，抗炎消肿。用于疖肿、腮腺炎、咽炎、扁桃体炎等。   ',img :'http://img.39.net/yp/360/s/622325.jpg' }{ name :'蒲地蓝消炎片',desc :'清热解毒，消炎止痛，舒筋活络。用于流行性感冒，咽喉炎，肺炎，菌痢，急性胃肠炎，阑尾炎，烧伤，疮疡脓肿，蜂窝织炎。   ',img :'http://img.39.net/yp/360/2nd/S/5/509700.jpg' }{ name :'蒲地蓝消炎胶囊',desc :'用于治疗病毒性感染和自身免疫性疾病：1.皮肤病；扁平疣，带状疱疹，单纯疱疹，两周为一个疗程；银痟病，尖锐湿疣等，三个月为一个疗程；2.呼吸道疾病；反复感冒，支气管炎，哮喘，慢性支气管炎，过敏性鼻...  ',img :'http://img.39.net/ypk/images/nopic.gif' }}";
		//System.out.println("ret: " + ret);
		StandBafVariants result = MapBafVariantsImpl.getInstance(ret);
		
		ret = result.getParameter("RESULT");
		System.out.println("ret1: " + ret);
		System.out.println("ret2: " + ret.substring(1, ret.length() - 1));
		
		//JSONObject jsonObject = JSONObject.parseObject("[{ name :'蒲地蓝消炎口服液',desc :'清热解毒，抗炎消肿。用于疖肿、腮腺炎、咽炎、扁桃体炎等。   '},{ name :'蒲地蓝消炎片',desc :'清热解毒，消炎止痛，舒筋活络。用于流行性感冒，咽喉炎，肺炎，菌痢，急性胃肠炎，阑尾炎，烧伤，疮疡脓肿，蜂窝织炎。   '}]");
		//JSONArray jsonArray = JSONArray.parseArray("[{ name :'蒲地蓝消炎口服液',desc :'清热解毒，抗炎消肿。用于疖肿、腮腺炎、咽炎、扁桃体炎等。   '},{ name :'蒲地蓝消炎片',desc :'清热解毒，消炎止痛，舒筋活络。用于流行性感冒，咽喉炎，肺炎，菌痢，急性胃肠炎，阑尾炎，烧伤，疮疡脓肿，蜂窝织炎。   '}]");
		JSONArray jsonArray = JSONArray.parseArray(ret);
		Iterator<Object> iterator = jsonArray.iterator();
		while(iterator.hasNext()){
			JSONObject jsonObject = (JSONObject) iterator.next();
			System.out.println("name: " + jsonObject.get("name"));
			System.out.println("desc: " + jsonObject.get("desc"));
			System.out.println("img: " + jsonObject.get("img"));
		}
		
		
		
		/*List<Map> retList = getSearchResult(ret);
		for(Map map : retList){
			System.out.println("name: " + map.get("name"));
			System.out.println("desc: " + map.get("desc"));
			System.out.println("img: " + map.get("img"));
		}*/
		
		return result.getParameter("RESULT");
		//return new String(ret.getBytes("GBK"), "UTF-8");
	}
	
	public static List<Map> getSearchResult(String str){
		List<Map> list = new ArrayList<Map>();
		if (str != null) {
			TclMsg tclmsg = new TclMsg(str);
			int msgLength = tclmsg.getLength();
			for (int i = 0; i < msgLength; i++) {
				Map map = new HashMap();
				
				TclMsg tmp = tclmsg.getTclMsg(i);
				String ttString = tmp.toTclStr();
				System.out.println("ttString: " + ttString);
				map.put("name", tclmsg.getTclMsg(i).getString(0));
				map.put("desc", tclmsg.getTclMsg(i).getString(1));
				map.put("img", tclmsg.getTclMsg(i).getString(2));
				list.add(map);
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		baseSearch();
	}

}
