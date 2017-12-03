package com.tcl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.tcl.Loader;
import com.tcl.StringList;
import com.tcl.MockDataException;

/**
 * ģ�����Provider
 * @author plh
 */
public class MockDataProvider {   
    private static final Logger logger = Logger.getLogger(MockDataProvider.class);
	private static final String MOCK_DATA_FILE = "mockdata.xml";	

    private StringList dataFiles;
	private MockDataProvider(){}
	public static MockDataProvider createMockDataProvider(){
		MockDataProvider mdp = new MockDataProvider();
		mdp.init();
		return mdp;
	}
	
	private void init(){ 
        try {
            Digester d = new Digester();
            d.addObjectCreate("mock-data/include-files", StringList.class);
            d.addCallMethod("mock-data/include-files/include", "add", 1);
            d.addCallParam("mock-data/include-files/include", 0, "fileName"); 
            URL url = Loader.getResource(MOCK_DATA_FILE);
			d.parse(url.openStream());
			this.dataFiles = (StringList)d.getRoot();  	
		} catch (Exception e) {
			dataFiles = new StringList();
			logger.error(e);
		}		
	}
	
    /**
     * ���TUXEDO����ģ�ⷵ�ؽ��
     * @param tclString  tcl String
     * @return ��ò������
     */
    public HashMap getTuxResult(String actionName) throws MockDataException{ 
    	logger.info("actionName=" + actionName);
		
    	HashMap hm = getTuxResult(actionName, MOCK_DATA_FILE);
    	if(hm != null) return hm;
		for(int i=0; i<dataFiles.size(); i++){
			hm = getTuxResult(actionName, dataFiles.get(i));
			if(hm != null) return hm;
		}
    	
    	throw new MockDataException("SYS-90999", "û��������ȷ��ģ�����");
    }
    
    private HashMap getTuxResult(String actionName, String fileName) throws MockDataException{    
        try {
            Digester d = new Digester();
            addTuxRltRules(d);
            URL url = Loader.getResource(fileName);
			d.parse(url.openStream());
			ArrayList blst = (ArrayList)d.getRoot();
						
			HashMap hm = null;
			for(int i=0; i<blst.size(); i++){
				hm = (HashMap)blst.get(i);
				if(actionName.equals(hm.get("actionName"))){
					return hm;
				}
			}
			return null;
		} catch (Exception e) {
			logger.error("����ļ���" + fileName, e);
			throw new MockDataException("SYS-90998", "����mockdata.xml�ļ�ʧ��", e);
		}	
    }
    
    private void addTuxRltRules(Digester d) {
        d.addObjectCreate("mock-data/tuxedo/tcl-result", ArrayList.class);
        
        d.addObjectCreate("mock-data/tuxedo/tcl-result/busi", HashMap.class); 
        d.addSetNext("mock-data/tuxedo/tcl-result/busi", "add");  
        
        d.addCallMethod("mock-data/tuxedo/tcl-result/busi/actionName", "put", 2);
        d.addObjectParam("mock-data/tuxedo/tcl-result/busi/actionName", 0, "actionName"); 
        d.addCallParam("mock-data/tuxedo/tcl-result/busi/actionName", 1); 
        
        d.addCallMethod("mock-data/tuxedo/tcl-result/busi/decription", "put", 2);
        d.addObjectParam("mock-data/tuxedo/tcl-result/busi/decription", 0, "decription"); 
        d.addCallParam("mock-data/tuxedo/tcl-result/busi/decription", 1); 

        d.addCallMethod("mock-data/tuxedo/tcl-result/busi/request", "put", 2);
        d.addObjectParam("mock-data/tuxedo/tcl-result/busi/request", 0, "request"); 
        d.addCallParam("mock-data/tuxedo/tcl-result/busi/request", 1); 

        d.addCallMethod("mock-data/tuxedo/tcl-result/busi/code", "put", 2);
        d.addObjectParam("mock-data/tuxedo/tcl-result/busi/code", 0, "code"); 
        d.addCallParam("mock-data/tuxedo/tcl-result/busi/code", 1); 

        d.addCallMethod("mock-data/tuxedo/tcl-result/busi/result", "put", 2);
        d.addObjectParam("mock-data/tuxedo/tcl-result/busi/result", 0, "result"); 
        d.addCallParam("mock-data/tuxedo/tcl-result/busi/result", 1); 
    }
}