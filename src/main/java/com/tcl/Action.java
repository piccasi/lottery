package com.tcl;

public interface Action {
    public static String CHANNEL_PMS = "A1";
    public static String CHANNEL_BMS = "A2"; 
    public static String CHANNEL_REPORT = "A3";
    public String execute(String serviceName, String inMessage);
}