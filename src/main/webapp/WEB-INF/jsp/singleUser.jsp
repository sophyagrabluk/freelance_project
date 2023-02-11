<%@ page import="org.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User userJsp = (User)request.getAttribute("user"); %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
    <h1>   This is your User </h1>
    <h2> User id: <%= userJsp.getId()%> </h2>
    <h2> User first name: <%= userJsp.getFirstName()%> </h2>
    <h2> User last name: <%= userJsp.getLastName()%> </h2>
    <h2> User country: <%= userJsp.getCountry()%> </h2>
    <h2> User city: <%= userJsp.getCity()%> </h2>
    <h2> User rating: <%= userJsp.getRating()%> </h2>
    <h2> User login: <%= userJsp.getLogin()%> </h2>
    <h2> User password: <%= userJsp.getPassword()%> </h2>
    <h2> User created date: <%= userJsp.getCreated()%> </h2>
</body>
</html>
