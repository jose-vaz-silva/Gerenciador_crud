<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/index.css">
</head>
<body>
	<div class="login">
		<div class="login-screen">
			<div class="app-title">
			<h1>Login</h1></div>
				<form action="LoginServlet" method="post">
					<div class="login-form">
					<div class="control-group">
						<input type="text" placeholder="username" name="login" id="login" class="login-field"></div> 
							
					<div class="control-group">
						 <input type="text" placeholder="password" name="senha" id="senha" class="login-field"> </div> 
						 
					<div class="control-group">
						 <input type="submit" value="Logar" class="btn btn-primary btn-large btn-block"></div>
					</div>
				</form>
			</div>
		</div>
</body>
</html>