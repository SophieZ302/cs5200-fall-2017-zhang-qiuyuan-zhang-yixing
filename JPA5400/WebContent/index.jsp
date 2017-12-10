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
<title>Movie Reviews</title>
</head>

<body>
	<div class="container">
		<br>
		<%
			User user = (User) session.getAttribute("user");
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
			}
		%>
		<%
			if (user == null) {
		%>
		<h2>
			<a href="index.jsp">Home Page</a> <a
				class="btn btn-success float-right" href="login.jsp">LogIn</a>
		</h2>
		<%
			} else {
		%>
		<h2>
			<a href="index.jsp">Home Page</a>
			<form action="index.jsp">
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

		<%
			boolean isSearch = false;
			List<Movie> movies = null;
			List<Actor> actors = null;
			List<Director> directors = null;
			String action2 = request.getParameter("search_action");
			String txt = request.getParameter("searchtxt");

			if (action2 != null && action2.equals("search") && txt != null && txt.length() != 0) {
				MovieDao md = new MovieDao();
				movies = md.getMoviesWithName(txt);

				ActorDao ad = new ActorDao();
				actors = ad.getActorsWithName(txt);

				DirectorDao dd = new DirectorDao();
				directors = dd.getDirectorWithName(txt);

				isSearch = true;
			}
		%>

		<br> <br> <br> <br>

		<form action="index.jsp">
			<div class="input-group">
				<input name="searchtxt" type="text" class="form-control"
					placeholder="Search for movies...">
				<button name="search_action" value="search"
					class="btn btn-secondary" type="searchBtn">Go!</button>
			</div>
		</form>
		<!-- /input-group -->

		<%
			if (isSearch) {
				JsonWebServiceClient cl = new JsonWebServiceClient();
				ApiMovieData movieApiData = cl.fetchData(txt);
				if (movieApiData == null) {
				%><div class="alert alert-warning" role="alert">
					<strong>Warning!</strong> Movie not found, please use correct name
					</div>
		<%
				} else {
		%>
		<br> <br>
		<h3>Movie From IMDB API</h3>
		<br>
		<table class="table table-striped">
			<tr>
				<td>Name</td>
				<td>Plot</td>
				<td>Rating</td>
			</tr>
			<tr>
				<td><a
					href="apimoviedetail.jsp?movieId=
				<%=movieApiData.getId()%>"><%=movieApiData.getTitle()%></a></td>
				<td><%=movieApiData.getPlot()%></td>
				<td><%=movieApiData.getRating()%></td>
			</tr>
		</table>
		<%
			}
			}
		%>


		<%
			MovieDao dao = new MovieDao();
			if (!isSearch && movies == null) {
				movies = dao.findAllMovie();
			}
		%>
		<br> <br>
		<%
			if (movies != null && movies.size() != 0) {
		%>
		<h3>All Movies From Database</h3>
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
				<td><a href="moviedetail.jsp?movieId=
				<%=movie.getId()%>"><%=movie.getTitle()%></a></td>
				<td><%=movie.getCritiqueRate()%></td>
				<td><%=movie.getRegularRate()%></td>
			</tr>
			<%
				}
			%>
		</table>

		<%
			}
			if (isSearch) {
		%>
		<br> <br>
		<%
			if (actors != null && actors.size() != 0) {
		%>
		<h4>All Actors</h4>
		<table class="table table-striped">
			<%
				for (Actor actor : actors) {
							String actorname = actor.getFirstName() + " " + actor.getLastName();
			%>
			<tr>
				<td><a href="actordetail.jsp?actorId=<%=actor.getId()%>"><%=actorname%></a></td>
			</tr>
			<%
				}
			%>
		</table>
		<br> <br>
		<%
			}
		%>
		<%
			if (directors != null && directors.size() != 0) {
		%>
		<h4>All Directors</h4>
		<table class="table table-striped">
			<%
				for (Director d : directors) {
							String name = d.getFirstName() + " " + d.getLastName();
			%>
			<tr>
				<td><a href="directordetail.jsp?directorId=<%=d.getId()%>"><%=name%></a></td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			}
			}
		%>
	</div>
</body>
</html>