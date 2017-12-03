package com.tcl;

import com.tcl.BafVariants;

public class SimpleBafVariants implements BafVariants {
    String actionDetail;

    public SimpleBafVariants(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public String toTclString() {
        if (actionDetail == null || actionDetail.equals("")) {
            return "{}";
        }
        return actionDetail;
    }

}

