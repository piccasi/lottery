package cn.online.interceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-12-6 下午1:52:45 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class DecryptInterceptor implements HandlerInterceptor {
	private static Logger logger = Logger.getLogger(DecryptInterceptor.class);

    // before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        //long startTime = System.currentTimeMillis();
        //request.setAttribute("interceprtoStartTime", startTime);
/*        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);

            sb.append("-----------------------").append(SimpleDateFormatCache.getYmdhms().format(new Date()))
                    .append("-------------------------------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
            sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
            System.out.println(sb.toString());
        }*/
        return true;
    }

    // after the handler is executed
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	logger.info("come DecryptInterceptor");
/*        long startTime = (Long) request.getAttribute("interceprtoStartTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if(handler instanceof HandlerMethod){
        	HandlerMethod h = (HandlerMethod) handler;
            StringBuilder sb = new StringBuilder(1000);
            sb.append("URI:").append(request.getRequestURI()).append(", ");
            sb.append("Controller:").append(h.getBean().getClass().getName()).append(", ");
            sb.append("Method:").append(h.getMethod().getName()).append(", ");
            //sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
            sb.append("CostTime:").append(executeTime).append("ms").append(",");
            logger.info(sb.toString());
        }*/
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for(Entry<String,String[]> e:map.entrySet()){
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if(value != null && value.length == 1){
                sb.append(value[0]).append("\t");
            }else{
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
}
