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
<title>Movie Detail</title>
</head>

<body>

	<%
		User user = (User) session.getAttribute("user");
		if (user instanceof Critique || user instanceof Regular) {
			response.sendRedirect("index.jsp");
		}
		String movieIdString = request.getParameter("movieId");
		MovieDao mdao = new MovieDao();
		int movieId = 0;
		Movie movie = null;
		if (movieIdString != null) {
			movie = mdao.findMovieById(Integer.parseInt(movieIdString));
			movieId = Integer.valueOf(movieIdString);
		}
		if (movie == null) {
	%>
	<div class="alert alert-warning">
		<strong>Warning!</strong> movie has been deleted
	</div>
	<h2>
		<a href="profile.jsp">Back Home</a>
	</h2>
	<%
		} else {
			String action = request.getParameter("action");
	%>

	<!-- Handles deleting movie -->
	<%
		if ("delete".equals(action)) {
				mdao.deleteMovie(movieId);
				System.out.println("deleted movie " + movieId);
				response.sendRedirect("editmovie.jsp?movieId=" + movieId);
			}
	%>

	<!-- Handles update movie title, description -->
	<%
		if ("update_word".equals(action)) {
				String title = request.getParameter("title");
				String des = request.getParameter("description");
				movie.setTitle(title);
				movie.setDescription(des);
				mdao.updateMovie(movieId, movie);
				System.out.println("updated movie words" + movieId);
				response.sendRedirect("editmovie.jsp?movieId=" + movieId);
			}
	%>
	<!-- Handles add director -->
	<%
		if ("create_director".equals(action)) {
				String first = request.getParameter("first");
				String last = request.getParameter("last");
				DirectorDao dd = new DirectorDao();
				Director dir = dd.getDirectorWithFirstLastName(first, last);
				if (dir == null) {
					dir = new Director(first, last);
				}
				mdao.addDirector(movie, dir);
				System.out.println("added director" + dir.toString());
				response.sendRedirect("editmovie.jsp?movieId=" + movieId);
			}
	%>

	<!-- Handles delete director -->
	<%
		if ("delete_director".equals(action)) {
				String dId = request.getParameter("directorId");
				System.out.println(dId);
				DirectorDao dd = new DirectorDao();
				if (dId != null && dId.length() != 0) {
					Director director = dd.findDirectorById(Integer.valueOf(dId));
					mdao.deleteDirector(movie, director);
					System.out.println("removed director" + dId);
					response.sendRedirect("editmovie.jsp?movieId=" + movieId);
				}
			}
	%>


	<!-- Handles add actor -->

	<%
		if ("create_actor".equals(action)) {
				String first = request.getParameter("first");
				String last = request.getParameter("last");
				ActorDao ad = new ActorDao();
				Actor act = ad.getActorWithFirstLastName(first, last);
				if (act == null) {
					act = new Actor(first, last);
				}
				mdao.addActor(movie, act);
				System.out.println("added actor" + act.toString());
				response.sendRedirect("editmovie.jsp?movieId=" + movieId);
			}
	%>


	<!-- Handles delete actor -->
	<%
		if ("delete_actor".equals(action)) {
				String dId = request.getParameter("actorId");
				System.out.println(dId);
				ActorDao dd = new ActorDao();
				if (dId != null && dId.length() != 0) {
					Actor actor = dd.findActorById(Integer.valueOf(dId));
					mdao.deleteActor(movie, actor);
					System.out.println("removed actor" + dId);
					response.sendRedirect("editmovie.jsp?movieId=" + movieId);
				}
			}
	%>

	<div class="container">

		<h1>
			<a href="index.jsp">Home Page</a>
			<form action="editmovie.jsp">
				<input type="hidden" name="movieId" value=<%=movieId%>>
				<button type="submit" name="action" value="delete"
					class="btn btn-danger float-right" href="login.jsp">Delete
					Movie</button>
			</form>
		</h1>

		<br> <br>
		<!-- finish header -->

		<h2><%=movie.getTitle()%></h2>
		<p>Info:</p>

		<table class="table table-striped">
			<tr>
				<th scope="row">Description</th>
				<td><%=movie.getDescription()%></td>
			</tr>
			<tr>
				<th scope="row">Popular Rating</th>
				<td>
					<%
						double pr1 = movie.getRegularRate();
					%> <%=pr1%> / 5
				</td>
			</tr>

			<tr>
				<th scope="row">Professional Rating</th>
				<td>
					<%
						double pr = movie.getRegularRate();
					%> <%=pr%> / 5
				</td>
			</tr>

			<tr>
				<form action="editmovie.jsp">
					<input type="hidden" name="movieId" value=<%=movieId%>>
				<td><input name="title" class=form-control
					placeholder="new title" /></td>
				<td><input name="description" class=form-control
					placeholder="new description" /></td>
				<td><button class="btn btn-primary" type="submit" name="action"
						value="update_word">update</button></td>
				</form>
			</tr>

		</table>
		<br> <br>
		<p>Directors</p>
		<table class="table table-striped">
			<tr>
				<td>first name</td>
				<td>last name</td>
				<td>action</td>
			</tr>
			<tr>
				<form action="editmovie.jsp">
					<input type="hidden" name="movieId" value=<%=movieId%>>
				<td><input name="first" class=form-control placeholder="first" /></td>
				<td><input name="last" class=form-control placeholder="last" /></td>
				<td><button class="btn btn-primary" type="submit" name="action"
						value="create_director">Create</button></td>
				</form>
			</tr>

			<%
				List<Director> directors = movie.getDirectors();
					for (Director i : directors) {
						String firstName = i.getFirstName();
						String lastName = i.getLastName();
						String name = firstName + " " + lastName + "; ";
			%>
			<tr>
				<td><%=firstName%></td>
				<td><%=lastName%></td>
				<form action="editmovie.jsp">
					<input type="hidden" name="movieId" value=<%=movieId%>> <input
						type="hidden" name="directorId" value=<%=i.getId()%>>
				<td><button class="btn btn-danger" type="submit" name="action"
						value="delete_director">Delete</button></td>
				</form>
			</tr>
			<%
				}
			%>

		</table>

		<br> <br>
		<p>Actors</p>
		<table class="table table-striped">
			<tr>
				<td>first name</td>
				<td>last name</td>
				<td>action</td>
			</tr>
			<tr>
				<form action="editmovie.jsp">
					<input type="hidden" name="movieId" value=<%=movieId%>>
				<td><input name="first" class=form-control placeholder="first" /></td>
				<td><input name="last" class=form-control placeholder="last" /></td>
				<td><button class="btn btn-primary" type="submit" name="action"
						value="create_actor">Create</button></td>
				</form>
			</tr>

			<%
				List<Actor> actors = movie.getActors();
					for (Actor i : actors) {
						String firstName = i.getFirstName();
						String lastName = i.getLastName();
			%>
			<tr>
				<form action="editmovie.jsp">
					<input type="hidden" name="movieId" value=<%=movieId%>>
					<input type="hidden" name="actorId" value=<%=i.getId()%>>
				<td><%=firstName%></td>
				<td><%=lastName%></td>
				<td><button class="btn btn-danger" type="submit" name="action"
						value="delete_actor">Delete</button></td>
				</form>
			</tr>
			<%
				}
			%>

		</table>

		<br> <br>

	</div>
	<%
		}
	%>
</body>
</html>