package com.tcl;

import com.tcl.StringUtils;

/**
 * ��b���� : Sep 25, 2006 10:15:48 AM <br>
 * ���� : plh <br>
 * ģ�� : CommonResult��ϵͳ��ͨ�÷��ؽӿ�<br>
 * ���� : isSuccess()�жϷ����Ƿ�ɹ������سɹ������������һ�����?<br>
 * ʧ����Ӧ��ͨ��errtp��������ԭ�� (����0��Ӧ���߼���?������ϢΪRESULT С��0, ϵͳ��?������Ϣ��RESULT)<br>
 * �޸���ʷ <br>
 * ��� ���� �޸��� �޸�ԭ�� <br>
 * 1 061024 yaoq �����һ��isError���Ǻǡ�<br>
 * 2 061024 yaoq �޸���pushErrorInfo����Ĵ���<br>
 */

public class CommonResult {
    /**
     * ���ر��� ��0��ʾ�ɹ����ɹ�������Ϣ��RESULT ����0��Ӧ���߼���?������ϢΪRESULT С��0, ϵͳ��?������Ϣ��RESULT
     */
    private int errtp;

    /**
     * ������� �����ʽ��XX-XSSS-NNNNN
     */
    private String errcode;

    /**
     * ������Ϣ ��ʾ�����Ա
     */
    private String message;

    /**
     * �����ջ��Ϣ Debug��Ϣ��ֻ��ʾ�����Ա<br/> detail������Exception����String
     */
    private Object detail;

    /**
     * ҵ�����
     */
    private String businame;

    /**
     * ����
     */
    private String solution;

    /**
     * ������� ��ǹ��������Ϊ�ա�
     */
    private String wfid;

    /**
     * ��ҳhistory���˵�����
     */
    private String backPageNum = "-1";

    private String hrefUrl = "";// �������ֵ������ת��ʱ����ת����ҳ��

    /** ��tcl�������4�Ķ��������VO, MODEL, ����map */
    private Object parseResult;

    /**
     * ������� ��ǹ��������Ϊ�ա����������¼����Ϊ�ա� <br>
     * ���ȽϹؼ��̨���������¼������Ҫ���������������¼��ǰ̨ҳ����ת��Ʊ�ݴ�ӡ��
     */
    private String chgid;

    private String closeJS;

    public String getCloseJS() {
    	return StringUtils.trim(closeJS);
    	//return com.asiainfo.core.util.StringUtils.trim(closeJS);
    }

    public void setCloseJS(String closeJS) {
        this.closeJS = closeJS;
    }

    public CommonResult() {
        message = "";
    }

    public CommonResult(int errtp, String errcode, String message) {
        this.errtp = errtp;
        this.errcode = errcode;
        this.message = message;
    }

    public CommonResult(int errtp, int errcode, String message) {
        this.errtp = errtp;
        this.errcode = String.valueOf(errcode);
        this.message = message;
    }

    public CommonResult(int errtp, String errcode, String message, Object detail) {
        this.errtp = errtp;
        this.errcode = errcode;
        this.message = message;
        this.detail = detail;
    }

    /**
     * ��Ϣ�޸�
     * 
     * @param errtp ��������
     * @param errcode �������
     * @param message ������Ϣ
     */
    public void modiInfo(int errtp, String errcode, String message) {
        this.errtp = errtp;
        this.errcode = errcode;
        this.message = message;
    }

    /**
     * ��Ϣ�޸�
     * 
     * @param errtp ��������
     * @param errcode �������
     * @param message ������Ϣ
     */
    public void modiInfo(int errtp, int errcode, String message) {
        this.errtp = errtp;
        this.errcode = String.valueOf(errcode);
        this.message = message;
    }

    /**
     * ��Ϣ�޸�
     * 
     * @param errtp ��������
     * @param errcode �������
     * @param message ������Ϣ
     * @param detail ��ϸ��Ϣ
     */
    public void modiInfo(int errtp, String errcode, String message, Object detail) {
        this.errtp = errtp;
        this.errcode = errcode;
        this.message = message;
        this.detail = detail;
    }

    /**
     * ��Ϣ�޸�
     * 
     * @param errtp ��������
     * @param errcode �������
     * @param message ������Ϣ
     * @param detail ��ϸ��Ϣ
     */
    public void modiInfo(int errtp, int errcode, String message, Object detail) {
        this.errtp = errtp;
        this.errcode = String.valueOf(errcode);
        this.message = message;
        this.detail = detail;
    }

    /**
     * ׷�Ӵ�����Ϣ
     * <p>
     * ��modiInfo������ͬ��modiInfo�����Ὣerrcode��message�滻��<br>
     * ��pushErrorInfo�����ǽ�errcode�滻,message=aErrormsg��message
     * </p>
     * 
     * @param aErrorcode
     * @param aErrormsg
     */
    public void pushErrorInfo(String aErrorcode, String aErrormsg) {
        this.errcode = aErrorcode;
        this.message += aErrormsg;
    }

    public void pushErrorInfo(String aErrormsg) {
        this.errtp = 1;
        this.errcode = "999999";
        this.message += aErrormsg;
    }

    /**
     * @return �ɹ�true/ʧ��false
     */
    public boolean isSuccess() {
        return errtp == 0;
    }

    public boolean isError() {
        return errtp != 0;
    }

    public String getBusiname() {
        return businame;
    }

    public void setBusiname(String businame) {
        this.businame = businame;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public String getSimpleMessage() {
        if (StringUtils.hasText(message)) {
            String simpleMessage = message.replaceAll("^.*-.*�쳣[\\s]*", "");
            simpleMessage = simpleMessage.replaceAll("File.*$", "");
            simpleMessage = simpleMessage.replaceAll("\\[.*\\][\\s]*", "");
            return simpleMessage;
        }
        else {
            return "";
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrtp() {
        return errtp;
    }

    public void setErrtp(int errtp) {
        this.errtp = errtp;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getBackPageNum() {
        return backPageNum;
    }

    public void setBackPageNum(String backPageNum) {
        this.backPageNum = backPageNum;
    }

    public String getChgid() {
        return chgid;
    }

    public void setChgid(String chgid) {
        this.chgid = chgid;
    }

    public String getWfid() {
        return wfid;
    }

    public void setWfid(String wfid) {
        this.wfid = wfid;
    }

    public Object getParseResult() {
        return parseResult;
    }

    public void setParseResult(Object parseResult) {
        this.parseResult = parseResult;
    }

    public void clean() {
        errcode = "";
        message = "";
        detail = null;
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public void pushSuccessInfo(String aErrormsg) {
        this.errcode = "0";
        this.message += aErrormsg;
        this.errtp = 0;
    }

}
