package cn.online.util;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-6-18 上午11:04:14 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class BaiduOcrClient {
    //设置APPID/AK/SK
    public static final String APP_ID = "9728579";
    public static final String API_KEY = "gb2MD40T9mw88zB5rH5bu2ne";
    public static final String SECRET_KEY = "AWmAp8S6CqLTNEOwc7rFgz4oYD1fcR8Y";
    
    private static AipOcr client;
    
    static {
    	client = new AipOcr(APP_ID, API_KEY, SECRET_KEY); 
    }
    
    private BaiduOcrClient(){
    	
    }
    
    public static AipOcr getInstance() {
		return client;
	}

    public static void main(String[] args) {
        // 初始化一个OcrClient
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        //client.setConnectionTimeoutInMillis(20000);
        //client.setSocketTimeoutInMillis(600000);

        // 调用身份证识别接口
        //String idFilePath = "test.jpg";
        //JSONObject idcardRes = client.idcard(idFilePath, true, null);
        //System.out.println(idcardRes.toString(2));

        // 调用银行卡识别接口
        //String bankFilePath = "test_bank.jpg";
        //JSONObject bankRes = client.bankcard(bankFilePath);
        //System.out.println(bankRes.toString(2));

        // 调用通用识别接口
        String genFilePath = "C:\\Users\\fusionZ\\Documents\\timg.jpg";
        JSONObject genRes = client.basicGeneral(genFilePath, new HashMap<String, String>());
        System.out.println(genRes.toString(2));
        org.json.JSONArray array = genRes.getJSONArray("words_result");
        StringBuilder report_word = new StringBuilder();
        //Iterator item = array.iterator();
        array.length();
        for(int i = 0; i < array.length(); ++i){
        	String tmp = array.getJSONObject(i).get("words").toString();
        	report_word.append(tmp);
        	if(i < array.length() - 1 ){
        		report_word.append("###");
        	}
        }
/*        while (item.hasNext()) {
			Object obj = item.next();
			JSONObject.wrap(obj);
			report_word.append(obj.toString());
			
		}*/
        System.out.println(report_word.toString());
        //client.basicGeneral(imgData, options)
        

        // 调用通用识别（含位置信息）接口
        //String genFilePath = "test_general.jpg";
        //JSONObject genRes = client.general(genFilePath, new HashMap<String, String>());
        //System.out.println(genRes.toString(2));
    }
}
