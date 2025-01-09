<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Test Page</title>
    </head>
    <body>
        <form action="login" method="POST">
            <input name="username" placeholder="username"/>
            <input name="password" placeholder="password" />
            <input type="submit" value="submit"/>
        </form>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Session ID</th>
                    <th>Session</th>
                    <th>Course ID</th>
                    <th>Course</th>
                    <th>Header</th>
                    <th>Description</th>
                    <th>Disabled flag</th>
                    <th>Bypass Time flag</th>
                    <th>Original File Name</th>
                    <th>Server File Name</th>
                    <th>File Path</th>
                    <th>Started At</th>
                    <th>Ended At</th>
                    <th>Created At</th>
                    <th>Updated At</th>
                    <th>Deleted At</th>
                </tr>
            </thead>
            <tbody>
                ${assignments}
            </tbody>
        </table>
    </body>
</html>