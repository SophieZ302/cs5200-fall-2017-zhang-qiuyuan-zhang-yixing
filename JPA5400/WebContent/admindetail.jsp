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
<title>Admin Page</title>
</head>

<body>
	<div class="container">
		<!-- header and sign out handle -->
		<%
			User user = (User) session.getAttribute("user");
			if (user == null) {
				response.sendRedirect("index.jsp");
			}
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
				response.sendRedirect("index.jsp");
			}
		%>
		<h1>
			<a href="index.jsp">Home Page</a>
			<form action="admindetail.jsp">
				<button type="submit" name="logout_action" value="logout"
					class="btn btn-secondary float-right">Logout</button>
			</form>
		</h1>
		<p>welcome to admin page</p>
		<br> <br>

		<!-- Handels search users -->
		<%
			String searchAction = request.getParameter("search_action");
			List<User> userList = null;
			boolean isSearch = false;
			UserDao udao = new UserDao();
			String usernametxt = request.getParameter("searchtxt");

			if ("search_user".equals(searchAction)) {
				userList = udao.findUserByName(usernametxt);
				isSearch = true;
			}

			if (isSearch && userList == null) {
		%><div class="alert alert-warning" >
			<strong>Warning!</strong> User not found, please use correct name
		</div>
		<%
			}
		%>
		<!-- Search users -->
		<form action="admindetail.jsp">
			<div class="input-group">
				<input name="searchtxt" type="text" class="form-control"
					placeholder="Search for username ...">
				<button name="search_action" value="search_user"
					class="btn btn-secondary" type="searchBtn">search</button>
			</div>
		</form>
		<br> <br>

		<!-- List all users -->
		<h3>All Users</h3>
		<br>
		<table class="table table-striped">
			<tr>
				<td>user name</td>
				<td>email</td>
				<td>role</td>
			</tr>
			<%
				if (userList == null) {
					userList = udao.findAllUser();
				}
				for (User person : userList) {
					String username = person.getUsername();
					String email = person.getEmail();
					String type = "user";
					if (person instanceof Admin) {
						type = "admin";
					} else if (person instanceof Producer) {
						type = "producer";
					} else if (person instanceof Regular) {
						type = "regular";
					} else if (person instanceof Critique) {
						type = "critique";
					}
			%>
			<tr>
				<td><a href="profile.jsp?<%=person.getId()%>"><%=username%></a></td>
				<td><%=email%></td>
				<td><%=type%></td>
			</tr>
			<%
				}
			%>
		</table>
		


		<!-- List all movies -->
		<!-- Handels search users -->
		<%
			String searchAction2 = request.getParameter("search_action");
			List<Movie> movieList = null;
			boolean isSearchMovie = false;
			MovieDao mdao = new MovieDao();
			String moviename = request.getParameter("searchtxt");

			if ("search_movie".equals(searchAction)) {
				movieList = mdao.getMoviesWithName(moviename);
				isSearchMovie = true;
			}

			if (isSearchMovie && movieList == null) {
		%><div class="alert alert-warning" >
			<strong>Warning!</strong> Movie not found, please use correct name
		</div>
		<%
			}
		%>
		<!-- Search Movies-->
		<br> <br>
		<form action="admindetail.jsp">
			<div class="input-group">
				<input name="searchtxt" type="text" class="form-control"
					placeholder="Search for moviename ...">
				<button name="search_action" value="search_movie"
					class="btn btn-secondary" type="searchBtn">search</button>
			</div>
		</form>
		
		<!-- List all movies -->
		<br><br>
		<h3>All Movies</h3>
		<br>
		<table class="table table-striped">
			<tr>
				<td>movie name</td>
				<td>plot</td>
				<td>prof rating</td>
				<td>popular rating</td>
			</tr>
			<%
				if (movieList == null) {
					movieList = mdao.findAllMovie();
				}
				for (Movie movie : movieList) {
			%>
			<tr>
				<td><a href="editmovie.jsp?<%=movie.getId()%>"><%=movie.getTitle()%></a></td>
				<td><%=movie.getDescription() %></td>
				<td><%=movie.getCritiqueRate()%></td>
				<td><%=movie.getRegularRate() %></td>
			</tr>
			<%
				}
			%>
		</table>
		
		
	</div>
</body>
</html>