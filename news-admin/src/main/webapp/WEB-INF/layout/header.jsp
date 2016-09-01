<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header_title">
    <h1><spring:message code="page.header.main.title"/></h1>
</div>
<div class="header_info">
    <div class="header_username">
        <h4><spring:message code="page.header.title.hello"/><sec:authentication property="principal.username"/></h4>
    </div>

    <div class="header_logout">
        <form:form action="${pageContext.request.contextPath}/logout" method="POST">
            <button type="submit"><spring:message code="page.header.logout.title"/></button>
        </form:form>
    </div>
    <div class="lang">
        <a href="?lang=en"><spring:message code="page.header.lang.en"/></a>
        <a href="?lang=ru"><spring:message code="page.header.lang.ru"/></a>
    </div>
</div>