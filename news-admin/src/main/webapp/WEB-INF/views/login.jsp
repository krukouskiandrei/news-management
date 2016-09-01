<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:url value="/j_spring_security_check" var="loginUrl"/>
<link href="${pageContext.request.contextPath}/resources/style/error.css" rel="stylesheet" type="text/css"/>
<div class="login">
    <form name="loginForm" action="${loginUrl}" method="POST">
        <div class="main">
        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        	<div class="field">
            	<label for="inputLogin">
            		<spring:message code="login.login.title"/>
            	</label>
            	<input type="text" name="login" value="" id="inputLogin"
                	 placeholder="<spring:message code="login.login.title"/>">
            
        	</div>
        	<div class="field">
            	<label for="inputPassword">
            		<spring:message code="login.password.title"/>
            	</label>
            	<input type="password" name="password" id="inputPassword"
                	 placeholder="<spring:message code="login.password.title"/>">
            </div>
        	<div>
            	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                	<h1><spring:message code="login.security.error.title"/></h1>
            	</c:if>
        	</div>
        	<div>
            	<button type="submit"><spring:message code="login.button.login"/></button>
            </div>
        </div>
    </form>
</div>