<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<html>
<head>
<<<<<<< HEAD
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/style/tiles.css">
=======
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/tiles.css">
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid wrapper">
    <div id="header" class="row">
        <t:insertAttribute name="header"/>
    </div>
    <div id="content" class="row logincontent">
        <t:insertAttribute name="body"/>
    </div>
    <div id="footer" class="row footer">
        <t:insertAttribute name="footer"/>
    </div>
</div>
</body>
</html>
