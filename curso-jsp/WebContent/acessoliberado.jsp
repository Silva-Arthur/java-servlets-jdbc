<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:setProperty property="*" name="calcula" />
	<h3>Seja bem vindo ao sistema em jsp</h3>

	<!-- Chamo a servlet e la na servlet no requestdispach eu indico
	para qual jsp quero direcionar -->
	<a href="salvarUsuario?acao=listartodos"><img
		alt="Cadastro de usuários" title="Cadastro de usuários"
		src="resources/img/usuario.png" height="50" width="50"></a>
		
	<a href="salvarProduto?acao=listartodos">
		<img alt="Cadastro de produtos" title="Cadastro de produtos" src="resources/img/produto.png" height="50" width="50">
	</a>
</body>
</html>