package api.gateway.common;

import java.io.Serializable;

public class CommonResponse <T> implements Serializable  {
	private static final String ZERO = "0";

    /**
     *
     */
    private static final long serialVersionUID = 3278611319011091487L;

    /**
     * 默认成功
     */
    private boolean success = true;
    private String responseCode = ZERO;
    /**
     * 错误信息(自己查看)
     */
    private String responseMsg;
   
   
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    
    @Override
    public String toString() {
        return "CommonResponseData [success=" + success + ", responseCode=" + responseCode + ", responseMsg="
                + responseMsg + ", data=" + data + "]";
    }

}

