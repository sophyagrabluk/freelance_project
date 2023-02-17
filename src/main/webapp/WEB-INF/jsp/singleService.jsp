<%@ page import="org.Service" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Service serviceJsp = (Service)request.getAttribute("service"); %>
<html>
<head>
    <title>Service Info</title>
</head>
<body>
<h1>   This is your User </h1>
<h2> Service id: <%= serviceJsp.getId()%> </h2>
<h2> Service name: <%= serviceJsp.getName()%> </h2>
<h2> Service section: <%= serviceJsp.getSection()%> </h2>
<h2> Service description: <%= serviceJsp.getDescription()%> </h2>
</body>
</html>