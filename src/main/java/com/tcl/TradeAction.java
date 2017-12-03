package com.tcl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.CommonResult;
import com.tcl.SystemException;
import com.tcl.StringUtils;
import com.tcl.BaseBafCommand;
import com.tcl.TclMsg;

/**
 * ��b���� : Feb 6, 2009 10:23:53 PM<br>
 * ���� : penglh<br>
 * ģ�� : <br>
 * ���� : <br>
 * �޸���ʷ: ��� ���� �޸��� �޸�ԭ�� <br>
 * 1 yaoq 20090203 ��ʼ�ع���Ŀǰ̫��<br>
 * 2 <br>
 */
public class TradeAction {
    private static final Log logger = LogFactory.getLog(TradeAction.class);

    public static final String BAF_TYPE_SOCKET = "socket";
    public static final String BAF_TYPE_WTC = "wtc";
    public static final String BAF_TYPE_EJB = "ejb";
    public static final String BAF_TYPE_MOCK = "mock";
    public static final String DEFAULT_CONFIG = "/baf.properties";

    private static Properties config;
    private static String bafType;
    private Action bafAction;

    static {
        config = new Properties();
        try {
            Class config_class = Class.forName(EjbTradeAction.class.getName());
            //config_class.getResourceAsStream(DEFAULT_CONFIG);
            InputStream is = config_class.getResourceAsStream(DEFAULT_CONFIG);
            //is = config_class.getResourceAsStream(DEFAULT_CONFIG);
            if (is != null) {
                config.load(is);
            }
            else {
                throw new SystemException("99022", "异常" + DEFAULT_CONFIG);
            }

            bafType = getProperty("baf.objectFactory");
            if (bafType.equals(BAF_TYPE_EJB)) {
                if (logger.isInfoEnabled()) {
                    logger.info("����EJB���ú�̨EJB模式");
                }
                EjbTradeAction.setEjbUrl(getProperty("baf.objectFactory.ejb.url"));
            }
            else if (bafType.equals(BAF_TYPE_SOCKET)) {
                if (logger.isInfoEnabled()) {
                    logger.info("Socket模式");
                }
                SocketTradeAction.setBAF_IP(getProperty("baf.objectFactory.socket.url"));
                SocketTradeAction.setBAF_PORT(getIntProperty("baf.objectFactory.socket.port"));
            }
        }
        catch (Exception e) {
            throw new SystemException("99022", "��ʼ��EJB����URLʧ��!", e);
        }
    }

    public static String getProperty(String key) {
        String value = config.getProperty(key);
        return value == null ? value : value.trim();
    }

    public static int getIntProperty(String name) {
        return getIntProperty(name, 0);
    }

    public static int getIntProperty(String name, int defaultValue) {
        // get the value first, then convert
        String value = getProperty(name);

        if (value == null)
            return defaultValue;

        return (new Integer(value)).intValue();
    }

    /**
     * ҵ���׷���
     * @param serviceName �������
     * @param tclString ����
     * @param crt ͨ�÷��ؽ��
     * @return �ɹ���0/ʧ�ܣ�-1
     * @deprecated ��ʹ��execute(String serviceName, BaseBafCommand bafAction,
     * CommonResult crt).
     */
    public static int execute(String serviceName, String tclString, CommonResult crt) {
        return execute(serviceName, new TclMsg(tclString).getString(0), tclString, crt, true);
    }

    /**
     * ҵ���׷���
     * @param serviceName �������
     * @param BaseBafCommand ����
     * @param crt ͨ�÷��ؽ��
     * @return �ɹ���0/ʧ�ܣ�-1
     */
    public static int execute(String serviceName, BaseBafCommand bafAction, CommonResult crt) {
        return execute(serviceName, bafAction.getMockDataKey(), bafAction.toString(), crt, true);
    }

    /**
     * �굥��ѯ����
     * @param serviceName �������
     * @param BaseBafCommand ����
     * @param crt ͨ�÷��ؽ��
     * @return �ɹ���0/ʧ�ܣ�-1
     */
    public static int executeQuery(String serviceName, BaseBafCommand bafAction, CommonResult crt) {
        return execute(serviceName, bafAction.getMockDataKey(), bafAction.toString(), crt, false);
    }

    public String execute(String serviceName, String tclString, boolean flg) {
        if (logger.isInfoEnabled()) {
            logger.info("����:" + tclString);
        }

        String result = bafAction.execute(serviceName, tclString);

        if (logger.isInfoEnabled()) {
            logger.info("���ش�:" + StringUtils.substring(result, 0, 200));
        }

        return result;
    }

    /**
     * 
     * @param serviceName
     * @param mockDataKey
     * @param tclString
     * @param crt
     * @param flg
     * @return
     * @deprecated ��ʹ�÷���String execute(String serviceName, String
     * tclString,boolean flg)
     */
    private static int execute(String serviceName, String mockDataKey, String tclString, CommonResult crt, boolean flg) {
        // ����
        if (logger.isInfoEnabled()) {
            logger.info("TradeAction.execute:" + tclString);
        }

        try {
            if (bafType.equals("mock")) {
                return StubTradeAction.execute(serviceName, mockDataKey, tclString, crt, flg);
            }
            else if (bafType.equals(BAF_TYPE_EJB)) {
                return EjbTradeAction.executeTrade(serviceName, tclString, crt);
            }
            else if (bafType.equals(BAF_TYPE_WTC)) {
           //     return WtcTradeAction.execute(serviceName, mockDataKey, tclString, crt, flg);
            }
            else if (bafType.equals(BAF_TYPE_SOCKET)) {
                try {
                    String result = SocketTradeAction.executeBaf(tclString);
                    crt.modiInfo(0, 0, result);
                    return 0;
                }
                catch (SystemException e) {
                    crt.modiInfo(-1, e.getErrcode(), e.getMessage(), e);
                    return -1;
                }
            }
            else {
                crt.modiInfo(-1, "EJB-999999", "δ��ȷ����TUXEDO���÷�ʽ������ȷ��ֵbaf.objectFactor");
                return -1;
            }
        }
        finally {
            if (logger.isInfoEnabled()) {
                logger.info("OOXX:" + StringUtils.substring(crt.getMessage(), 0, 200));
            }
        }
		return 0;
    }
}
