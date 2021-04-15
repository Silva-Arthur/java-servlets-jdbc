<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page"></jsp:useBean>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:set var="data" scope="page" value="${500*6}"/>
	
	<c:remove var="data"/>
	
	<c:out value="${data}"></c:out>
	
	<c:catch var="erro">
		<%=  100/0%>
	</c:catch>
	
	<c:if test="${erro != null}">
		${erro.message}
	</c:if>	
	
	<c:set var="numero" value="${100/2}"></c:set>
	
	<!-- If usado jstl -->
	<c:choose>
		<c:when test="${numero >= 50}">
			<c:out value="${'Maior ou igual que 50'}"/>
		</c:when>	
		
		<c:when test="${numero < 50}">
			<c:out value="${'Menor que 50'}"/>
		</c:when>
		
		<c:otherwise>
			<c:out value="${'Não encontrou valor correto'}"/>
		</c:otherwise>
	</c:choose>
	
	<!-- For usando jstl -->
	<c:forEach var="n" begin="1" end="${numero}">
		Item: ${n}
	</c:forEach>
	
	<c:forTokens items="Arthur-Pereira-Silva" delims="-" var="nome">
		<br>
		Nome: <c:out value="${nome}"></c:out>
	</c:forTokens>
	
	<c:url value="/acessoliberado.jsp" var="acesso">
		<c:param name="param1" value="123"></c:param>
		<c:param name="param2" value="456"></c:param>
	</c:url>
	
	${acesso}
	
	<c:if test="${numero >= 50}">
		<c:redirect url="https://www.google.com.br"></c:redirect>
	</c:if>
	<c:if test="${numero < 50}">
		<c:redirect url="http://www.javaavancado.com"></c:redirect>
	</c:if>
	<p/>
	<p/>
	<p/>
	<p/>
	
	<form action="LoginServlet" method="post">
		Login:
		<input type="text" id="login" name="login">
		<br>
		Senha:
		<input type="text" id="senha" name="senha">
		<br>
		<input type="submit" value="Logar">
	</form>
	
</body>
</html>