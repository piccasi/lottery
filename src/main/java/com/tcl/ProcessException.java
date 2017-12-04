package com.tcl;

public class ProcessException extends Exception {  
    /** 错误码 */
    protected String errcode;
    
    public ProcessException(String errcode, String errmsg){ 
    	super(errmsg);
        this.errcode = errcode;
    }
    
    public ProcessException(String errcode, String errmsg, Throwable cause){ 
    	super(errmsg, cause);
        this.errcode = errcode;
    }

	public String getErrcode() {
		return errcode;
	}
}
