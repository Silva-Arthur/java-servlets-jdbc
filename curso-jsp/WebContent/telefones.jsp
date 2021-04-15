<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="resources/css/cadastro.css" rel="stylesheet">
<title>Cadastro de telefones</title>

</head>
<body>

	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>

	<center>
		<h1>Cadastro de telefones</h1>
		<h3 style="color: orange;">${msg}</h3>
	</center>

	<form action="salvarTelefones" method="post" id="formTelefones"
		onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>User:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							class="field-long" value="${userEscolhido.id}"></td>
						<td>Nome:</td>
						<td><input type="text" readonly="readonly" id="nome"
							name="nome" class="field-long" value="${userEscolhido.nome}"></td>
					</tr>

					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" placeholder="Informe o número"></td>
						<td>Tipo:</td>
						<td><select id="tipo" name="tipo">
								<option>Casa</option>
								<option>Contato</option>
								<option>Celular</option>
						</select></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"></td>
					</tr>

				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table id="customers">
			<caption>Usuários Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Número</th>
				<th>Tipo</th>
				<th>Excluir</th>
			</tr>
			<c:forEach items="${listaItens}" var="obj">
				<tr>
					<td style="width: 150px;"><c:out value="${obj.id}" /></td>
					<td style="width: 150px;"><c:out value="${obj.numero}" /></td>
					<td><c:out value="${obj.tipo}" /></td>
					<td><a href="salvarTelefones?acao=delete&id=${obj.id}"><img
							alt="Excluir" title="Excluir" src="resources/img/excluir.png"
							width="20" height="20"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("numero").value == '') {
				alert("Informe o número!");
				return false;
			} else if (document.getElementById("tipo").value == '') {
				alert("Informe o tipo!");
				return false;
			}

			return true;
		}
	</script>


</body>
</html>