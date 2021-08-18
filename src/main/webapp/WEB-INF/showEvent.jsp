<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Show Event</title>
</head>
<body>
<a href="/dashboard">Home</a>
<!-- EVENT INFO -->
	<h1><c:out value="${event.name}"/></h1>
	<p>Host: <c:out value="${event.user.firstName}"/></p>
	<p>Date: <fmt:formatDate type="date" dateStyle="long" value="${event.date}"/></p>
	<p>Location: <c:out value="${event.city}, ${event.state}"/></p>
	<p>Attendees: <c:out value="${attendeesLength}"/></p>
	
<!-- ALL MESSAGES -->
 	<div>
	 	<ul>
			<c:forEach items="${messages}" var="m">
				<li>From <c:out value="${m.user.firstName}"/>: <c:out value="${m.content}"/></li>
			</c:forEach>
		</ul>
	</div>
	
<!-- 	CREATE MESSAGE -->

<h2>Add Message</h2>
	
	<form:form action="/addmessage" method="post" modelAttribute="message">
	 	<form:label path="content">Your Message: </form:label>
        <form:errors path="content"/>
       	<form:input path="content"/>
      
       	
       	<form:label path="event"></form:label>
        <form:errors path="event"/>
       	<form:input type="hidden" path="event" value="${event.id}"/>
       	
       	<form:label path="user"></form:label>
        <form:errors path="user"/>
       	<form:input type="hidden" path="user" value="${user_id}"/>
       	
       	<input type="submit" value="Add Message"/>
	</form:form> 
	
<!-- 	ATTENDEES -->
<div>
<h2>Attendees</h2>
	 	<ul>
			<c:forEach items="${attendees}" var="a">
				<li>- <c:out value="${a.firstName} ${a.lastName}: ${a.city} "/></li>
			</c:forEach>
		</ul>
	</div>


</body>
</html>