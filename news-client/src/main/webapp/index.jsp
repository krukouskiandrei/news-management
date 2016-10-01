<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Start page</title>
</head>
<c:redirect url="NewsServlet">
	<c:param name="command">show_news</c:param>
	<c:param name="page">1</c:param>
</c:redirect>

</html>