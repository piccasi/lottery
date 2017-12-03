package com.tcl;

import com.tcl.ProcessException;


public class ServiceLocatorException extends ProcessException {
	public ServiceLocatorException(String errcode, String errmsg) {
		super(errcode, errmsg);
	}

	public ServiceLocatorException(String errcode, String errmsg, Throwable cause) {
		super(errcode, errmsg, cause);
	}		
}
