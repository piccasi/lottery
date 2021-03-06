package api.gateway.enums;

import api.gateway.common.CommonHandler;
import api.gateway.handler.*;

/**
 * 主题枚举
 *
 * @author zhaozm
 * @datetime 2017年12月05日 下午6:46:12
 * @since 1.0.0
 */
public enum BafSubjectEnums {
   /*
    * 只要新增枚举就可以实现新增接口
    * 参照TEST_EXAMPLE TEST_EXAMPLE为BAF调用英文名字 BAF调用英文名字(BAF调用英文名字,BAF中文名字,必须参数名,全部入参包含必须非必须,返回参数名,调用类不需要改)
    * 需要在末尾加个,
    * 
    * 
    * 
    */
 
    
 
    Pis_Get_Medical_Info("Pis_Get_Medical_Info",
    		"查询药品接口",
    		new String[]{"KEY_WORDS","TYPE"},
    		new String[]{"KEY_WORDS","TYPE"},
    		new String[]{"DRUG_NAME","DRUG_DESC","DRUG_IMG"},
    		CommonHandler.class),
    Ssbon_Location_WX("Ssbon_Location_WX",
    	    		"地理位置同步",
    	    		new String[]{"LOC_X","LOC_Y"},
    	    		new String[]{"XW_OPENID","LOC_X","LOC_Y","USER_ADDR","CITY"},
    	    		new String[]{"ROBOT_OUTPUT"},
    	    		CommonHandler.class),
    Pis_User_New("Pis_User_New",
    		"用户注册",
    		new String[]{"USER_NAME","PASSWD","REG_TYPE"},
    		new String[]{"XW_OPENID","USER_NAME","PASSWD","REG_TYPE"},
    		new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    		CommonHandler.class),
    Pis_User_Logon("Pis_User_Logon",
    		"登陆接口",
    		new String[]{"USER_NAME","PASSWD","REG_TYPE"},
    		new String[]{"XW_OPENID","USER_NAME","PASSWD","REG_TYPE"},
    		new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    		CommonHandler.class),
    Pis_First_Info("Pis_First_Info",
    	    "首页资讯接口",
    	    new String[]{"XW_OPENID"},
    	    new String[]{"XW_OPENID"},
    	    new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    	    CommonHandler.class),
    Pis_Health_Test("Pis_Health_Test",
    	    	    "健康评估接口",
    	    	    new String[]{"XW_OPENID"},
    	    	    new String[]{"XW_OPENID"},
    	    	    new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    	    	    CommonHandler.class),
    Pis_Product_Info("Pis_Product_Info",
    	    "商品展示接口",
    	     new String[]{"XW_OPENID"},
    	     new String[]{"XW_OPENID"},
    	     new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    	     CommonHandler.class),
    Pis_OCR("Pis_OCR",
    	     "图像识别接口",
    	      new String[]{"XW_OPENID","PIC_URL"},
    	      new String[]{"XW_OPENID","PIC_URL"},
    	      new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    	      CommonHandler.class),
    TEST_EXAMPLE("TEST_EXAMPLE",
    		"这个是中文名字",
    		new String[]{"我是必须传","的参数"},
    		new String[]{"我是请求的","参数"},
    		new String[]{"我是返回的","参数"},
    		CommonHandler.class),
	TEST_GATEWAY("TEST_GATEWAY",
    		"测试",
    		new String[]{"name"},
    		new String[]{"name","age"},
   	        new String[]{"RET_CODE","RET_MSG","RESULT_INFO","CUSTOMER_ID"},
    		CommonHandler.class)
    ;
    
    /**
     * 主题值
     */
    private String type;

    /**
     * 主题中文名称
     */
    private String name;

    /**
     * 必须参数列表
     */
    private String[] requireds;

    /**
     * 必须参数列表
     */
    private Class<?> requestClass;

    /**
     * 参与签名参数列表
     */
    private String[] signs;

    /**
     * 业务处理器
     */
    private Class<?> classz;
    
    
    /**
     * 入参数列表
     */
    private String[] resStrs;
    
    public String[] getResStrs() {
		return resStrs;
	}

	public void setResStrs(String[] resStrs) {
		this.resStrs = resStrs;
	}

	public void setRequireds(String[] requireds) {
		this.requireds = requireds;
	}

	/**
     * 出参数列表
     */
    private String[] reqStrs;
    
    
    public String[] getReqStrs() {
		return reqStrs;
	}

	public void setReqStrs(String[] reqStrs) {
		this.reqStrs = reqStrs;
	}

	private BafSubjectEnums(String type, String name, String[] requireds, String[] resStrs, String[] reqStrs, Class<?> classz) {
        this.type = type;
        this.name = name;
        this.requireds = requireds;
        this.resStrs = resStrs;
        this.reqStrs = reqStrs;
        this.classz = classz;
    }
    
    private BafSubjectEnums(String type, String name, String[] requireds, String[] signs, Class<?> classz) {
        this.type = type;
        this.name = name;
        this.requireds = requireds;
        this.signs = signs;
        this.classz = classz;
    }

    private BafSubjectEnums(String type, String name, Class<?> requestClass, String[] signs, Class<?> classz) {
        this.type = type;
        this.name = name;
        this.requestClass = requestClass;
        this.signs = signs;
        this.classz = classz;
    }
    

    public static BafSubjectEnums typeOf(String type) {
        for (final BafSubjectEnums option : BafSubjectEnums.values()) {
            if (option.getType().equals(type)) {
                return option;
            }
        }
        return null;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String[] getRequireds() {
        return this.requireds;
    }

    public Class<?> getRequestClass() {
        return this.requestClass;
    }

    public String[] getSigns() {
        return this.signs;
    }

    public Class<?> getClassz() {
        return this.classz;
    }

}
