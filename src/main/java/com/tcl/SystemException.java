package com.tcl;

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

