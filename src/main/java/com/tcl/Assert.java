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
     * 判断表达式是否正确
     * 
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            logger.error("boolean判断错误！" + message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断text是否是非空。
     * 
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        if (text == null || text.length() == 0) {
            logger.error("参数不能为非空！" + message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * object 不能是空的哦
     * 
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            logger.error("参数不能是null！" + message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否是正确的日期类型
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
            logger.error("日期解析出错：" + message);
            throw new SystemException("9343", message);
        }
    }

}
