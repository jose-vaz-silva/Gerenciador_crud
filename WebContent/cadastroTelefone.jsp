<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="Stylesheet" href="resources/css/cadastro.css">
<link rel="Stylesheet" href="resources/css/table.css">
</head>
<body>
	<a href="UsuarioServlet?acao=listar">voltar</a>
	<center>
		<h2>Cadastro de telefones</h2>
		<h3>${msg}</h3>
	</center>

	<form action="TelefoneServlet" method="post" onsubmit="return validarCampos() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário:</td>
						<td><input type="text" id="id" name="id" readonly="readonly"
							class="field-long" value="${user.id}"></td>
						<td><input type="text" readonly="readonly" id="nome"
							name="nome" class="field-long" value="${user.nome}"></td>
					</tr>

					<tr>
						<td>Telefone:</td>
						<td><input type="text" id="telefone" name="telefone"
							value="${fone.numero}"></td>
						<td><select id="tipo" name="tipo">
								<option>Casa</option>
								<option>Trabalho</option>
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
		<table class="responsive-table">
			<caption>Telefones Cadastrados</caption>
			<thead>
				<tr>
					<th>Id</th>
					<th>Número</th>
					<th>Tipo</th>
					<th>Usuário</th>
					<th>Deletar</th>
				<tr>
			</thead>
			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td><c:out value="${fone.id}"></c:out></td>
					<td><c:out value="${fone.numero}"></c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>
					<td><c:out value="${fone.usuario}"></c:out></td>

					<td><a
						href="TelefoneServlet?acao=delete&telefoneId=${fone.id}&user=${fone.usuario}">
							<img src="resources/img/excluir.png" alt="Excluir"
							title="Excluir" width="20px" height="20px">
					</a></td>
				<tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos(){
			if(document.getElementById("telefone").value == ""){
				alert("Informe o telefone");
				return false;
			}else{
				return true;
			}
		}
	
	</script>
</body>
</html>