<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="addnewsform">
	<springform:form action="${contextPath}/news/updateNews" method="POST" modelAttribute="editNews">
		<div>
			<springform:label path="title"><spring:message code="addnews.title"/></springform:label>
			<springform:input path="title"/>			
		</div>
		<div>
			<span><spring:message code="addnews.date"/></span>
			<c:out value="${editNews.date}" />
		</div>
		<div>
			<springform:label path="shortText"><spring:message code="addnews.shorttext"/></springform:label>
			<springform:textarea path="shortText"/>
			
		</div>
		<div>
			<springform:label path="fullText"><spring:message code="addnews.fulltext"/></springform:label>
			<springform:textarea path="fullText"/>
			
		</div>
		<div>
			<springform:errors path="title" cssClass="error"/>
			<springform:errors path="shortText" cssClass="error"/>
			<springform:errors path="fullText" cssClass="error"/>
		</div>
		<div>
			<input type="submit" value="Save">
	    </div>
	</springform:form>
</div>