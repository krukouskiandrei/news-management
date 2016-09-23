<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="show_news">
	<h2><c:out value="${newsInfo.news.title}"></c:out></h2>
	<div>
		<span>(by <c:out value="${newsInfo.author.authorName}"/>)</span>
		<span><c:out value="${newsInfo.news.date}"/></span>
	</div>
	<p><c:out value="${newsInfo.news.fullText}"/></p>
	<div class="comments">
		<c:forEach items="${newsInfo.comments}" var="comment">
			<div class="comment">
				<p class="create_date">
					<c:out value="${comment.date}"/>
				</p>
				<p class="text_comment">
					<c:out value="${comment.commentText}"/>
				</p>
				<a href="<c:url value="/news/${comment.idNews}/${comment.idComment}" />">Delete Comment</a>
			</div>
		</c:forEach>
	</div>
	<div class="crete_comment">
		<form:form action="${contextPath}/${newsInfo.news.idNews}/createcomment" method="POST" modelAttribute="comment">
			<div>
				<form:input path="commentText"/>
				<form:errors path="commentText"/>
			</div>
			<input type="submit" value="Post Comment">
		</form:form>
	</div>
	<div>
		<a href="<c:url value="/news/next" />">Next</a>
		<a href="<c:url value="/news/previous" />">Previous</a>
	</div>
</div>