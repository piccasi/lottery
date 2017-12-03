package com.tcl;


public class OpMode {
    public static final OpMode PRE_SUBMIT = new OpMode("PRE_SUBMIT");
    public static final OpMode SUBMIT = new OpMode("SUBMIT");
    public static final OpMode SUBMIT_MOD = new OpMode("SUBMIT_MOD");
    public static final OpMode CANCEL = new OpMode("CANCEL");
    public static final OpMode ROLLBACK = new OpMode("ROLLBACK");
    public static final OpMode FINISH = new OpMode("FINISH");
    public static final OpMode APPRYES = new OpMode("APPRYES");
    public static final OpMode APPRNO = new OpMode("APPRNO");
    public static final OpMode APPR = new OpMode("APPR");
    public static final OpMode PRE_DISCARD = new OpMode("PRE_DISCARD");
    public static final OpMode DISCARD = new OpMode("DISCARD");

    /** ��ǰ����--�ύ�����ӿ� */
    public static final OpMode PRE_CHECK_FOR = new OpMode("PRE_CHECK_FOR");
    /** ��ǰ����--������ͨ���ӿ� */
    public static final OpMode PRE_CHECK_FAIL = new OpMode("PRE_CHECK_FAIL");
    /** ��ǰ����--������ͨ���˻��շѵĽӿ� */
    public static final OpMode PRE_CHECK_BACKFEE = new OpMode("PRE_CHECK_BACKFEE");
    /** ��ǰ����--����ͨ���ӿ� */
    public static final OpMode PRE_CHECK_COMMIT = new OpMode("PRE_CHECK_COMMIT");

    private final String code;

    private OpMode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

