<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="addnewsform">
	<form:form action="${contextPath}/createNews" method="POST" modelAttribute="newsInfo" acceptCharset="UTF-8">
		<div>
			<form:label path="news.title">Title:</form:label>
			<form:input path="news.title"/>
			<form:errors path="news.title"></form:errors>
		</div>
		<div>
			<%-- <form:label path="news.modificationDate">Date:</form:label>
			<form:input path="news.modificationDate"/> --%>
			<span>Date:</span>
			<c:out value="${currentDate}" />
		</div>
		<div>
			<form:label path="news.shortText">Brief:</form:label>
			<form:textarea path="news.shortText"/>
			<form:errors path="news.shortText"></form:errors>
		</div>
		<div>
			<form:label path="news.fullText">Content:</form:label>
			<form:textarea path="news.fullText"/>
			<form:errors path="news.fullText"></form:errors>
		</div>
		<form:select path="author.idAuthor">
			<form:options items="${listAuthors}" itemValue="idAuthor" itemLabel="authorName"/>
		</form:select>
		<form:errors path="author.idAuthor"></form:errors>
		<form:select path="tags[0].idTag">
			<form:options items="${listTags}" itemValue="idTag" itemLabel="tagName"/>
		</form:select>
		<form:errors path="tags[0].idTag"></form:errors>
		<input type="submit" value="Save">
	</form:form>
</div>