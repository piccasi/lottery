<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>测试</title>
  </head>
  
  <body>
    <table border='1'>
        <tr>  
            <th><c:out value="药品名称" /></th>   
            <th><c:out value="药品描述" /></th>   
            <th><c:out value="药品照片" /></th>   
        </tr> 
     <c:forEach items="${medicines}" var="t">   
        <tr>  
            <td><c:out value="${t.name}" /></td>   
            <td><c:out value="${t.desc}" /></td>   
            <td><img src=<c:out value="${t.img}" /> /></td>   
            <!-- <th><a href="/list.jsp">尚未评估</th> -->   
        </tr>   
     </c:forEach> 
     </table>
  </body>
</html>
