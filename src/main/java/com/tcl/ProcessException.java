package com.tcl;

public class ProcessException extends Exception {  
    /** ´íÎóÂë */
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
