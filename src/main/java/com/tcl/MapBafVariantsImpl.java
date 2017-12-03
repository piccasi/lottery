package com.tcl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class MapBafVariantsImpl implements StandBafVariants {
    private static final Log logger = LogFactory
            .getLog(MapBafVariantsImpl.class);

    private Map parameter;

    public MapBafVariantsImpl() {
        parameter = new HashMap();
    }

    public MapBafVariantsImpl(String tclString) {
        parameter = new HashMap();
        initParameterByTclStr(tclString);
    }

    public static StandBafVariants getInstance(String tclString) {
        MapBafVariantsImpl bafVariants = new MapBafVariantsImpl();
        bafVariants.initParameterByTclStr(tclString);
        return bafVariants;
    }

    private void initParameterByTclStr(String tclString) {
        TclMsg result = new TclMsg(tclString);
        
        //System.out.println("initParameterByTclStr.getLength : " + result.getLength());
        
        for (int i = 0; i < result.getLength(); i++) {
        	//System.out.println(i + ": " + result.getTclMsg(i).getString(1));
            addParameter(result.getTclMsg(i).getString(0), result.getTclMsg(i)
                    .getString(1));
        	}
    }

    public void addParameter(String key, String value) {

        // �����Ĳ����ǿգ�����Ϊnull�������κδ��?ֱ�ӷ��ء�
        if (key == null || key.equals("")) {
            //logger.info("addParameter(String, String) - key=null");
            return;
        }
        if (value == null || value.equals("")) {
            //logger.info("addParameter(String, String) - value=null");
            return;
        }

        parameter.put(key, value);
    }

    public String getParameter(String key) {

        String result = (String) parameter.get(key);

        if (result == null) {
            result = "";
        }

        return result;
    }

    public TclMsg toTclMsg() {
        TclMsg tclMsg = new TclMsg();

        Set mappings = parameter.entrySet();
        for (Iterator i = mappings.iterator(); i.hasNext();) {
            Map.Entry me = (Map.Entry) i.next();
            tclMsg.append((String) me.getKey(), (String) me.getValue());
        }

        return tclMsg;
    }

    public String toTclString() {
        StringBuffer result = new StringBuffer();

        Set mappings = parameter.entrySet();
        for (Iterator i = mappings.iterator(); i.hasNext();) {
            Map.Entry me = (Map.Entry) i.next();
            result.append(" {" + me.getKey() + " {" + me.getValue() + "}}");
        }

        return result.toString();
    }

    public Object getAllParameter() {
        TclMsg tclMsg = new TclMsg();

        Set mappings = parameter.entrySet();
        for (Iterator i = mappings.iterator(); i.hasNext();) {
            Map.Entry me = (Map.Entry) i.next();
            tclMsg.append((String) me.getKey(), (String) me.getValue());
        }

        return tclMsg;
    }
    
    public Map getMapValue() {
        return parameter;
    }

    public int size() {
        return parameter.size();
    }

    public void addNecessaryParam(String key, String value) {

        // ������key�ǿգ�����Ϊnull�������κδ��?ֱ�ӷ��ء�
        if (key == null || key.equals("")) {
            logger.warn("addParameter(String, String) - key=null");
            return;
        }
        if (value == null || value.equals("")) {
            value = "";
        }

        parameter.put(key, value);

    }

    public static void main(String[] args) {
        StandBafVariants temp = new MapBafVariantsImpl();
        temp.addNecessaryParam("para1", "value1");
        temp.addNecessaryParam("para2", "");
        temp.addParameter("para3", "");
        System.out.println("tcl =" + temp.toTclString());
        //Console: tcl ={para1 value1} {para2 {}}
    }
    
    
}
