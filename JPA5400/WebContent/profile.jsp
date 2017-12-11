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
<title>Profile</title>
</head>
<body>
	<div class=container>
		<a href="index.jsp"><h1>Home Page</h1></a>
		<%
			UserDao userDao = new UserDao();
			User user = (User) session.getAttribute("user");
			User current = user;
			String type = "user";
			if (user instanceof Admin) {
				type = "admin";
			} else if (user instanceof Producer) {
				type = "producer";
			} else if (user instanceof Critique) {
				type = "critique";
			} else if (user instanceof Regular) {
				type = "regular";
			}

			//if request is from admin, it is a admin view, then get user by userId
			//else redirect to admin page
			String userId = request.getParameter("userId");
			//System.out.println(userId);
			if (type.equals("admin")) {
				if (userId == null || userId.length() == 0) {
					response.sendRedirect("admindetail.jsp");
				} else {
					user = userDao.findUserById(Integer.valueOf(userId));
					type = "user";
					if (user instanceof Admin) {
						type = "admin";
					} else if (user instanceof Producer) {
						type = "producer";
					} else if (user instanceof Critique) {
						type = "critic";
					} else if (user instanceof Regular) {
						type = "regular";
					}
				}
			}
		%>



		<!-- Handles login/logout, and search -->
		<%
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
				response.sendRedirect("index.jsp");
			}
		%>
		<!-- Handles delete user -->
		<%
			String actionDeleteUser = request.getParameter("delete_user_action");
			if ("delete".equals(actionDeleteUser)) {
				String uId = request.getParameter("userId");
				UserDao udao = new UserDao();
				if (uId != null && uId.length() != 0) {
					udao.deleteUser(Integer.valueOf(uId));
				}
				System.out.println(uId + "deleted");
				response.sendRedirect("admindetail.jsp");
			}
		%>

		<!-- Handles update comment -->
		<%
			CommentDao commentDao = new CommentDao();
			String operation = request.getParameter("operation");
			String commentId = request.getParameter("commentId");
			String rating = request.getParameter("rating");
			String content = request.getParameter("content");
			if ("updateComment".equals(operation)) {
				Comment old = commentDao.findCommentById(Integer.parseInt(commentId));
				if (rating != null) {
					old.setRate(Integer.parseInt(rating));
				}
				if (content != null && !content.equals("")) {
					old.setContent(content);
				}
				commentDao.updateComment(old.getId(), old);
				System.out.println("update comment success");
			}
		%>
		<!-- Handles delete comment -->
		<%
			if ("deleteComment".equals(operation)) {
				commentDao.deleteComment(Integer.parseInt(commentId));
			}
		%>
		<!-- Handles critic name update -->
		<%
			CritiqueDao cdao = new CritiqueDao();
			if ("updateCriticName".equals(operation)) {
				String first = request.getParameter("criticFirst");
				String last = request.getParameter("criticLast");
				if (first != null && !first.equals("") && last != null && !last.equals("")) {
					cdao.updateCritiqueFirstLastName(user.getId(), first, last);
				}
				userDao.updateUser(user.getId(), user);
				System.out.println("update profile success");
				response.sendRedirect("profile.jsp?userId=" + user.getId());
			}
		%>
		<!-- Handles producer company update -->

		<%
			ProducerDao pdao = new ProducerDao();
			if ("updateProducerCmpany".equals(operation)) {
				String company = request.getParameter("company");
				if (company != null && !company.equals("")) {
					pdao.updateProducerCompany(user.getId(), company);
				}
				userDao.updateUser(user.getId(), user);
				System.out.println("update company success");
				response.sendRedirect("profile.jsp?userId=" + user.getId());
			}
		%>


		<!-- Handles update profile -->
		<%
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			if ("updateProfile".equals(operation)) {
				if (password != null && !password.equals("")) {
					user.setPassword(password);
				}
				if (email != null && !email.equals("")) {
					user.setEmail(email);
				}
				userDao.updateUser(user.getId(), user);
				System.out.println("update profile success");
				//response.sendRedirect("profile.jsp?userId=" + user.getId());
			}
		%>
		<!-- Handles create movie -->
		<%
			if (type.equals("producer") && "createMovie".equals(operation)) {
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				Movie movie = new Movie(title, description);
				movie.setProducer((Producer) user);
				((Producer) user).getMovies().add(movie);
				MovieDao movieDao = new MovieDao();
				movieDao.createMovie(movie);
			}
		%>
		<%
			if (current instanceof Admin) {
		%>
		<h2>
			<form action="profile.jsp">
				<input type="hidden" name="userId" value=<%=userId%>>
				<button type="submit" name="delete_user_action" value="delete"
					class="btn btn-danger float-right">Delete User</button>
			</form>
		</h2>
		<%
			} else {
		%>
		<h2>
			<form action="profile.jsp">
				<input name="userId" type="hidden" value=<%=user.getId()%>>
				<button type="submit" name="logout_action" value="logout"
					class="btn btn-secondary float-right">Logout</button>
			</form>
		</h2>
		<%
			}
		%>



		<br> <br>
		<!-- finish header -->
		<h1>Profile</h1>
		<table class="table table-striped">
			<tr>
				<th>user name</th>
				<th>password</th>
				<th>email</th>
			</tr>
			<tr>
				<td><%=user.getUsername()%></td>
				<td><%=user.getPassword()%></td>
				<td><%=user.getEmail()%></td>
			</tr>
			<tr>
				<form>
					<td></td> <input name="userId" type="hidden"
						value=<%=user.getId()%>>
					<td><input name="password" class=form-control
						placeholder="passowrd" /></td>
					<td><input name="email" class=form-control placeholder="email" /></td>
					<td><button class="btn btn-primary" type="submit"
							name="operation" value="updateProfile">Update</button></td>
				</form>
			</tr>
		</table>

		<!--  user info table, name, password, email -->
		<table class="table table-striped">
			<tr>
				<th>user type</th>
				<%
					if (type.equals("critic")) {
				%>
				<th>first name</th>
				<th>last name</th>
				<th>update</th>
				<%
					}
				%>
				<%
					if (type.equals("producer")) {
				%>
				<th>company name</th>
				<th>update</th>
				<%
					}
				%>
			</tr>
			<tr>
				<td><%=type%></td>
				<%
					if (type.equals("critic")) {
				%>
				<td><%=((Critique) user).getFirstname()%></td>
				<td><%=((Critique) user).getLastname()%></td>
				<%
					}
				%>
				<%
					if (type.equals("producer")) {
				%>
				<td><%=((Producer) user).getCompanyName()%></td>
				<%
					}
				%>
			</tr>

			<tr>
				<td></td>
				<%
					if (type.equals("critic")) {
				%>
				<form action="profile.jsp">
					<input name="userId" type="hidden" value=<%=user.getId()%>>
					<td><input name=criticFirst placeholder="real name"></td>
					<td><input name=criticLast placeholder="real name"></td>
					<td><button class="btn btn-primary" type="submit"
							name="operation" value="updateCriticName">Update</button></td>
				</form>
				<%
					}
				%>
				<%
					if (type.equals("producer")) {
				%>
				<form action="profile.jsp">
					<input name="userId" type="hidden" value=<%=user.getId()%>>
					<td><input name=company placeholder="Company Name"></td>
					<td><button class="btn btn-primary" type="submit"
							name="operation" value="updateProducerCmpany">Update</button></td>
				</form>

				<%
					}
				%>
			</tr>


		</table>

		<h1>Comments</h1>

		<table class="table table-striped">
			<tr>
				<th>Movie</th>
				<th>Rate</th>
				<th>Content</th>
				<th>Operation</th>
			</tr>
			<%
				List<Comment> comments = commentDao.getCommentsbyUser(user);
				for (Comment c : comments) {
			%>
			<form action="profile.jsp">
				<input name="userId" type="hidden" value=<%=user.getId()%>>
				<input name=commentId type="hidden" value=<%=c.getId()%>>
				<tr>
					<td><%=c.getMovie().getTitle()%></td>
					<td><%=c.getRate()%></td>
					<td><%=c.getContent()%></td>
					<td><button class="btn btn-danger" type="submit"
							name="operation" value="deleteComment">Delete</button></td>
				</tr>
			</form>
			<form action="profile.jsp">
				<tr>
					<input name="userId" type="hidden" value=<%=user.getId()%>>
					<td><input name=commentId type="hidden" value=<%=c.getId()%>></td>
					<td><select class="custom-select mb-2 mr-sm-2 mb-sm-0"
						name="rating" id="inlineFormCustomSelect">
							<option selected>choose rating</option>
							<option value="0">0/5</option>
							<option value="1">1/5</option>
							<option value="2">2/5</option>
							<option value="3" selected="selected">3/5</option>
							<option value="4">4/5</option>
							<option value="5">5/5</option>
					</select></td>
					<td><input name="content" class=form-control
						placeholder="content" /></td>
					<td><button class="btn btn-primary" type="submit"
							name="operation" value="updateComment">Update</button></td>
				</tr>
			</form>
			<%
				}
			%>
		</table>

		<%
			if (type.equals("producer")) {
		%>
		<h1>Movies</h1>

		<table class="table table-striped">
			<tr>
				<th>title</th>
				<th>Description</th>
			</tr>
			<form action="profile.jsp">
				<tr>
					<input name="userId" type="hidden" value=<%=user.getId()%>>
					<td><input name="title" class=form-control placeholder="title" /></td>
					<td><input name="description" class=form-control
						placeholder="description" /></td>
					<td><button class="btn btn-primary" type="submit"
							name="operation" value="createMovie">Create</button></td>
				</tr>
			</form>
			<%
				MovieDao movieDao = new MovieDao();
					List<Movie> movies = movieDao.getMoviesByProducer((Producer) user);
					for (Movie m : movies) {
			%>
			<tr>
				<td><%=m.getTitle()%></td>
				<td><%=m.getDescription()%></td>
				<td><a href="editmovie.jsp?movieId=
				<%=m.getId()%>">edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			}
		%>

		<h1>Like list</h1>
		<ul class="list-group">
		<%
			List<User> likeList = userDao.getLikeList(user.getId());
			for (User u : likeList) {
		%>
		<li class="list-group-item"><a href="visitPerson.jsp?id=<%=u.getId()%>"><%=u.getUsername()%></a></li>
		<%
			}
		%>
		</ul>
	</div>

</body>
</html>