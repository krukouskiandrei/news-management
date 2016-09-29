<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="show_news">
	<h2><c:out value="${newsInfo.news.shortText}"></c:out></h2>
	<div>
		<span class="show_author">(<spring:message code="news.author.by"/> <c:out value="${newsInfo.author.authorName}"/>)</span>
		<span class="show_date"><c:out value="${newsInfo.news.date}"/></span>
	</div>
	<p class="fulltext"><c:out value="${newsInfo.news.fullText}"/></p>
	<div class="comments">
		<c:forEach items="${newsInfo.comments}" var="comment">
			<div class="comment">
				<p class="create_date">
					<c:out value="${comment.date}"/>
				</p>
				<div class="text_comment">
					<p>
						<c:out value="${comment.commentText}"/>
					</p>
					<a href="<c:url value="/news/${comment.idNews}/${comment.idComment}" />">
						<img src="${contextPath}/resources/style/image/cross.png"/>
					</a>
				</div>
				
			</div>
		</c:forEach>
	</div>
	<div class="crete_comment">
		<springform:form action="${contextPath}/news/${newsInfo.news.idNews}/createcomment" method="POST" modelAttribute="comment">
			<div>
				<springform:input path="commentText"/>
				<p>
					<springform:errors path="commentText" cssClass="error"/>
				</p>
			</div>
			<input type="submit" value="<spring:message code="shownews.comment.post"/>">
		</springform:form>
	</div>
	<div class="paginationlink">
		<a href="<c:url value="/news/previous" />"><spring:message code="shownews.previous.page"/></a>
		<a href="<c:url value="/news/next" />"><spring:message code="shownews.next.page"/></a>
	</div>
</div>