<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Employee List</title>
	</head>
	<body>
	    <h1>Employee List</h1>
	    <table>
	        <tr>
	            <th>ID</th>
	            <th>Name</th>
	            <th>Email</th>
	            <th>Tel</th>
	            <th>Joined</th>
	        </tr>
	        <c:forEach var="employee" items="${employees}" varStatus="status">
	        	<tr>
	                <td>${employee.id} </td>
	                <td>${employee.name}  </td>
	                <td>${employee.email} </td>
	                <td>${employee.tel}  </td>
	                <td>${employee.joined}  </td>
	            </tr>
	        </c:forEach>
	    </table>
	    <br>
	</body>
</html>