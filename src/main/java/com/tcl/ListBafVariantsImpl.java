package com.tcl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.StringUtils;

/**
 * ��b���� : 2006-8-28 17:35<br>
 * ���� : yaoq<br>
 * ģ�� : ����Ա��½��Ȩ<br>
 * ���� : BAF��action�ľ����������ֵ��Ӧ�Ĺ�ϵ��<br>
 * �޸���ʷ<br>
 * ��� ���� �޸��� �޸�ԭ��<br>
 * 1 060829 yaoq �����һ���ڲ���<br>
 * 2 <br>
 */

public class ListBafVariantsImpl implements StandBafVariants {
    private static final Log logger = LogFactory.getLog(ListBafVariantsImpl.class);

    private List varList = new ArrayList();

    public ListBafVariantsImpl() {
    }

    public List getVarList() {
        return varList;
    }

    /*
     * clean contents of variants.
     */
    public void clear() {
        varList.clear();
    }

    /**
     * ֻ��������������ӣ����valueΪ""�������Ҳû�����壬ֱ�ӷ���
     */
    public void addParameter(final String key, final String value) {
        if (!StringUtils.hasLength(key)) {
            //logger.warn("addParameter(String, String) - key=null");
            return;
        }
        if (!StringUtils.hasLength(value)) {
            //logger.warn("addParameter(String, String) - value=null");
            return;
        }

        varList.add(new BafParameter(key, value));
    }

    public TclMsg toTclMsg() {
        TclMsg tclMsg = new TclMsg();

        for (Iterator iter = varList.iterator(); iter.hasNext();) {
            BafParameter element = (BafParameter) iter.next();
            tclMsg.append(element.getParaName(), element.getParaValue());
        }

        return tclMsg;
    }

    protected class BafParameter {

        private String paraName;

        private String paraValue;

        public BafParameter(String key, String value) {
            this.paraName = key;
            this.paraValue = value;
        }

        public String getParaName() {
            return paraName;
        }

        public void setParaName(String paraName) {
            this.paraName = paraName;
        }

        public String getParaValue() {
            return paraValue;
        }

        public void setParaValue(String paraValue) {
            this.paraValue = paraValue;
        }

    }

    public Object getAllParameter() {
        // TODO����ʵ��,Ҫ���ӿ������� ȥ���
        return null;
    }

    public String getParameter(String key) {
        for (Iterator iter = varList.iterator(); iter.hasNext();) {
            BafParameter element = (BafParameter) iter.next();
            if (element.getParaName().equals(key)) {
                return element.getParaValue()==null?"":element.getParaValue();
            }
        }
        return "";
    }

    public String toTclString() {
        return toTclMsg().toTclStr();
    }

    public int size() {
        return varList.size();
    }

    /*
     * (non-Javadoc) ���ܿյ�ֵ
     * 
     * @see com.asiainfo.frame.aiipclient.StandBafVariants#addNecessaryParam(java.lang.String,
     * java.lang.String)
     */
    public void addNecessaryParam(String key, String value) {
        // TODO: ��ʵ��
        logger.error("δ��ɸýӿڵĲ��ԣ��벻Ҫ���ã����Ҫʹ��addNecessaryParam����MapBafVariantsImpl");
        if (!StringUtils.hasLength(key)) {
            logger.warn("addParameter(String, String) - key=null");
            return;
        }
        if (!StringUtils.hasLength(value)) {
            value = "";
        }

        varList.add(new BafParameter(key, value));
    }

    public static void main(String[] args) {
        StandBafVariants temp = new ListBafVariantsImpl();
        temp.addNecessaryParam("para1", "value1");
        temp.addNecessaryParam("para2", "");
        temp.addParameter("para3", "");
        System.out.println("tcl =" + temp.toTclString());
        // Console: tcl ={para1 value1} {para2 {}}
    }

	public Map getMapValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
