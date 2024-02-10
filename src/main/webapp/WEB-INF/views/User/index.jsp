<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>User Page</title>
    </head>
    <body>
        <form action="create" method="POST">
            <input name="fullname" placeholder="fullname"/><br>
            <input name="username" placeholder="username"/><br>
            <input type="email" name="email" placeholder="email"/><br>
            <input name="password" placeholder="password" /><br>
            <input type="radio" name="gender" value="M">
            <label>Lelaki</label><br>
            <input type="radio" name="gender" value="F">
            <label>Perempuan</label><br>
            <input type="date" name="birthdate"><br>
            <input type="radio" name="role" value="1">
            <label>Admin</label><br>
            <input type="radio" name="role" value="2">
            <label>Lecturer</label><br>
            <input type="radio" name="role" value="3">
            <label>Student</label><br>
            <input type="radio" name="role" value="4">
            <label>Registant</label><br>
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>