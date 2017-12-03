package com.tcl;

import com.tcl.BafVariants;

public class BafActionInfo {
    private String actionName;

    private BafVariants parameter;

    public BafActionInfo() {
    }

    public BafActionInfo(String action, BafVariants parameter) {
        super();
        this.actionName = action;
        this.parameter = parameter;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String action) {
        this.actionName = action;
    }

    public BafVariants getParameter() {
        return parameter;
    }

    public void setParameter(BafVariants parameter) {
        this.parameter = parameter;
    }

    public String getParamTclString() {
        return parameter.toTclString();
    }

}