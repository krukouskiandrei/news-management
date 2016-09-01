<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sitebar"> 
	<nav>
		<a href="<c:url value="/news" />"><img src="${pageContext.request.contextPath}/resources/style/image/right.png"/>
			<spring:message code="sitebar.newslist"/></a>
		<a href="<c:url value="/addnews" />"><img src="${pageContext.request.contextPath}/resources/style/image/right.png"/>
			<spring:message code="sitebar.addnews"/></a>
		<a href="<c:url value="/addauthor" />"><img src="${pageContext.request.contextPath}/resources/style/image/right.png"/>
			<spring:message code="sitebar.addauthor"/></a>
		<a href="<c:url value="/addtag" />"><img src="${pageContext.request.contextPath}/resources/style/image/right.png"/>
			<spring:message code="sitebar.addtag"/></a>
	</nav>
</div>