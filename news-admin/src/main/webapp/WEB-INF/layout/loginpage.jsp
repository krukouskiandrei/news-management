<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/style.css">
</head>
<body>
<div class="wrapper">
    <div id="header" class="header">
        <t:insertAttribute name="header"/>
    </div>
    <div id="content" class="content">
        <t:insertAttribute name="body"/>
    </div>
    <div id="footer" class="footer">
        <t:insertAttribute name="footer"/>
    </div>
</div>
</body>
</html>