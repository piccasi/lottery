package com.tydic.uniform.hh.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tydic.uniform.hh.constant.BusinessType;

public class SmsTemplateUtil {
	private final static Logger LOGGER = Logger.getLogger(SmsTemplateUtil.class);    
	
    public static final String DEFAULT_SPLIT = "$$";
    
    
    /**
     * 使用context中对应的值替换templet中用$$包围的变量名(也是context的key)
     * @param templet 模板
     * @param context 用于替换模板中的变量
     * @return 例如  参数 : dddd$$aaa$$$$bbb$$ccc$$, $$, {<aaa, value1>, <bbb, value2>}  结果:ddddvalue1value2ccc$$
     */
    private static String render(String templet, Map<String, String> context) {
        return render(templet, DEFAULT_SPLIT, context);
    }
    
    /**
     * 使用context中对应的值替换templet中用split包围的变量名(也是context的key)
     * @param templet 模板
     * @param split 用于标识变量名的标志
     * @param context 用于替换模板中的变量
     * @return 例如  参数 : dddd$$aaa$$$$bbb$$ccc$$, $$, {<aaa, value1>, <bbb, value2>}  结果:ddddvalue1value2ccc$$
     */
    private static String render(String templet, String split, Map<String, String> context) {
        Set<String> paramNames = getParamNames(templet, split);

        for (String name : paramNames) {
            String value = context.get(name);
            value = value == null ? "" : value;
            String regex = "\\Q" + split + name + split + "\\E";//\Q　转义所有紧随的元字符直到  \E
            //String regex =  split + name + split ;
            //System.out.println("regex: " + regex);
            //System.out.println("templet: " + templet);
            //System.out.println("value: " + value);
            templet = templet.replaceAll(regex, value);
        }

        return templet;
    }
    
    /**
     * 根据分割符从模板中取得变量的名字($$变量名$$) eg:
     * $$aaa$$$$bbb$$ccc$$ 返回   aaa,bbb
     * @param templet 模板
     * @param split 包围变量名的字符串
     * @return 模板中的变量名
     */
    private static Set<String> getParamNames(String templet, String split) {
    	//System.out.println("getParamNames templet: " + templet);
        Set<String> paramNames = new HashSet<String>();
        
        int start = 0, end = 0;
        int len = templet.length();
        while (end < len) {
            start = templet.indexOf(split, end);
            if (start == -1) {
                break;
            }
            start = start + split.length();
            end = templet.indexOf(split, start);
            if (end == -1) {
                break;
            }
            
            String tmp = templet.substring(start, end);
            //System.out.println("tmp: " + tmp);
            paramNames.add(tmp);
            end = end + split.length();
        }
        return paramNames;
    }
    
	
	public static String getSmsContent(BusinessType type, Map<String, String> context){
		String template = ConfigUtil.getPropertyValue(type.name());
		//System.out.println("getSmsContent template:" + template);
		LOGGER.info("getSmsContent " + type.name() + ":" + template);
		return render(template, context);
	}

    public static void main(String[] args) {
        //Set<String> paramNames = getParamNames("dddd$$aaa$$$$bbb$$ccc$$", "$$");
    	String template = "尊敬的客户：您正在通过海航通信代理商app登录，验证码：$$code$$，时间$$date$$";
        Set<String> paramNames = getParamNames(template, "$$");
        System.out.println(paramNames);
        
        Map<String, String> context = new HashMap<String, String>();
        context.put("code", "value1");
        context.put("date", "value2");
        String render = render(template, context );
        System.out.println(render);
    }

}
