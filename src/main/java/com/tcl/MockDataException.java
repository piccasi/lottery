package com.tcl;

import com.tcl.SystemException;

/**
 * ģ�������쳣
 * @author plh
 */
public class MockDataException extends SystemException {
	public MockDataException(Throwable cause) {
		super("SYS-000-9999", "ģ�������쳣", cause);
	}
	public MockDataException(String errcode, String errmsg) {
		super(errcode, errmsg);
	}
	public MockDataException(String errcode, String errmsg, Throwable cause) {
		super(errcode, errmsg, cause);
	}
}
