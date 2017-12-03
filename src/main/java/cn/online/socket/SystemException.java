package cn.online.socket;
/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-1 下午5:17:41 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */


public class SystemException extends RuntimeException {   
    /** 错误码 */
    protected String errcode;
    
    public SystemException(String errcode, String errmsg){ 
    	super(errmsg);
        this.errcode = errcode;
    }
    
    public SystemException(String errmsg){ 
    	super(errmsg);
        this.errcode = "999999";        
    }
    
    public SystemException(String errcode, String errmsg, Throwable cause){ 
    	super(errmsg, cause);
        this.errcode = errcode;
    }

	public String getErrcode() {
		return errcode;
	}
    
}
