<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cadastro de Produto</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
<a href="acessoliberado.jsp">Início</a>
<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Produto</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>
	<form action="salvarProduto" method="post" id="produto">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${obj.id}"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${obj.nome}" placeholder="Informe o nome"></td>
					</tr>
					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="quantidade" name="quantidade"
							value="${obj.quantidade}" placeholder="Informe a quantidade"></td>
					</tr>
					<tr>
						<td>Valor:</td>
						<td><input type="text" id="valor" name="valor"
							value="${obj.valor}" placeholder="Informe o valor"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action = 'salvarProduto?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<!-- Tabela -->
	<div class="container">
		<table id="customers">
		<caption>Produtos Cadastrados</caption>
			<!-- Cabeçalho -->
			<tr>
				<th>Código</th>
				<th>Nome</th>
				<th>Qtd.</th>
				<th>Valor</th>
				<th>Editar</th>
				<th>Excluir</th>
			</tr>

			<!-- Linhas -->
			<c:forEach items="${listaItens}" var="itens">
				<tr>
					<td><c:out value="${itens.id}" /></td>
					<td><c:out value="${itens.nome}" /></td>
					<td><c:out value="${itens.quantidade}" /></td>
					<td><c:out value="${itens.valor}" /></td>
					<td><a href="salvarProduto?acao=editar&id=${itens.id}"><img alt="Excluir" title="Excluir" src="resources/img/editar.png"></a></td>
					<td><a href="salvarProduto?acao=excluir&id=${itens.id}"><img alt="Editar" title="Editar" src="resources/img/excluir.png"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>