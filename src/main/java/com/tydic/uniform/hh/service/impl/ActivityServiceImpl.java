package com.tydic.uniform.hh.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;





import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.ActivityServiceServ;
import com.tydic.uniform.hh.util.HFBMD5;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.util.StringUtil;
import com.tydic.uniform.hh.vo.rep.ActivityVo;
import com.tydic.uniform.hh.vo.resp.ActivityResp;
import com.tydic.uniform.hh.vo.resp.ResourceConversionResp;

/**
 * <p>
 * </p>
 * 
 * @author ghp 2015年9月29日 下午6:16:12
 * @ClassName ActivityServiceImpl 首页活动列表查询接口实体
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
@Service("ActivityServiceServ")
public class ActivityServiceImpl extends AbstractService implements
		ActivityServiceServ {
	private static Logger log = Logger.getLogger(ActivityServiceImpl.class);

	/**
	 * 查询首页活动列表
	 */
	@Override
	public Map<String, Object> qryactivity(ActivityVo activityVo) {
		log.info("*************************资源转换接口入参*************************");
		log.info("APP request str:"
				+ JSONObject.fromObject(activityVo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("actType", activityVo.getActtype());
		req.put("actChannel", activityVo.getActchannel());

		Map<String, Object> req_s2 = new HashMap<String, Object>();
		req_s2.put("index", activityVo.getIndex());
		req_s2.put("pageSize", activityVo.getPagesize());

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("param", req);
		body.put("pagination", req_s2);

		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1002002",
				ResponseBean.class);
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> resultResultMap = (Map<String, Object>) bean
				.getBody().get(0);
		String status = (String) resultResultMap.get(HhConstants.ESHOPRESULT);
		String result = (String) resultResultMap.get(HhConstants.BILLMSG);
		Map<String, Object> mapInfo = new HashMap<>();
		ActivityResp activityResp = new ActivityResp();
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功

			List<Map<String, Object>> repdata = (List<Map<String, Object>>) ((Map<String, Object>) resultResultMap
					.get(HhConstants.ESHOPREPDATA)).get("list");
			ArrayList<Map<String, Object>> resplist = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < repdata.size(); i++) {
				Map<String, Object> resTransTypemap = (Map<String, Object>) repdata
						.get(i);
				Map<String, Object> respmap = new HashMap<String, Object>();
				respmap.put("actid", resTransTypemap.get("actId"));
				respmap.put("actintroduction",
						resTransTypemap.get("actIntroduction"));
				respmap.put("actpurl", resTransTypemap.get("actPurl"));
				respmap.put("actstatus", resTransTypemap.get("actStatus"));
				respmap.put("acttitle", resTransTypemap.get("actTitle"));
				respmap.put("acttype", resTransTypemap.get("actType"));
				respmap.put("acturl", resTransTypemap.get("actUrl"));
				respmap.put("isurl", resTransTypemap.get("isurl"));
				resplist.add(respmap);
			}
			mapInfo.put("actlist", resplist);
			activityResp.setMapInfo(mapInfo);
			resultMap.put("activityResp", activityResp);
			resultMap.put(HhConstants.MESSAGE, result);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
			if (result != null)
				resultMap.put(HhConstants.MESSAGE, result);
		}
		return resultMap;
	}

	/**
	 * 查询首页活动列表
	 */
	@Override
	public Map<String, Object> qryiosactivity() {
		log.info("*************************资源转换接口入参*************************");
		/*
		 * 按照BOSS文档设置接口入参
		 */

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("actType", "6");
		req.put("actChannel", "4");

		Map<String, Object> req_s2 = new HashMap<String, Object>();
		req_s2.put("index", "0");
		req_s2.put("pageSize", "10");

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("param", req);
		body.put("pagination", req_s2);

		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1002002",
				ResponseBean.class);
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> resultResultMap = (Map<String, Object>) bean
				.getBody().get(0);
		String status = (String) resultResultMap.get(HhConstants.ESHOPRESULT);
		String result = (String) resultResultMap.get(HhConstants.BILLMSG);
		Map<String, Object> mapInfo = new HashMap<>();
		ActivityResp activityResp = new ActivityResp();
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功

			List<Map<String, Object>> repdata = (List<Map<String, Object>>) ((Map<String, Object>) resultResultMap
					.get(HhConstants.ESHOPREPDATA)).get("list");
			ArrayList<Map<String, Object>> resplist = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < repdata.size(); i++) {
				Map<String, Object> resTransTypemap = (Map<String, Object>) repdata
						.get(i);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String endDate = (String)resTransTypemap.get("endDate");
				if(endDate!=null&&!endDate.equals("")){
					try {
						Date endate  = sdf.parse(endDate);
						Date nowd = new Date();
						String nowstr = sdf.format(nowd);
						//广告结束时间在当前时间之后   下载
						if(endate.after(nowd)||endDate.equals(nowstr)){
							Map<String, Object> respmap = new HashMap<String, Object>();
							respmap.put("actid", resTransTypemap.get("actId"));
							respmap.put("actintroduction",
									resTransTypemap.get("actIntroduction"));
							respmap.put("actpurl", resTransTypemap.get("actPurl"));
							respmap.put("actstatus", resTransTypemap.get("actStatus"));
							respmap.put("acttitle", resTransTypemap.get("actTitle"));
							respmap.put("acttype", resTransTypemap.get("actType"));
							respmap.put("acturl", resTransTypemap.get("actUrl"));
							respmap.put("isurl", resTransTypemap.get("isurl"));
							resplist.add(respmap);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			mapInfo.put("actlist", resplist);
			activityResp.setMapInfo(mapInfo);
			resultMap.put("activityResp", activityResp);
			resultMap.put(HhConstants.MESSAGE, result);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
			if (result != null)
				resultMap.put(HhConstants.MESSAGE, result);
		}
		return resultMap;
	}

	// 话费宝新春红包
	@Override
	public Map<String, Object> qryRedUrl(ActivityVo activityVo) {
		String mobile = activityVo.getOrdid();
		String huafeibao_private_key = PropertiesUtil.getPropertyValue("huafeibao_private_key");
		String huafeibao_url = PropertiesUtil.getPropertyValue("huafeibao_url");
		String sid = "170app";
		// 获取签名
		String data = mobile + sid + huafeibao_private_key;
		String signValue = HFBMD5.GetMD5Code(data);

		Map<String, String> body = new HashMap<String, String>();
		body.put("signValue", signValue);
		body.put("ordId", mobile);
		body.put("sid", sid);

		log.info("requtest str:"+JSON.toJSONString(body));
		String bean = http(huafeibao_url,body);
		log.info("response str:"+bean);
		
		
		Map<String, Object> resultMap;
		try {
			resultMap = new HashMap<String, Object>();
			JSONObject myJsonObject = JSONObject.fromObject(bean);
			String status = myJsonObject.get("status").toString();
			ActivityResp activityResp = new ActivityResp();
			Map<String, Object> mapInfo = new HashMap<>();
			
			int times=0;
			
			if (HhConstants.SUCCESS.equals(status)) {
				resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
//				Map<String, Object> respmap = new HashMap<String, Object>();
				mapInfo.put("status", myJsonObject.get("status"));
				mapInfo.put("accesstoken", myJsonObject.get("accessToken"));
				mapInfo.put("url",  myJsonObject.get("url"));
//				mapInfo.put("respmap", respmap);
				activityResp.setMapInfo(mapInfo);
				resultMap.put("activityResp", activityResp);
				return resultMap;
			} else{
				++times;
				//失败一次 再次请求
				if(times<1){
					log.info("requtest2 str:"+JSON.toJSONString(body));
					bean = http(huafeibao_url,body);
					log.info("response2 str:"+bean);
					myJsonObject = JSONObject.fromObject(bean);
					status = myJsonObject.get("status").toString();
					
					if (HhConstants.SUCCESS.equals(status)) {
						resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
//						Map<String, Object> respmap = new HashMap<String, Object>();
						mapInfo.put("status", myJsonObject.get("status"));
						mapInfo.put("accesstoken", myJsonObject.get("accessToken"));
						mapInfo.put("url",  myJsonObject.get("url"));
//						mapInfo.put("respmap", respmap);
						activityResp.setMapInfo(mapInfo);
						resultMap.put("activityResp", activityResp);
						return resultMap;
					} else{
						resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
						String result =  myJsonObject.get("msg").toString();
						resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
						if (result != null)
							resultMap.put(HhConstants.MESSAGE, result);
						return resultMap;
					}
				}else{
					resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
					String result =  myJsonObject.get("msg").toString();
					resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
					if (result != null)
						resultMap.put(HhConstants.MESSAGE, result);
					return resultMap;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String http(String url, Map<String, String> params) {

		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:" + url);
		System.out.println("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con
			.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
