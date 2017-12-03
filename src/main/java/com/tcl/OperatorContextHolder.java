package com.tcl;

import com.tcl.OperatorContext;

public class OperatorContextHolder {

    private static final ThreadLocal threadLocal = new ThreadLocal() {
        protected Object initialValue() {
            return new OperatorContext();
        }
    };

    public static OperatorContext getOperatorContext() {
        if (threadLocal.get() == null) {
            threadLocal.set(new OperatorContext());
        }

        return (OperatorContext) threadLocal.get();
    }

    public static void setOperatorContext(final OperatorContext operatorContext) {
        threadLocal.set(operatorContext);
    }

    public static void clear() {
        threadLocal.set(null);
    }
}
