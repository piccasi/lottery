package com.tcl;

import com.tcl.Action;
import com.tcl.BafAuthInfo;
import com.tcl.OpMode;
import com.tcl.OperatorContextHolder;
import com.tcl.TclMsg;



public class BafCommInfo {

    public static final String OPMODE_SUBMIT = "SUBMIT";
    /** 要执行的busi code */
    protected String busiId;
    protected String oldBmsAcceptId;
    /** 操作员信息 */
    protected BafAuthInfo authInfo;
    /** 要执行的action所在的channel */
    protected String channel;
    /** 工单号，仅仅用于正式提交, 可选参数 */
    protected String bmsAcceptId;
    /** 操作备注， 可选参数 */
    protected String remark;
    /** PRE_SUBMIT=预提交,SUBMIT=正式提交(默认)， CANCEL */
    protected String opMode;
    /** 索引关键字 */
    protected String busiKey;
    protected String userRegionId;
    /** 代办人 */
    private String transactor;
    /** 第一发展人 */
    private String firstDevOper;
    /** 第二发展人 */
    private String secondDevOper;
    /** 发展级别 */
    private String devLevel;

    public BafCommInfo(String busiId, OpMode opMode) {
        this(busiId, opMode, null, "", "");
        this.authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();
    }

    /**
     * 默认要执行的action所在的channel=Action.CHANNEL_BMS<br>
     * 不建议使用了，请使用如下构造函数：<br>
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
     * 强制opMode的类型，防止非法数据传入
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

        // 总参数
        tclMsg.append("COMM_INFO");
        // 必选参数
        tclMsg.append("BUSI_CODE", busiId);
        tclMsg.append(authInfo.toTclMsg());
        tclMsg.append("CHANNEL", channel);
        tclMsg.append("OP_MODE", opMode);

        // 可选参数
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
