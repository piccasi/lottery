package cn.hna.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-7-20 下午4:16:29 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class BaiduOcrClient {
	//设置APPID/AK/SK
    public static final String APP_ID = "9913145";
    public static final String API_KEY = "2OrMqeEjNwf0GYvI7tjEGBYC";
    public static final String SECRET_KEY = "wTlDZEN8j7VToPbX41lSD96hrCIyNUeq";

    public static void main(String[] args) { 
        // 初始化一个OcrClient
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用身份证识别接口
        // 设置识别身份证正面参数
        boolean isFront = true;

        // 自定义参数定义
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "false");

        // 参数为本地图片路径
        //String imagePath = "C:\\Users\\fusionZ\\Documents\\idcard1.jpg";
        //JSONObject response = client.idcard(imagePath, isFront, options);
        //System.out.println(response.toString());
        
        // 参数为本地图片路径
        /*isFront = false;
        String imagePath = "C:\\Users\\fusionZ\\Documents\\idcard2.jpg";
        JSONObject response = client.idcard(imagePath, isFront, options);
        System.out.println(response.toString());*/

        // 参数为本地图片文件二进制数组
        //byte[] file = readImageFile(imagePath);
        //JSONObject response = client.idcard(file, side, options);
        //System.out.println(response.toString());

        // 调用银行卡识别接口
        //String bankFilePath = "test_bank.jpg";
        //JSONObject bankRes = client.bankcard(bankFilePath);
        //System.out.println(bankRes.toString(2));

        // 调用通用识别接口
        //String genFilePath = "test_basic_general.jpg";
        //JSONObject genRes = client.basicGeneral(genFilePath, new HashMap<String, String>());
        //System.out.println(genRes.toString(2));

        // 调用通用识别（含位置信息）接口
        //String genFilePath = "test_general.jpg";
        //JSONObject genRes = client.general(genFilePath, new HashMap<String, String>());
        //System.out.println(genRes.toString(2));
    }
}
