<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<!-- 
	<h1>Bem vindo ao curso de JSP</h1>
	<%="seu sucesso garantido: " %>
	
	<form action="receber-nome2.jsp">
		<input type="text" id="nome" name="nome">
		<input type="submit" value="Enviar">
	</form>
	
	
<%! int cont = 2; 

 	public int retorna(int numero){
 		return numero * 3;
 	}
%>
<%=cont %>

<br>

<%= retorna(8)%>

<br>

<%= application.getInitParameter("estado") %>

<br>

<%session.setAttribute("curso", "curso de jsp"); %>	 

<%@ page import="java.util.Date" %>

 "data de hoje: " + new Date() 

<%@ page errorPage="receber-nome.jsp"%>

<%= 100/2 %>

<%@ include file="pagina-include.jsp" %>

	<myprefix:minhatag/>
	
	<jsp:forward page="receber-nome.jsp">
		<jsp:param value="curso de jsp site java avancado.com" name="paramforward"/>
	</jsp:forward>-->
	
</body>
</html>