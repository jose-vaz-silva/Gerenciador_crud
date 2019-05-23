<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="Stylesheet" href="resources/css/cadastro.css">
<link rel="Stylesheet" href="resources/css/table.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"
	type="text/javascript"></script>
</head>
<body>
	<a href="acessoLiberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h2>Cadastro de Usuario</h2>
		<h3 style="color: orange">
			<c:out value="${msg}"></c:out>
		</h3>
	</center>

	<form action="UsuarioServlet" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false" enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" class="field-long"
							name="id" id="id" value="${user.id}"></td>

						<td>Cep:</td>
						<td><input type="text" name="cep" id="cep"
							onblur="consultaCep()" value="${user.cep}"></td>
					</tr>

					<tr>
						<td>Login:</td>
						<td><input type="text" class="field-long" name="login"
							id="login" value="${user.login}"></td>

						<td>Rua:</td>
						<td><input type="text" name="rua" id="rua"
							value="${user.rua }"></td>
					</tr>

					<tr>
						<td>Nome:</td>
						<td><input type="text" name="nome" id="nome"
							value="${user.nome}" class="field-long"></td>

						<td>Bairro:</td>
						<td><input type="text" name="bairro" id="bairro"
							value="${user.bairro}"></td>
					</tr>

					<tr>
						<td>Senha:</td>
						<td><input type="text" class="field-long" name="senha"
							id="senha" value="${user.senha}"></td>

						<td>cidade:</td>
						<td><input type="text" name="cidade" id="cidade"
							readonly="readonly" value="${user.cidade}"></td>
					</tr>

					<tr>
						<td>Fone:</td>
						<td><input type="text" id="fone" name="fone"
							class="field-long" value="${user.fone}"></td>

						<td>Estado:</td>
						<td><input type="text" name="estado" id="estado"
							readonly="readonly" value="${user.estado}"></td>
					</tr>
					<tr>
						<td>IBGE:</td>
						<td ><input type="text" name="ibge" id="ibge"
							readonly="readonly" value="${user.ibge}"></td>
					</tr>
					
					<tr>
						<td>Foto:</td>
						<td><input type="file" value="foto" id="foto" name="foto"></td>
					</tr>
					
					<tr>
						<td>Currículo:</td>
						<td><input type="file" value="curriculo" id="curriculo" name="curriculo"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="salvar"> <input
							type="button" value="cancelar"
							onclick="limparCampos()"></td>
					</tr>

				</table>
			<li>
		</ul>
	</form>


	<div class="container">
		<table class="responsive-table">
			<caption>Usuarios Cadastrados</caption>
			<thead>
				<tr>
					<th scope="col">Código</th>
					<th scope="col">Nome</th>
					<th scope="col">Foto</th>
					<th scope="col">Currículo</th>
					<th scope="col">Fone</th>
					<th scope="col">login</th>
					<th scope="col">Senha</th>
					<th scope="col">Delete</th>
					<th scope="col">Editar</th>
					<th scope="col">Telefones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<td><c:out value="${user.id}"></c:out></td>
						<td><c:out value="${user.nome}"></c:out></td>
						<td><a href="UsuarioServlet?acao=download&id=${user.id}"><img src='<c:out value="${user.imagemTipo}"></c:out>' width="30px" height="30px"></a></td>
						<td><a href="UsuarioServlet?acao=downloadPdf&id=${user.id}"><c:out value="curriculo"></c:out></a></td>
						<td><c:out value="${user.fone}"></c:out></td>
						<td><c:out value="${user.login}"></c:out></td>
						<td><c:out value="${user.senha}"></c:out></td>
						<td><a href="UsuarioServlet?acao=delete&id=${user.id}"><img
								alt="Excluir" src="resources/img/excluir.png" width="30px"
								height="30px" title="Excluir"></a></td>
						<td><a href="UsuarioServlet?acao=edit&id=${user.id}"><img
								alt="Editar" title="Editar" width="30px" height="30px"
								src="resources/img/edit.png"></a></td>
						<td><a href="TelefoneServlet?user=${user.id}&acao=listar"><img alt="Telefones" title="Telefones" 
								width="30px" height="30px" src="resources/img/cadFone.png"></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById('login').value == '') {
				alert("Informe o login.");
				return false;
			} else if (document.getElementById('nome').value == '') {
				alert("Informe o nome.");
				return false;
			} else if (document.getElementById('senha').value == '') {
				alert("Informe a senha.");
				return false;
			} else if (document.getElementById('fone').value == '') {
				alert("Informe o telefone.");
			} else {
				return true;
			}
		}

		function consultaCep() {
			var cep = $("#cep").val();

			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} //end if.
						else {
							//CEP pesquisado não foi encontrado.
							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							alert("CEP não encontrado.");
						}
					});
			
		}
		
		function limparCampos(){
			document.getElementById('id').value = '';
			document.getElementById('login').value = '';
			document.getElementById('nome').value = '';
			document.getElementById('senha').value = '';
			document.getElementById('fone').value = '';
			document.getElementById('cep').value = '';
			document.getElementById('rua').value = '';
			document.getElementById('bairro').value = '';
			document.getElementById('cidade').value = '';
			document.getElementById('estado').value = '';
			document.getElementById('ibge').value = '';
			document.getElementById('foto').value = '';
			document.getElementById('curriculo').value = '';
		}
	</script>
</body>
</html>