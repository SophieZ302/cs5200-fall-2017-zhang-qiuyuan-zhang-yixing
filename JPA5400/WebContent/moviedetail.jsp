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
		String movieId = request.getParameter("movieId");
		MovieDao mdao = new MovieDao();
		Movie movie = mdao.findMovieById(Integer.parseInt(movieId));
	%>
	<div class="container">

		<%
			User user = (User) session.getAttribute("user");
			if (user == null) {
		%><div class="alert alert-danger">
			<strong>Warning!</strong> Must login to leave a comment
		</div>
		<%
			}
		%>
		<h1>
			<a href="index.jsp">Home Page</a>
		</h1>


		<!-- Handles login/logout, and search -->
		<%
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
			}

			String action2 = request.getParameter("comment_action");

			if ("comment".equals(action2)) {
				if (user != null) {
					CommentDao cd = new CommentDao();
					String comment = request.getParameter("comment");
					int rate = Integer.valueOf(request.getParameter("rating"));
					Comment c = new Comment(comment, rate);
					cd.createComment(c, user.getId(), Integer.valueOf(movieId));
					response.sendRedirect("moviedetail.jsp?movieId=" + movieId);
				}
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
		<br> <br>
		<!-- finish header -->

		<h2><%=movie.getTitle()%></h2>
		<p>Info:</p>

		<table class="table table-striped">
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
				<th scope="row">Directors</th>
				<td>
					<%
						List<Director> directors = movie.getDirectors();
						for (Director i : directors) {
							String firstName = i.getFirstName();
							String lastName = i.getLastName();
							String name = firstName + " " + lastName + "; ";
					%><a href="directordetail.jsp?directorId=<%=i.getId()%>"><%=name%></a>
					<%
						}
					%>
				</td>
			</tr>
			<tr>
				<th scope="row">Actors</th>
				<td>
					<%
						List<Actor> actors = movie.getActors();
						for (Actor i : actors) {
							String firstName = i.getFirstName();
							String lastName = i.getLastName();
							String name = firstName + " " + lastName + "; ";
					%><a href="actordetail.jsp?actorId=<%=i.getId()%>"><%=name%></a> <%
 	}
 %>
				</td>
			</tr>

			<tr>
				<th scope="row">Description</th>
				<td><%=movie.getDescription()%></td>
			</tr>
		</table>

		<br> <br>

		<p>add a comment:</p>

		<form action="moviedetail.jsp">
			<input name=movieId type="hidden" value=<%=movieId%>> <input
				name="comment" class="form-control"
				placeholder="must login to comment"> <select
				class="custom-select mb-2 mr-sm-2 mb-sm-0" name="rating"
				id="inlineFormCustomSelect">
				<option selected>choose rating</option>
				<option value="0">0/5</option>
				<option value="1">1/5</option>
				<option value="2">2/5</option>
				<option value="3" selected="selected">3/5</option>
				<option value="4">4/5</option>
				<option value="5">5/5</option>
			</select>
			<button type="submit" class="btn btn-primary float-right"
				name="comment_action" value="comment">add a comment</button>
		</form>


		<br> <br> <br>
		<p>all comments:
		<table class="table-striped" style="width: 100%; height: 100%;">

			<%
				CommentDao cd = new CommentDao();
				List<Comment> comments = cd.getCommentsbyMovieId(movie);
				//List<Comment> comments = movie.getComments();

				for (Comment c : comments) {
					User u = c.getUser();
					String userName = u.getUsername();
					String content = c.getContent();
					int rating = c.getRate();
					String rated = "    rated: " + rating + "/5";
			%>
			<tr>
				<td>user: <a href="visitPerson.jsp?id=<%=u.getId()%>"><%=userName%></a></td>
				<td><%=rated%></td>
			</tr>

			<tr>
				<td><%=content%><br> <br></td>
				<td><br> <br></td>
			</tr>
			<%
				}
			%>
		</table>


	</div>

</body>
</html>