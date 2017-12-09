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
<title>Register</title>
</head>
<body>
	<div class="container">
		<%
			String action = request.getParameter("createUser");
			String name = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String type = request.getParameter("type");
			if ("create".equals(action)) {
				if (type.equals("1")) {
					Regular person = new Regular(name, password, email);
					RegularDao rdao = new RegularDao();
					rdao.createRegular(person);
					session.setAttribute("user", person);
				} else if (type.equals("2")) {
					Critique person = new Critique(name, password, email);
					CritiqueDao dao = new CritiqueDao();
					dao.createCritique(person);
					session.setAttribute("user", person);
				} else if (type.equals("3")) {
					Producer person = new Producer(name, password, email);
					ProducerDao dao = new ProducerDao();
					dao.createProducer(person);
					session.setAttribute("user", person);
				}
				response.sendRedirect("index.jsp");
			}
			
		%>

		<br>
		<h2 class="text-secondary">Please log in</h2>
		<form action="register.jsp">
			<div class="form-group">
				<label>Username</label> <input class="form-control" name="username"
					placeholder="Enter username">
			</div>
			<div class="form-group">
				<label>Email</label> <input class="form-control" name = "email"
					placeholder="abc@xmail.com">
			</div>
			<div class="form-group">
				<label>Password</label> <input class="form-control" name = "password"
					placeholder="Enter password">
			</div>

			<select class="custom-select mb-2 mr-sm-2 mb-sm-0" name = "type"
				id="inlineFormCustomSelect">
				<option selected>Choose a role</option>
				<option value="1">Movie Viewer - give comments and ratings
					on movies</option>
				<option value="2">Professional Critique - provide
					professional review and ratings movies</option>
				<option value="3">Producer - upload movie, actor details</option>
			</select>
			<button type="submit" name="createUser" value="create"
				class="btn btn-primary">Submit</button>
		</form>
	</div>
</body>
</html>