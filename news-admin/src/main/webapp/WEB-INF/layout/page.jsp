<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta charset="UTF-8" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/style.css">
</head>
<body>
<div class="wrapper">
    <div id="header" class="header">
        <t:insertAttribute name="header"/>
    </div>
    <div id="content" class="content">
    	<t:insertAttribute name="sitebar"/>
        <t:insertAttribute name="body"/>
    </div>
    <div id="footer" class="footer">
        <t:insertAttribute name="footer"/>
    </div>
    
</div>
</body>
</html>