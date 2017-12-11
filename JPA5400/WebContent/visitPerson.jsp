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
<title>User Page</title>
</head>
<body>
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
		<!-- Handles login/logout, and search -->
		<%
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
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
		<%
			String id = request.getParameter("id");
			UserDao userDao = new UserDao();
			User person = userDao.findUserById(Integer.parseInt(id));
		%>
		<!-- handle like/unlike -->
		<%
			String like = request.getParameter("like");
			String unlike = request.getParameter("unlike");
			if (like != null) {
				userDao.like(user.getId(), person.getId());
			} else if (unlike != null) {
				userDao.disLike(user.getId(), person.getId());
			}
		%>
		<form action="visitPerson.jsp">
			<input name=id type="hidden" value=<%=id%>>
			<h1><%=person.getUsername()%>
				<%
					if (!userDao.isLike(user.getId(), person.getId())) {
				%>
				<button class="btn btn-primary left" type="submit" name="like"
					value="like">like</button>
				<%
					} else {
				%>
				<button class="btn btn-primary left" type="submit" name="unlike"
					value="unlike">unlike</button>
				<%
					}
				%>
			</h1>
		</form>

		<p>Comments</p>
		<table class="table table-striped">
			<tr>
				<th>Movie</th>
				<th>Rate</th>
				<th>Content</th>
			</tr>
			<%
				List<Comment> comments = person.getComments();
				for (Comment c : comments) {
			%>
			<tr>
				<td><%=c.getMovie().getTitle()%></td>
				<td><%=c.getRate()%></td>
				<td><%=c.getContent()%></td>
			</tr>
			<%
				}
			%>
		</table>

	</div>


</body>
</html>