package com.tydic.uniform.hh.baseInvoke;


/**
 * 系统异常
 * @ClassName: NoPermissionException
 * @author yzb yangzb@tydic.com,yzhengbin@gmail.com
 * @date 2011-11-23
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 343241598018535L;
	
	private String errorCd;
	public ServiceException(String message,String errorCd) {
		super(message);
		this.errorCd=errorCd;
	}
	
	public ServiceException(String message) {
		super(message);
		this.errorCd="-1";
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public String getErrorCd()
	{
		return errorCd;
	}


	
	
	
}
