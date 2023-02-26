<%@ page import="org.Feedback" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Feedback feedbackJsp = (Feedback)request.getAttribute("feedback"); %>
<html>
<head>
    <title>Feedback Info</title>
</head>
<body>
<h1>   This is your User </h1>
<h2> Feedback id: <%= feedbackJsp.getId()%> </h2>
<h2> Rating: <%= feedbackJsp.getRating()%> </h2>
<h2> Comment: <%= feedbackJsp.getComment()%> </h2>
<h2> User id, who texted this comment: <%= feedbackJsp.getFromWhichUserId()%> </h2>
<h2> User id, whom this comment belong to: <%= feedbackJsp.getToWhichUserId()%> </h2>
<h2> This comment created: <%= feedbackJsp.getCreated()%> </h2>

</body>
</html>