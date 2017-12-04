package com.tcl;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.CommonResult;
import com.tcl.SystemException;
import com.asiainfo.crm.ejb.TradeHome;
import com.asiainfo.crm.ejb.TradeRemote;
import com.asiainfo.crm.ejb.TradeResult;


public class EjbTradeAction implements Action {
    private static final Log logger = LogFactory.getLog(EjbTradeAction.class);
    private static String ejbUrl;

    public static TradeResult trade(String serviceName, String tclString) throws Exception {
        TradeHome home = getRemoteHome("ejb/TradeHome", TradeHome.class);
        TradeRemote remote = home.create();
        TradeResult returnTradeResult = remote.trade(serviceName, tclString);
        return returnTradeResult;
    }

    public String execute(String serviceName, String inMessage) {
        try {
            TradeResult rlt = trade(serviceName, inMessage);
            if (rlt.getRtType() != 0) {
                throw new SystemException("9432", "调用BAF错误:" + rlt.getResult());
            }
            return rlt.getResult();
        }
        catch (Exception e) {
            logger.error("EJB调用错误:" + e.getMessage());
            throw new SystemException("EJB-999999", "EJB调用错误!", e);
        }
    }

    public static int executeTrade(String serviceName, String tclString, CommonResult crt) {
        try {
            TradeResult rlt = trade(serviceName, tclString);
            crt.modiInfo(rlt.getRtType(), rlt.getCode(), rlt.getResult(), rlt.getCause());
            return rlt.getRtType();
        }
        catch (Exception e) {
            logger.error("executeTrade(String, String, CommonResult)", e);
            crt.modiInfo(-1, "EJB-999999", "EJB调用错误:" + e.getMessage(), e);
            return -1;
        }
    }

    private static TradeHome getRemoteHome(String jndiHomeName, Class className) throws NamingException {

        Object objref = getInitialContext().lookup(jndiHomeName);
        Object obj = PortableRemoteObject.narrow(objref, className);
        TradeHome returnTradeHome = (TradeHome) obj;
        return returnTradeHome;
    }

    private static Context getInitialContext() throws NamingException {
        if (ejbUrl == null) {
            throw new SystemException("请设置EJB_URL, 如：t3://130.36.20.50:7001");
        }

        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        properties.put(Context.PROVIDER_URL, ejbUrl);

        Context returnContext = new InitialContext(properties);
        return returnContext;
    }

    public static String getEjbUrl() {
        return ejbUrl;
    }

    public static void setEjbUrl(String ejbUrl) {
        EjbTradeAction.ejbUrl = ejbUrl;
    }
}