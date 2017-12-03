package com.tcl;

import com.tcl.BafVariants;
import com.tcl.SimpleBafVariants;

public class SimpleBaModel implements BaModel {

	private String actionName;

	private String tclStr;

	public SimpleBaModel(String actionName, String tclStr) {
		super();
		this.actionName = actionName;
		this.tclStr = tclStr;
	}

	public String getActionName() {
		return actionName;
	}

	public BafVariants getBafVariants() {
		return new SimpleBafVariants(tclStr);
	}
}
