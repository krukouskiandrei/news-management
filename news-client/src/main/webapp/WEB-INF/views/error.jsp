<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error page</title>
</head>
<body>
	<h1>Sorry, error occurred</h1>
	<h3><a href="<c:url value="NewsServlet">
		<c:param name="command">show_news</c:param>
		<c:param name="page">1</c:param></c:url>">Main page</a></h3>
</body>
</html>