<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>海豚医生</title>
</head>
<style>
s_ipt_wr {
	border: 1px solid #b6b6b6;
	border-color: #7b7b7b #b6b6b6 #b6b6b6 #7b7b7b;
	background: #fff;
	display: inline-block;
	vertical-align: top;
	width: 539px;
	margin-right: 0;
	border-right-width: 0;
	border-color: #b8b8b8 transparent #ccc #b8b8b8;
	overflow: hidden;
}
</style>
<body>


	<form id="bdfm" target="_blank" name="bdfm" method="get"
		action="search">
		<table>
			<tr>
				<td>
					<!-- <a href="http://localhost:8080">
	                 <img src="image/ht-logo.png"/>
	                 </a> --> <a href="/"
					class="ht-image -image-logo pull-left"><img
						src="http://localhost:8080/image/dolphin48x48.png" alt="logo"></a>
				</td>
				<td><input name="type" id="type" type="hidden" value="${type}" /></td>
				<td><input style="width: 539px; height: 30px; font-size: 16px"
					type="text" id="keywords" name="keywords"
					placeholder="${placeholder}" /></td>
				<td><input style="width: 60px; height: 30px; font-size: 16px"
					type="submit" value="搜索" /></td>
			</tr>
		</table>
	</form>


	<c:if test="${type == '1' && diseases!=null && fn:length(diseases) > 0}">
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
	</c:if>

	<c:if test="${type == '2' && medicines!=null && fn:length(medicines) > 0}">
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
	</c:if>

	<c:if test="${type == '3' && symptoms!=null && fn:length(symptoms) > 0}">
		<table>
<%-- 			<tr>
				<th><c:out value="可能是以下几种病" /></th>
			</tr> --%>
			<c:forEach items="${symptoms}" var="symptom">
				<tr>
					<td>
						<a href="/health/search?type=1&keywords=${symptom.key}"><c:out value="${symptom.key}" /></a>
					</td>
				</tr>
				<tr>
					<td>
						<c:out value="${symptom.value['JDK_JS']}"></c:out>
					</td>
				</tr>
				<tr>
					<td>
						所属科室：<c:out value="${symptom.value['JDK_KESHI']}"></c:out>
						&#12288;&#12288;
						常规治疗方法：<c:out value="${symptom.value['JDK_METHOD']}"></c:out>
						&#12288;&#12288;
						常用治疗药物：<c:out value="${symptom.value['JDK_DRUG']}"></c:out>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>
