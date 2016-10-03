<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="filter">
	<form method="POST">
		<select name="authorId">
			<c:forEach items="${listAuthors}" var="author">
				<option value="${author.idAuthor}"><c:out value="${author.authorName}"/></option>
			</c:forEach>
		</select>
		<select name="tagsId" multiple size="10">
			<option value="0" selected="selected">...</option>
			<c:forEach items="${listTags}" var="tag">
				<option value="${tag.idTag}"><c:out value="${tag.tagName}"/></option>
			</c:forEach>
		</select>
		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		<button formaction="${contextPath}/news/filternews">Filter</button>
		<button formaction="${contextPath}/news">Reset</button>
	</form>
</div>

<div class="list_news">
	<c:if test="${empty listNewsInfo}">
		<h1><spring:message code="news.empty"/></h1>
	</c:if>
	<form:form action="${contextPath}/delete" method="POST">
		<c:forEach items="${listNewsInfo}" var="newsInfo">
			<div class="news">
				<h3>
					<a href="<c:url value="/news/${newsInfo.news.idNews}" />">
						<c:out value="${newsInfo.news.title}"/>
					</a>
				</h3>
				<span class="author_date">
					<span>(<spring:message code="news.author.by"/> <c:out value="${newsInfo.author.authorName}"/>)</span>
					<span><c:out value="${newsInfo.news.date}"/></span>
				</span>
				<p><c:out value="${newsInfo.news.shortText}"/></p>
				<div class="comment_tag_news">
					<span class="tag">
						<c:forEach items="${newsInfo.tags}" var="tag">
							<c:out value="${tag.tagName}"/> 
						</c:forEach>
					</span>
					<c:set var="count" value="${newsInfo.comments}"/>
					<span class="comment"><spring:message code="news.comments.count"/>(<c:out value="${fn:length(count)}"/>)</span>
					<span>
						<a href="/news/edit/${newsInfo.news.idNews}"><spring:message code="news.link.edit"/></a>
					</span>
					<input type="checkbox" name="deleteNewsId" value="${newsInfo.news.idNews}"/>
				</div>
			</div>
		</c:forEach>
		<c:if test="${not empty listNewsInfo}">
			<div class="delete-button">
				<button type="submit"><spring:message code="news.button.delete"/></button>
			</div>
		</c:if>
		<div id="page-buttons"></div>
	</form:form>
</div>

<script src="${contextPath}/resources/script/jquery-3.1.0.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/script/createChangePageButtons.js" type="text/javascript"></script>
<script>
		$(document).ready(createPageButtons(${countNews}, ${pageNum}, "${pageContext.request.contextPath}"));
</script>
