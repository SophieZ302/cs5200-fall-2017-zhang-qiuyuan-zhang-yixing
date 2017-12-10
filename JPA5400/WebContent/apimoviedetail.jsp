<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="edu.neu.cs5200.orm.jpa.daos.*,  edu.neu.cs5200.orm.jpa.entities.*, edu.neu.cs5200.orm.jpa.jsonwebservice.*,java.util.*"%>
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
<title>Movie Detail</title>
</head>

<body>

	<%
		String movieId = request.getParameter("movieId");
		JsonWebServiceClient api = new JsonWebServiceClient();
		ApiMovieData movie = api.fetchDataById(movieId);
		System.out.println(movie.toString());
	%>
	<div class="container">
		<h1>
			<a href="index.jsp">Home Page</a>
		</h1>

		<!-- Handles login/logout, and search -->
		<%
			User user = (User) session.getAttribute("user");
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
				response.sendRedirect("index.jsp");
			}
		%>
		<%
			if (user == null) {
		%>
		<h2>
			<a class="btn btn-success float-right" href="login.jsp">LogIn</a>
		</h2>
		<%
			} else {
		%>
		<h2>
			<form action="apimoviedetail.jsp">
				<button type="submit" name="logout_action" value="logout"
					class="btn btn-secondary float-right">Logout</button>
			</form>
		</h2>
		<p class="float-left">
			Welcome
			<%=user.getUsername()%>
			<a href="profile.jsp">[profile]</a>
		</p>
		<form method="post" action="index.jsp"></form>
		<%
			}
		%>
		<br> <br>
		<!-- finish header -->

		<h2><%=movie.getTitle()%></h2>
		<p>Info:</p>

		<table class="table table-striped">
			<tr>
				<th scope="row">IMDB Rating</th>
				<td>
					<%=movie.getRating()%> / 10
				</td>
			</tr>


			<tr>
				<th scope="row">Directors</th>
				<td>
					<%=movie.getDirectors()%>
				</td>
			</tr>
			<tr>
				<th scope="row">Actors</th>
				<td><%=movie.getActors() %></td>
			</tr>

			<tr>
				<th scope="row">Description</th>
				<td><%=movie.getPlot()%></td>
			</tr>
		</table>

		<br> <br>


	</div>

</body>
</html>