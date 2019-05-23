<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel="Stylesheet" href="resources/css/cadastro.css">
<link rel="Stylesheet" href="resources/css/table.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js"></script>

<body>
	<a href="acessoLiberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h2>Cadastro de Produtos</h2>
		<h3>${msg}</h3>
	</center>

	<form action="ProdutosServlet" method="post" id="formProdutos" onsubmit="return validarCampos() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Id:</td>
						<td><input type="text" name="id" id="id" readonly="readonly"
							class="field-long" value="${item.id}"></td>
					</tr>

					<tr>
						<td>Nome:</td>
						<td><input type="text" name="nome" id="nome"
							class="field-long" value="${item.nome}"></td>
					</tr>

					<tr>
						<td>Quantidade:</td>
						<td><input type="text" name="quantidade" id="quantidade"
							class="field-long" value="${item.quantidade}"></td>
					</tr>

					<tr>
						<td>valor R$:</td>
						<td><input type="text" name="valor" id="valor"
							class="field-long" placeholder="Ex: 12,00" value="${item.valor}"></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input type="submit" value="Cancelar" 
						onclick="document.getElementById('formProdutos').action = 'ProdutosServlet?acao=cancel'"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<div class="container">
	<table class="responsive-table">
	<caption>Produtos Cadastrados</caption>
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Valor</th>
				<th>Deletar</th>
				<th>Editar</th>
			</tr>
		</thead>
	<c:forEach items="${produtos}" var="produtos">
		<tr>
		<td><c:out value="${produtos.id}"></c:out></td>
		<td><c:out value="${produtos.nome}"></c:out></td>
		<td><c:out value="${produtos.quantidade}"></c:out></td>
		<td><c:out value="${produtos.valor}"></c:out></td>
		
		<td><a href="ProdutosServlet?acao=delete&id=${produtos.id}"><img src="resources/img/excluir.png" alt="Excluir" 
		title="Excluir" width="20px" height="20px"></a></td>
		<td><a href="ProdutosServlet?acao=edit&id=${produtos.id}"><img src="resources/img/edit.png" alt="Editar" 
		title="Editar" width="20px" height="20px"></a></td>
		</tr>
	</c:forEach>
	</table>
	</div>
	
	<script type="text/javascript">
		function validarCampos(){
			if(document.getElementById("nome").value == ''){
				alert("Infome o nome do produdo.");
				return false;
			}else if (document.getElementById("quantidade").value == ''){
				alert("Infome a quantidade de produtos.");
				return false;
			}else if (document.getElementById("valor").value == ''){
				alert("Infome o valor do produto.");
				return false;
			}else {
				return true;
			}
		}
		
		
		 $('#valor').mask("#.##0,00", {reverse: true});
	</script>
</body>
</html>