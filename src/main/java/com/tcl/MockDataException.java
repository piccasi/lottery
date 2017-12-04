package com.tcl;
/**
 * 模拟数据异常
 * @author plh
 */
public class MockDataException extends SystemException {
	public MockDataException(Throwable cause) {
		super("SYS-000-9999", "模拟数据异常", cause);
	}
	public MockDataException(String errcode, String errmsg) {
		super(errcode, errmsg);
	}
	public MockDataException(String errcode, String errmsg, Throwable cause) {
		super(errcode, errmsg, cause);
	}
}
