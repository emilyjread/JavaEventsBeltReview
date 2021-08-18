<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<!-- YOUR own local CSS -->
<link rel="stylesheet" href="/css/main.css"/>
<!-- For any Bootstrap that uses JS or jQuery-->
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	<meta charset="UTF-8">
	<title>Dashboard</title>
</head>
<body>
	<div class= "container">
	<a href="/logout">Logout</a>
		<h1>Welcome <c:out value="${user.firstName}"/></h1>
	
<!-- LOCAL EVENTS -->

	<h2>Here are some events near you: </h2>
	<div>
		<table class= "table table-striped table-bordered">
		<thead>
			<tr>
				<th>Name: </th>
				<th>Date: </th>
				<th>Location: </th>
				<th>Host: </th>
				<th>Actions: </th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${localEvents}" var= "e">
			<tr>
				<td><a href="/events/${e.id}"><c:out value="${e.name}"/></a></td>
				<td><fmt:formatDate type="date" dateStyle="long" value="${e.date}"/></td>
				<td><c:out value="${e.city}"/></td>
				<td><c:out value="${e.user.firstName}"/></td>
		<!-- IF USER EVENT -->
				<c:if test="${user.id==e.user.id}">
			<!-- delete -->			
					<td>
						<form class="btn" action="/events/${e.id}" method="post">
						    <input type="hidden" name="_method" value="delete">
						    <input type="submit" value="Delete"> 
						</form>
			<!--edit -->		
						<a class="btn btn-outline-primary" href="/events/${e.id}/edit">Edit</a>
					</td>
				</c:if>
		<!-- IF NOT USER EVENT -->
		
				<c:if test="${user.id!=e.user.id}">
				
				<c:forEach items="${attendees}" var="att">
					<c:if test="${user.id!=att.id}">
					<td>
						<a href="/events/${e.id}/join">Join</a>
						<c:out value="${e.attendees}"/>
					</td>
					</c:if>
				</c:forEach>
				
				<c:forEach items="${attendees}" var="attendee">
					<c:if test="${user.id==attendee.id}">
					<td>
						<a href="/events/${e.id}/cancel">Cancel</a>
					</td>
					</c:if>
				</c:forEach> 
					<td>
				<!-- join event -->
						<a class="btn btn-outline-success" href="/events/${e.id}/join">Join</a>				
				<!-- cancel event -->
						<a class="btn btn-outline-danger" href="/events/${e.id}/cancel">Cancel</a>
					</td>  
				</c:if>
			
			</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>	
	<!-- NOT LOCAL EVENTS -->
		<h2>Here are some events in other states: </h2>
		<div>	
		<table class= "table table-striped table-bordered">
		<thead>
			<tr>
				<th>Name: </th>
				<th>Date: </th>
				<th>Location: </th>
				<th>Host: </th>
				<th>Actions: </th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${notLocalEvents}" var= "e">
			<tr>
				<td><a href="/events/${e.id}"><c:out value="${e.name}"/></a></td>
				<td><fmt:formatDate type="date" dateStyle="long" value="${e.date}"/></td>
				<td><c:out value="${e.city}"/></td>
				<td><c:out value="${e.user.firstName}"/></td>
		<!-- IF USER EVENT -->
				<c:if test="${user.id==e.user.id}">
				<!-- delete -->			
					<td>
						<form class= "btn "action="/events/${e.id}" method="post">
						    <input type="hidden" name="_method" value="delete">
						    <input class="btn btn-outline-danger" type="submit" value="Delete"> 
						</form>
				<!--edit -->		
						<a class="btn btn-outline-primary" href="/events/${e.id}/edit">Edit</a>
					</td>
				</c:if>
		<!-- IF NOT USER EVENT -->
				<c:if test="${user.id!=e.user.id}">	
				<c:forEach items="${e.attendees}" var= "att">
					<c:if test="${user.id!=att.id}">
						<td><a class="btn btn-outline-success" href="/events/${e.id}/join">Join</a></td>
					</c:if>
					<c:if test="${user.id==att.id}">
						<td><a class="btn btn-outline-danger" href="/events/${e.id}/cancel">Cancel</a><td>
					</c:if>
					
					
				</c:forEach>
					<%-- <td>
				<!-- join event -->
						<a class="btn btn-outline-success" href="/events/${e.id}/join">Join</a>				
				<!-- cancel event -->
						<a class="btn btn-outline-danger" href="/events/${e.id}/cancel">Cancel</a>
					</td>   --%>
				
				</c:if>
			
			</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>	
	<!-- 	new event -->
	<div class= "card">
	<h1>Create Event</h1>
		
		<form:form action="/events" method="post" modelAttribute="event">
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
	       	
	       	<input type="submit" value="Create"/>
		</form:form> 
	</div>
	</div>
</body>
</html>