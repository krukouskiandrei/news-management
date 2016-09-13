<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="filter">
	<form:form action="${pageContext.request.contextPath}/filternews" method="POST" modelAttribute="searchParameter">
		<form:select path="author.idAuthor">
			<form:options items="${listAuthors}" itemValue="idAuthor" itemLabel="authorName"/>
		</form:select>
		<form:select path="tagList[0].idTag">
			<form:options items="${listTags}" itemValue="idTag" itemLabel="tagName"/>
		</form:select>
		<input type="submit" value="Filter">
		<input type="reset" value="Reset">
	</form:form>
	
</div>

<div class="list_news">
	<c:if test="${empty listNewsInfo}">
		<h1>Not newses</h1>
	</c:if>
	<form:form action="${pageContext.request.contextPath}/delete" method="POST">
		<c:forEach items="${listNewsInfo}" var="newsInfo">
			<div class="news">
				<h3>
					<a href="<c:url value="/news/${newsInfo.news.idNews}" />">
						<c:out value="${newsInfo.news.shortText}"/>
					</a>
				</h3>
				<span class="author_date">
					<span>(by <c:out value="${newsInfo.author.authorName}"/>)</span>
					<span><c:out value="${newsInfo.news.date}"/></span>
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
					<span>
						<a href="/news/edit/${newsInfo.news.idNews}">Edit</a>
					</span>
					<input type="checkbox" name="deleteNewsId" value="${newsInfo.news.idNews}"/>
				</div>
			</div>
		</c:forEach>
		<c:if test="${not empty listNewsInfo}">
			<div class="delete-button">
				<button type="submit">DELETE</button>
			</div>
		</c:if>
		<div id="page-buttons"></div>
	</form:form>
</div>




<%-- <div class="list_news">
<c:if test="${empty listNewsInfo}">
	<h1>Not newses</h1>
</c:if>
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
			<input type="checkbox" name="deleteNewsId" value="${newsInfo.news.idNews}"/>
		</div>
	</div>	
</c:forEach>
<c:if test="${not empty listNewsInfo}">
	<div class="delete-button">
		<form:form action="delete" method="POST">
			<button type="submit">DELETE</button>
		</form:form>
	</div>
</c:if>
<div id="page-buttons">
  		
</div>
</div>
 --%>
<script src="${pageContext.request.contextPath}/resources/script/jquery-3.1.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/script/createChangePageButtons.js" type="text/javascript"></script>
<script>
		$(document).ready(createPageButtons(${countNews}, ${pageNum}, "${pageContext.request.contextPath}"));
</script>
