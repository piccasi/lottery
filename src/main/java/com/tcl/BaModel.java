package com.tcl;

import com.tcl.BafVariants;

public interface BaModel {

    /**
     * tcl串，格式如下{DATETIME 20041101131948}
     * 
     * @return
     */
    public String getActionName();

    public BafVariants getBafVariants();

}
