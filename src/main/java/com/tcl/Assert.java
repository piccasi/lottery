package com.tcl;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.SystemException;


public abstract class Assert {

    private static final Log logger = LogFactory.getLog(Assert.class);

    /**
     * �жϱ��ʽ�Ƿ���ȷ
     * 
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            logger.error("boolean�жϴ���" + message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * �ж�text�Ƿ��Ƿǿա�
     * 
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        if (text == null || text.length() == 0) {
            logger.error("��������Ϊ�ǿգ�" + message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * object �����ǿյ�Ŷ
     * 
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            logger.error("����������null��" + message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * �ж��Ƿ�����ȷ����������
     * 
     * @param value
     * @param format
     * @param message
     */
    public static void isDate(String value, String format, String message) {
        DateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        ParsePosition pos = new ParsePosition(0);

        Date date = sdf.parse(value, pos);

        if ((date == null) || (pos.getErrorIndex() != -1)) {
            logger.error("���ڽ�������" + message);
            throw new SystemException("9343", message);
        }
    }

}
