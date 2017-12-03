package com.tcl;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tcl.lang.TclDouble;
import tcl.lang.TclException;
import tcl.lang.TclInteger;
import tcl.lang.TclList;
import tcl.lang.TclObject;
import tcl.lang.TclString;


public class TclMsg {

    private static final Log logger = LogFactory.getLog(TclMsg.class);

    Vector tclObjectList;

    public TclMsg() {
        tclObjectList = new Vector();
    }

    public TclMsg(String tclString) {
        initTclObjectList(TclString.newInstance(tclString));
    }

    public TclMsg(TclObject tclObject) {
        initTclObjectList(tclObject);
    }

    public TclMsg getIndex(int index) {
        return getTclMsg(index);
    }

    /*
     * 
     */
    public int getInt(int index) {
        checkIndexBound(index);

        int returnInt = 0;
        try {
            returnInt = TclInteger.get(null, (TclObject) tclObjectList
                    .get(index));
        } catch (TclException e) {
            logger.error("getInt(int),can't convert to int!", e);
            throw new IllegalArgumentException("can't convert to int!");
        }

        return returnInt;
    }

    public double getDouble(int index) {

        checkIndexBound(index);
        double returnDouble;
        try {
            returnDouble = TclDouble.get(null, (TclObject) tclObjectList
                    .get(index));
        } catch (TclException e) {
            logger.error("getDouble(int),can't convert to int!", e); //$NON-NLS-1$
            throw new IllegalArgumentException("can't convert to int!");
        }

        return returnDouble;
    }

    /*
     * 
     */
    public String getString(int index) {
        checkIndexBound(index);
        return tclObjectList.get(index).toString();
    }

    public long getLong(int index) {
        checkIndexBound(index);
        return Long.parseLong(tclObjectList.get(index).toString());
    }

    /*
     * 
     */
    public TclMsg getTclMsg(int index) {
        checkIndexBound(index);
        return new TclMsg(getTclObject(index));
    }

    /**
     * ��ҵ��Ե�ʱ��ʹ�ÿ���ʹ�ô˺���˺����tcl����ֵ��<br>
     * �Ƿ�Ч�ʲ��ߣ���<br>
     * 
     * @return
     */
    public String toTclStr() {

        TclObject tclObject = TclList.newInstance();

        try {
            for (Iterator iter = tclObjectList.iterator(); iter.hasNext();) {
                TclObject element = (TclObject) iter.next();
                TclList.append(null, tclObject, element);
            }
        } catch (TclException e) {
            logger.error("toTclStr() can't get the TclStr!", e);
            throw new IllegalArgumentException("can't get the TclStr!");
        }

        return tclObject.toString();
    }

    public TclMsg append(String keyName) {
        if (keyName == null)
            return this;

        tclObjectList.add(TclString.newInstance(keyName));
        return this;
    }

    public TclMsg append(int keyValue) {
        tclObjectList.add(TclInteger.newInstance(keyValue));
        return this;
    }

    public TclMsg append(double keyValue) {
        tclObjectList.add(TclDouble.newInstance(keyValue));
        return this;
    }

    public TclMsg append(TclObject tclObject) {
        tclObjectList.add(tclObject);
        return this;
    }

    public TclMsg append(String tclName, String tclValue) {
        // �����Ĳ����ǿգ�����Ϊnull�������κδ��?ֱ�ӷ��ء�
        if (tclName == null || tclName.equals("")) {
            return this;
        }
        if (tclValue == null || tclValue.equals("")) {
            return this;
        }

        TclObject tmpTclObject = TclList.newInstance();
        try {
            TclList.append(null, tmpTclObject, TclString.newInstance(tclName));
            TclList.append(null, tmpTclObject, TclString.newInstance(tclValue));
        } catch (TclException e) {
            logger.error("name&value", e);
            throw new IllegalArgumentException("can't append the name&value!");
        }

        append(tmpTclObject);

        return this;
    }

    public TclMsg append(TclMsg newTclMsg) {
        tclObjectList.addAll(newTclMsg.tclObjectList);
        return this;
    }

    /*
     * 
     */
    public int getLength() {
        return tclObjectList.size();
    }

    private TclObject getTclObject(int index) {
        checkIndexBound(index);
        return (TclObject) tclObjectList.get(index);
    }

    private void checkIndexBound(int index) {
        Assert.isTrue(index < this.getLength(), "index too large!");
        Assert.isTrue(index >= 0, "index too small,should index>=0!");
    }

    private void initTclObjectList(TclObject tclObject) {
        try {
            TclObject[] tclObjectArray = TclList.getElements(null, tclObject);
            tclObjectList = new Vector(tclObjectArray.length);
            for (int i = 0; i < tclObjectArray.length; i++) {
                tclObjectList.add(tclObjectArray[i]);
            }
        } catch (TclException e) {
            logger.error("initTclObjectList(TclObject),error", e); //$NON-NLS-1$
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
