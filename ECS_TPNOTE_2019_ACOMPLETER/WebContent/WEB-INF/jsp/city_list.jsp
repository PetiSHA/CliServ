<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des villes contenant ${s}</title>
</head>
<body>
<h2>Liste des villes contenant ${s}</h2>
	<c:choose>
		<c:when test ="${!empty city_list}">
			<ul>
				<c:forEach var="v" items="${city_list}">
					<li> <a href="PlaneTicketServlet?cid=${v.getCid()}"> ${v.getName()} </a></li> 
				</c:forEach>
			</ul>			
		</c:when>
		<c:otherwise> Pas de ville contenant ${s} </c:otherwise>
		
	</c:choose>
</body>
</html>