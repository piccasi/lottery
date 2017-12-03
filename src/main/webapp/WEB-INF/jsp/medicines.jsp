<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>药品搜索</title>
  </head>
  
  <body>
    <table border='1'>
        <tr>  
            <th><c:out value="药品名称" /></th>   
            <th><c:out value="药品描述" /></th>   
            <th><c:out value="药品照片" /></th>   
        </tr> 
     <c:forEach items="${medicines}" var="medicine">   
        <tr>  
            <td><c:out value="${medicine['DRUG_NAME']}" /></td>   
            <td><c:out value="${medicine['DRUG_DESC']}" /></td>   
            <td><img src=<c:out value="${medicine['DRUG_IMG']}" /> /></td>   
            <!-- <th><a href="/list.jsp">尚未评估</th> -->   
        </tr>   
     </c:forEach> 
     </table>
  </body>
</html>
