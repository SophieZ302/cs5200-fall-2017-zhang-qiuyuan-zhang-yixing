<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<body>
    <form action="register" method="post">
        Name:<input type="text" name="userName"/><br/>
        Password:<input type="password" name="password"/><br/>
        Email Id:<input type="text" name="email" /><br/>
        Language: <select name="role">
            <option>Regular</option>
            <option>Critique</option>
            <option>Producer</option>
        </select> <br/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>