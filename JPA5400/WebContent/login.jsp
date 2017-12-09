<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="edu.neu.cs5200.orm.jpa.daos.*,  edu.neu.cs5200.orm.jpa.entities.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js""></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In</title>
</head>
<body>
	<div class="container">
		<%
			String action = request.getParameter("submit_form");
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			String type = request.getParameter("type");
			if ("login".equals(action)) {
				 
				
				
				response.sendRedirect("index.jsp");
			}
			
		%>
		
		<br>
	
	
		<br>
		<h2 class = "text-secondary">Please log in</h2>
		<form action = "login.jsp">
			<div class = "form-group">
				<label>Username</label>
				<input class = "form-control" name = "username" placeholder = "Enter username">
			</div>
			<div class = "form-group">
				<label>Password</label>
				<input class = "form-control" name = "password" placeholder = "Enter password">
			</div>
			<button type="submit" class="btn btn-primary" name = "submit_form" value = "login">Submit</button>
			<a href ="register.jsp" class="btn btn-secondary">Register New User</a>			
		</form>
	</div>
</body>
</html>