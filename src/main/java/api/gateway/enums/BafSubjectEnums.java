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

 
    
 
    Pis_Get_Medical_Info("Pis_Get_Medical_Info","查询药品接口",new String[]{"KEY_WORDS","TYPE"},new String[]{"KEY_WORDS","TYPE"},new String[]{"DRUG_NAME","DRUG_DESC","DRUG_IMG"},CommonHandler.class)
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
