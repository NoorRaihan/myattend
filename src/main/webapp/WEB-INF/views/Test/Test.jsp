<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Assignment in Course</title>
    </head>
    <body>
        <h1>
            Course: ${course.getCourse_name()}
        </h1>
        <table>
            <thead>
                <tr>
                    <th>Assignment ID</th>
                    <th>Session ID</th>
                    <th>Course ID</th>
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
                    <th>Image path</th>
                    
                    <th>Course</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop through assignments -->
                <c:forEach var="assignment" items="${assignments}">
                    <tr>
                        <td>${assignment.getAssignment_id()}</td>
                        <td>${assignment.getSessionId()}</td>
                        <td>${assignment.getCourse_id()}</td>
                        <td>${assignment.getAssignment_header()}</td>
                        <td>${assignment.getAssignment_desc()}</td>
                        <td>${assignment.isDisabled_flag()}</td>
                        <td>${assignment.isBypass_time_flag()}</td>
                        <td>${assignment.getOri_filename()}</td>
                        <td>${assignment.getServer_filename()}</td>
                        <td>${assignment.getFile_path()}</td>
                        <td>${assignment.getStarted_at()}</td>
                        <td>${assignment.getEnded_at()}</td>
                        <td>${assignment.getCreated_at()}</td>
                        <td>${assignment.getUpdated_at()}</td>
                        <td>${assignment.getDeleted_at()}</td> 
                        <td>
                            <c:forEach var="filename" items="${fn:split(assignment.getServer_filename(), '|')}">
                                <img src="${assignment.getFile_path()}/${filename}" alt="Assignment Image">
                            </c:forEach>
                        </td>
                        <td>${assignment.getCourse().getCourse_name()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>