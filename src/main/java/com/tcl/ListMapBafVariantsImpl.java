package com.tcl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-15 下午9:29:02 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class ListMapBafVariantsImpl {
	//private List<Map<String, String>> parameter = new ArrayList<Map<String, String>>();
	
    public static List<Map<String, String>> initParameterByTclStr(String tclString) {
        TclMsg result = new TclMsg(tclString);
        System.out.println("tclString: " + tclString);
        System.out.println("initParameterByTclStr.getLength : " + result.getLength());
        List<Map<String, String>> parameter = new ArrayList<Map<String, String>>();
        
        for (int i = 0; i < result.getLength(); i++) {
        	System.out.println("result.getTclMsg(i).getLength(): " + result.getTclMsg(i).getLength());
        	for(int j = 0; j < result.getTclMsg(i).getLength(); ++j){
	        	System.out.println(i + "," + j + " : " + result.getTclMsg(i).getString(j));
	        	String tmp = result.getTclMsg(i).getString(j);
	        	System.out.println("tmp: " + tmp);
	        	//System.out.println("tmp1: " + result.getTclMsg(i).getTclMsg(index));
	        	TclMsg rs = result.getTclMsg(i).getTclMsg(j);//new TclMsg(tmp);
	        	System.out.println("rs.getLength(): "+ rs.getLength());
	        	System.out.println("key: " + rs.getTclMsg(0).getString(0));
	        	System.out.println("value: " + rs.getTclMsg(1).getString(0));
	        	Map<String, String> map = new HashMap<String, String>();
	        	for(int x = 0; x < rs.getLength(); ++x){
	        		
		        	String key = rs.getTclMsg(x).getString(0);
		        	System.out.println("key: " + key);
		        	String val = rs.getTclMsg(x).getString(1);;
		        	System.out.println("val: " + val);
		        	map.put(key, val);
		        	//addParameter(key, val);
	        	}
	        	parameter.add(map);

        	}
        }
        
        return parameter;
    }

	public String toTclString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addParameter(String key, String value) {
		// TODO Auto-generated method stub
		
	}

	public void addNecessaryParam(String key, String value) {
		// TODO Auto-generated method stub
		
	}

	public String getParameter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAllParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getMapValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
