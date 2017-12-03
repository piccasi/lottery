package com.tcl;

import java.util.HashMap;

import com.tcl.CommonResult;
import com.tcl.MockDataProvider;


public class StubTradeAction {

    public static int execute(String serviceName, String mockDataKey, String tclString, CommonResult crt, boolean flg) {

        HashMap hm = MockDataProvider.createMockDataProvider().getTuxResult(mockDataKey);
        String scode = (String) hm.get("code");
        String result = (String) hm.get("result");

        if ("0".equals(scode)) {
            crt.modiInfo(0, scode, result);
            return 0;
        }
        else {
            crt.modiInfo(-1, scode, result);
            return 1;
        }
    }
}
