package com.tcl;

import com.tcl.Action;
import com.tcl.BafAuthInfo;
import com.tcl.OpMode;
import com.tcl.OperatorContextHolder;
import com.tcl.TclMsg;



public class BafCommInfo {

    public static final String OPMODE_SUBMIT = "SUBMIT";
    /** Ҫִ�е�busi code */
    protected String busiId;
    protected String oldBmsAcceptId;
    /** ����Ա��Ϣ */
    protected BafAuthInfo authInfo;
    /** Ҫִ�е�action���ڵ�channel */
    protected String channel;
    /** �����ţ�����������ʽ�ύ, ��ѡ���� */
    protected String bmsAcceptId;
    /** ������ע�� ��ѡ���� */
    protected String remark;
    /** PRE_SUBMIT=Ԥ�ύ,SUBMIT=��ʽ�ύ(Ĭ��)�� CANCEL */
    protected String opMode;
    /** �����ؼ��� */
    protected String busiKey;
    protected String userRegionId;
    /** ������ */
    private String transactor;
    /** ��һ��չ�� */
    private String firstDevOper;
    /** �ڶ���չ�� */
    private String secondDevOper;
    /** ��չ���� */
    private String devLevel;

    public BafCommInfo(String busiId, OpMode opMode) {
        this(busiId, opMode, null, "", "");
        this.authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();
    }

    /**
     * Ĭ��Ҫִ�е�action���ڵ�channel=Action.CHANNEL_BMS<br>
     * ������ʹ���ˣ���ʹ�����¹��캯����<br>
     * BafCommInfo(String busiId, OpMode opMode, BafAuthInfo authInfo)
     * 
     * @deprecated
     */
    public BafCommInfo(String busiId, String opMode, BafAuthInfo authInfo) {
        this.channel = Action.CHANNEL_BMS;
        this.opMode = opMode;
        this.busiId = busiId;
        this.authInfo = authInfo;
    }

    /**
     * ǿ��opMode�����ͣ���ֹ�Ƿ����ݴ���
     * 
     */
    public BafCommInfo(String busiId, OpMode opMode, BafAuthInfo authInfo) {
        this(busiId, opMode, authInfo, "", "");
    }

    public BafCommInfo(String busiId, OpMode opMode, BafAuthInfo authInfo, String busiKey, String remark) {
        this.channel = Action.CHANNEL_BMS;
        this.opMode = opMode.getCode();
        this.busiId = busiId;
        this.authInfo = authInfo;
        this.busiKey = busiKey;
        this.remark = remark;
    }

    public BafAuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(BafAuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public String getBmsAcceptId() {
        return bmsAcceptId;
    }

    public void setBmsAcceptId(String bmsAcceptId) {
        this.bmsAcceptId = bmsAcceptId;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getBusiKey() {
        return busiKey;
    }

    public void setBusiKey(String busiKey) {
        this.busiKey = busiKey;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TclMsg toTclMsg() {

        TclMsg tclMsg = new TclMsg();

        // �ܲ���
        tclMsg.append("COMM_INFO");
        // ��ѡ����
        tclMsg.append("BUSI_CODE", busiId);
        tclMsg.append(authInfo.toTclMsg());
        tclMsg.append("CHANNEL", channel);
        tclMsg.append("OP_MODE", opMode);

        // ��ѡ����
        tclMsg.append("BMS_ACCEPT_ID", bmsAcceptId);
        tclMsg.append("BUSI_KEY", busiKey);
        tclMsg.append("REMARK", remark);
        tclMsg.append("BUSI_REGION_ID", userRegionId);
        tclMsg.append("TRANSACTOR", transactor);
        tclMsg.append("FIRST_DEV_OPER", firstDevOper);
        tclMsg.append("SECOND_DEV_OPER", secondDevOper);
        tclMsg.append("DEV_LEVEL", devLevel);

        return tclMsg;

    }

    public String toTclString() {
        return toTclMsg().toTclStr();
    }

    public String getUserRegionId() {
        return userRegionId;
    }

    public void setUserRegionId(String userRegionId) {
        this.userRegionId = userRegionId;
    }

    public String getOldBmsAcceptId() {
        return oldBmsAcceptId;
    }

    public void setOldBmsAcceptId(String oldBmsAcceptId) {
        this.oldBmsAcceptId = oldBmsAcceptId;
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }

    public String getFirstDevOper() {
        return firstDevOper;
    }

    public void setFirstDevOper(String firstDevOper) {
        this.firstDevOper = firstDevOper;
    }

    public String getSecondDevOper() {
        return secondDevOper;
    }

    public void setSecondDevOper(String secondDevOper) {
        this.secondDevOper = secondDevOper;
    }

    public String getDevLevel() {
        return devLevel;
    }

    public void setDevLevel(String devLevel) {
        this.devLevel = devLevel;
    }

}
