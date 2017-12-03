package com.tcl;


public class BafSystemException extends SystemException {
    public BafSystemException(String errmsg) {
        super(errmsg);
    }

    public BafSystemException(String errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public BafSystemException(String errcode, String errmsg, Throwable cause) {
        super(errcode, errmsg, cause);
    }

}