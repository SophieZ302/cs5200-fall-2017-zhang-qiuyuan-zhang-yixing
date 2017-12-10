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
		<%
			UserDao userDao = new UserDao();
			User user = (User) session.getAttribute("user");
			String type = "user";
			if (user instanceof Admin) {
				type = "admin";
			} else if (user instanceof Producer) {
				type = "producer";
			} else {
				type = "other";
			}
		%>
		<!-- Handles login/logout, and search -->
		<%
			String action = request.getParameter("logout_action");
			if ("logout".equals(action)) {
				session.setAttribute("user", null);
			}
		%>
		<!-- Handles update -->
		<%
			CommentDao commentDao = new CommentDao();
			String operation = request.getParameter("operation");
			String commentId = request.getParameter("commentId");
			String rating = request.getParameter("rating");
			String content = request.getParameter("content");
			if ("update".equals(operation)) {
				Comment old = commentDao.findCommentById(Integer.parseInt(commentId));
				if (rating != null) {
					old.setRate(Integer.parseInt(rating));
				}
				if (content != null || !content.equals("")) {
					old.setContent(content);
				}
				commentDao.updateComment(old.getId(), old);
				System.out.println("update success");
			}
		%>
		<!-- Handles delete -->
		<%
			if ("delete".equals(operation)) {
				commentDao.deleteComment(Integer.parseInt(commentId));
			}
		%>
		<h2>
			<form action="index.jsp">
				<button type="submit" name="logout_action" value="logout"
					class="btn btn-secondary float-right">Logout</button>
			</form>
		</h2>
		<br> <br>
		<!-- finish header -->
		<h1>Profile</h1>
		<table class="table table-striped">
			<tr>
				<th>username</th>
				<th>password</th>
				<th>email</th>
			</tr>
			<tr>
				<td><%=user.getUsername()%></td>
				<td><%=user.getPassword()%></td>
				<td><%=user.getEmail()%></td>
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
			<input name=commentId type="hidden" value=<%=c.getId()%>>
			<tr>
				<td><%=c.getMovie().getTitle()%></td>
				<td><%=c.getRate()%></td>
				<td><%=c.getContent()%></td>
				<td><button class="btn btn-danger" type="submit"
						name="operation" value="delete">Delete</button></td>
			</tr>
			</form>
			<form action="profile.jsp">
			<tr>
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
				<td><input name="content" class=form-control /></td>
				<td><button class="btn btn-primary" type="submit"
						name="operation" value="update">Update</button></td>
			</tr>
			</form>
			<%
				}
			%>
		</table>


	</div>

</body>
</html>