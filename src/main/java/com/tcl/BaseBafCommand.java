package com.tcl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.BafCommInfo;
import com.tcl.ListBafVariantsImpl;
import com.tcl.BafActionInfo;



public class BaseBafCommand {
    private static final Log logger = LogFactory.getLog(BaseBafCommand.class);

    private static String BMS_ACTION_BATCH = "Bms_Action_Batch";

    private static String ACTION_LIST = "ACTION_LIST";

    private List actionList = new ArrayList();

    private BafCommInfo commInfo;

    public BaseBafCommand() {
    }

    public BaseBafCommand(BafCommInfo commInfo) {
        this.commInfo = commInfo;
    }

    public BafCommInfo getCommInfo() {
        return this.commInfo;
    }

    public void setCommInfo(BafCommInfo commInfo) {
        this.commInfo = commInfo;
    }

    public void addAction(BafActionInfo bafActionInfo) {
        actionList.add(bafActionInfo);
    }

    public void addAction(String actionName, ListBafVariantsImpl parameter) {
        actionList.add(new BafActionInfo(actionName, parameter));
    }

    /**
     * clean last message for reuse
     */
    public void removeAllActions() {
        actionList.clear();
    }

    /**
     * 仅仅用于模拟数据时候判断该取哪条数据
     */
    public String getMockDataKey() {
        String keyName = null;
        if (actionList.size() == 1) {
            BafActionInfo bafActionInfo = (BafActionInfo) actionList.get(0);
            keyName = bafActionInfo.getActionName();
        } else {
            keyName = commInfo.getBusiId();
        }
        return keyName;
    }

    public String toString() {
        return toTclMsg().toTclStr();
    }

    private TclMsg singleBafCommand() {

        BafActionInfo bafActionInfo = (BafActionInfo) actionList.get(0);

        TclMsg tclMsg = new TclMsg();

        tclMsg.append(bafActionInfo.getActionName());
        tclMsg.append(commInfo.toTclString());
        tclMsg.append(bafActionInfo.getParamTclString());

        return tclMsg;

    }

    private TclMsg batchBafCommand() {

        TclMsg tclMsg = new TclMsg();

        tclMsg.append(BMS_ACTION_BATCH);
        tclMsg.append(commInfo.toTclString());
        tclMsg.append(getActionsTclStr());

        return tclMsg;
    }

    public String getTclStr() {
        return toTclMsg().toTclStr();
    }

    public String getActionsTclStr() {
        TclMsg actionListTclMsg = new TclMsg(ACTION_LIST);
        for (Iterator iter = actionList.iterator(); iter.hasNext();) {
            BafActionInfo element = (BafActionInfo) iter.next();
            StringBuffer tmpTclMsg = new StringBuffer();
            tmpTclMsg.append(element.getActionName());
            tmpTclMsg.append(" ");
            tmpTclMsg.append(element.getParamTclString());
            actionListTclMsg.append(tmpTclMsg.toString());
        }
        return actionListTclMsg.toTclStr();
    }

    public TclMsg toTclMsg() {

        if (commInfo == null) {
            logger.error("封装baf消息时出错：commInfo shouldn't is null!");
            return null;
        }

        if (actionList.size() <= 0) {
            logger.error("封装baf消息时出错：action list should large 0!");
        } else if (actionList.size() == 1) {
            return singleBafCommand();
        } else {
            return batchBafCommand();
        }
        return null;

    }

    public int getActionSize() {
        return actionList.size();
    }

}
