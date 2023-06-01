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
	            <th>Phone</th>
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
	    <c:if test="${currentPage > 1}">
	    	<a href="?page=${currentPage - 1}">Previous</a>
		</c:if>
		
		<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
			<c:choose>
				<c:when test="${i == currentPage}">
					<b>${i}</b>
				</c:when>
				<c:otherwise>
					<a href="?page=${i}">${i}</a>
				</c:otherwise>
			</c:choose>
        </c:forEach>
		
		<c:if test="${currentPage < totalPages}">
	    	<a href="?page=${currentPage + 1}">Next</a>
		</c:if>

	    <br>
	    <form action="/api/employee" method="POST" enctype="multipart/form-data">
	        <label for="file">Upload File : </label>
	        <input type="file" name="file" id="file">
	        <button type="submit">등록</button>
   	 	</form>
	</body>
</html>