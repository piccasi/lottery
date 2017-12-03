<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>疾病搜索</title>
  </head>
  
  <body>
    <table border='1'>
        <tr>  
            <th><c:out value="JDK_NAME" /></th>   
            <th><c:out value="JDK_METHOD" /></th>   
            <th><c:out value="JDK_PERIOD" /></th>
            <th><c:out value="JDK_BODY" /></th>
            <th><c:out value="JDK_HUMAN" /></th>
            <th><c:out value="JDK_YIBAO" /></th>
            <th><c:out value="JDK_CHECK" /></th>
            <th><c:out value="JDK_FEE" /></th>
            <th><c:out value="JDK_JS" /></th>
            <th><c:out value="JDK_KESHI" /></th>
            <th><c:out value="JDK_INFECT" /></th>
            <th><c:out value="JDK_DRUG" /></th>
            <th><c:out value="JDK_CURE" /></th>   
        </tr> 
     <c:forEach items="${diseases}" var="disease">   
        <tr>  
            <td><c:out value="${disease['JDK_NAME']}" /></td>   
            <td><c:out value="${disease['JDK_METHOD']}" /></td>
            <td><c:out value="${disease['JDK_PERIOD']}" /></td>
            <td><c:out value="${disease['JDK_BODY']}" /></td>
            <td><c:out value="${disease['JDK_HUMAN']}" /></td>
            <td><c:out value="${disease['JDK_YIBAO']}" /></td>
            <td><c:out value="${disease['JDK_CHECK']}" /></td>
            <td><c:out value="${disease['JDK_FEE']}" /></td>
            <td><c:out value="${disease['JDK_JS']}" /></td>
            <td><c:out value="${disease['JDK_KESHI']}" /></td>
            <td><c:out value="${disease['JDK_INFECT']}" /></td>
            <td><c:out value="${disease['JDK_DRUG']}" /></td>
            <td><c:out value="${disease['JDK_CURE']}" /></td>
        </tr>   
     </c:forEach> 
     </table>
  </body>
</html>
