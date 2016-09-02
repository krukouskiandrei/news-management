<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="filter">
	<form:form action="filternews" method="POST" modelAttribute="searchParameter">
		<form:select path="author.idAuthor">
			<form:option value="0" label="Select Author"/>
			<form:options items="${listAuthors}" itemValue="idAuthor" itemLabel="authorName"/>
		</form:select>
		<form:select path="tagList[0].idTag">
			<form:option value="0" label="Select Tag"/>
			<form:options items="${listTags}" itemValue="idTag" itemLabel="tagName"/>
		</form:select>
		<input type="submit" value="Filter">
		<input type="reset" value="Reset">
	</form:form>
</div>
<div class="list_news">
<c:forEach items="${listNewsInfo}" var="newsInfo">
	<div class="news">
		<h3>
			<a href="<c:url value="/news/${newsInfo.news.idNews}" />">
			<c:out value="${newsInfo.news.shortText}"/></a>
		</h3>
		<span class="author_date">
			<span>(by <c:out value="${newsInfo.author.authorName}"/>)</span>
			<span><c:out value="${newsInfo.news.date}"/>
		</span>
		</span>
		<p><c:out value="${newsInfo.news.fullText}"/></p>
		<div class="comment_tag_news">
			<span class="tag">
			<c:forEach items="${newsInfo.tags}" var="tag">
				<c:out value="${tag.tagName}"/> 
			</c:forEach>
			</span>
			<c:set var="count" value="${newsInfo.comments}"/>
			<span class="comment">Comments(<c:out value="${fn:length(count)}"/>)</span>
			<span><a href="#">Edit</a></span>
			<input type="checkbox"/>
		</div>
	</div>
</c:forEach>
</div>

