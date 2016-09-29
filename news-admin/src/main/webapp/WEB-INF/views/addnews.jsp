<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="addnewsform">
	<springform:form action="${contextPath}/news/createNews" method="POST" modelAttribute="newsInfo">
		<div>
			<springform:label path="news.title"><spring:message code="addnews.title"/></springform:label>
			<springform:input path="news.title"/>			
		</div>
		<div>
			<span><spring:message code="addnews.date"/></span>
			<c:out value="${currentDate}" />
		</div>
		<div>
			<springform:label path="news.shortText"><spring:message code="addnews.shorttext"/></springform:label>
			<springform:textarea path="news.shortText"/>
			
		</div>
		<div>
			<springform:label path="news.fullText"><spring:message code="addnews.fulltext"/></springform:label>
			<springform:textarea path="news.fullText"/>
			
		</div>
		<div>
			<springform:select path="author.idAuthor">
				<springform:options items="${listAuthors}" itemValue="idAuthor" itemLabel="authorName"/>
			</springform:select>
			<springform:select path="tags[0].idTag">
				<springform:options items="${listTags}" itemValue="idTag" itemLabel="tagName"/>
			</springform:select>			
		</div>
		<div>
			<springform:errors path="news.title" cssClass="error"/>
			<springform:errors path="news.shortText" cssClass="error"/>
			<springform:errors path="news.fullText" cssClass="error"/>
			<springform:errors path="author.idAuthor" cssClass="error"/>
			<springform:errors path="tags[0].idTag" cssClass="error" />
		</div>
		<div>
			<input type="submit" value="Save">
	    </div>
	</springform:form>
</div>