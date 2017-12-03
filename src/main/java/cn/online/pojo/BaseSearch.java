package cn.online.pojo;

import com.tcl.BaModel;
import com.tcl.BafVariants;
import com.tcl.MapBafVariantsImpl;
import com.tcl.StandBafVariants;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-6 上午10:52:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

public class BaseSearch implements BaModel{
	private String resultField;
	private String keyWords;
	private String type;
	private String actionName;
	

	public String getActionName() {
        return actionName;//"Pis_Get_Medical_Info";
    }

public BafVariants getBafVariants() {
	 StandBafVariants bafVariants = new MapBafVariantsImpl();
     bafVariants.addParameter("KEY_WORDS", this.keyWords);
     bafVariants.addParameter("TYPE", this.type);
     
     return bafVariants;
}

public String getKeyWords() {
	return keyWords;
}

public void setKeyWords(String keyWords) {
	this.keyWords = keyWords;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getResultField() {
	return resultField;
}

public void setResultField(String resultField) {
	this.resultField = resultField;
}

public void setActionName(String actionName) {
	this.actionName = actionName;
}


}
