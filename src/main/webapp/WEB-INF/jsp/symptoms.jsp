<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>病症搜索</title>
  </head>
  
  <body>
    <table border='1'>
        <tr>  
            <th><c:out value="可能是以下几种病" /></th>    
        </tr> 
     <c:forEach items="${symptoms}" var="symptom">   
        <tr>  
            <td><a href="/health/search?type=1&keywords=${symptom}"><c:out value="${symptom}" /></a></td>   
        </tr>   
     </c:forEach> 
     </table>
  </body>
</html>
