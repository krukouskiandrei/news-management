<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/j_spring_security_check" var="loginUrl"/>
<<<<<<< HEAD
<link href="${pageContext.request.contextPath}/resources/style/error.css" rel="stylesheet" type="text/css"/>
=======
<link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css"/>
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
<div class="col-md-offset-4 col-md-4">
    <form name="loginForm" action="${loginUrl}" method="POST" class="form-horizontal">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-group">
            <label for="inputLogin" class="col-sm-2 control-label">Login</label>
            <div class="col-sm-10">
                <input type="text" name="login" value="" id="inputLogin" class="form-control" placeholder="Login">
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password">
            </div>
        </div>
        <div class="error-msg">
            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                <h1>Eroror Security</h1>
            </c:if>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Login</button>
            </div>
        </div>
    </form>
<<<<<<< HEAD
=======

    <%--<form name="loginForm" action="${loginUrl}" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div>
            <label>User:</label>
            <input type="text" name="login" value="">
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password">
        </div>
        <button type="submit">Login</button>
    </form>
    <div class="error-msg">
        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
            <h1>Eroror Security</h1>
        </c:if>
    </div>--%>
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
</div>