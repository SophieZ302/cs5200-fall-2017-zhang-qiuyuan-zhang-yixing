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
<title>Movie Reviews</title>
</head>

<body>
	<div class="container">
		<br>
		<h2> Home Page </h2>
			 <%
			 	User user = (User) session.getAttribute("user");
			 	if (user == null) {
			 %>
			 		<a class="btn btn-success float-right" href="login.jsp">LogIn</a>
			 <% 
			 	} else {
			 		%>
			 		<p class = "float-left"> Welcome <%= user.getUsername()%></p>			 		
			 		<a class="btn btn-secondary float-right" href="index.jsp">Logout</a>
			 		<%	
			 	} 
			%>
			
		
		<br>
		<div class="input-group">
			<input type="text" class="form-control"
				placeholder="Search for movies..."> <span
				class="input-group-btn">
				<button class="btn btn-secondary" type="searchBtn">Go!</button>
			</span>
		</div>
		<!-- /input-group -->
		
		<%
			MovieDao dao = new MovieDao();
			List<Movie> movies = dao.findAllMovie();
		%>
		<br>
		<br>
		<h3>All Movies</h3>
		<table class="table table-striped">
			<tr>
				<td>Name</td>
				<td>Professional Rating</td>
				<td>Popular Rating</td>
			</tr>
			<%
				for (Movie movie : movies) {
			%>
			<tr>
				<td><%=movie.getTitle()%></td>
				<td><%=movie.getCritiqueRate()%></td>
				<td><%=movie.getRegularRate()%></td>
			</tr>
			<%
				}
			%>
		</table>

	</div>
</body>
</html>