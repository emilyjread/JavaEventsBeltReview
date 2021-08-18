<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Edit Event</title>
</head>
<body>
<h1>Edit Event</h1>

<form:form action="/events/${event.id}" method="post" modelAttribute="event">
    <input type="hidden" name="_method" value="put">
   	<form:label path="name">Name: </form:label>
        <form:errors path="name"/>
       	<form:input path="name"/>
       	
       	<form:label path="date">Date: </form:label>
        <form:errors path="date"/>
       	<form:input type= "date" path="date"/>
       	
       	<form:label path="city">City: </form:label>
        <form:errors path="city"/>
       	<form:input path="city"/>
       	
       	<form:label path="state">State: </form:label>
        <form:errors path="state"/>
       	<form:input path="state"/>
       	
       	<form:label path="user"></form:label>
        <form:errors path="user"/>
       	<form:input type="hidden" path="user" value="${user.id}"/>
  
    <input type="submit" value="Submit"/>
</form:form>   
	
</body>
</html>