<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des billets disponibles</title>
</head>
<body>
<!-- NE PAS MODIFIER -->
	<h1>Liste des billets au départ de ${planeticket.orig}</h1>
	<ul>
		<c:forEach var="d" items="${planeticket.dests}">
			<li>${d.name} : ${d.price} euros  (${d.qtity})</li>
		</c:forEach>
	</ul>
</body>
</html>