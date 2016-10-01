<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h1><c:choose>
	<c:when test="${filter==null}">
		<a href=<c:url value="NewsServlet">
		<c:param name="command" value="show_news"/>
		<c:param name="page" value="${page}"/>
		</c:url>>Back</a>	
	</c:when>
	<c:otherwise>
		<a href=<c:url value="NewsServlet">
		<c:param name="command" value="filter_news"/>
		<c:param name="page" value="${page}"/>
		<c:param name="author dropdown" value="${filter.getAuthorId()}"/>
		<c:param name="tagList" value="${filter.getTags()}"/>
		<c:param name="filtered" value="true"/>
		</c:url>>Back</a>
	</c:otherwise>
	</c:choose></h1>
	<h1><c:out  value="${msg.getNews().getTitle()}"></c:out></h1>
	<h3 align="right"><c:out value="(by ${msg.getAuthor().getAuthorName()}) ${msg.getNews().getModificationDate()}">
	</c:out></h3>
	<p><c:out  value="${msg.getNews().getFullText()}"></c:out></p>
	<br>
	<ul>
		<c:forEach items="${msg.getComments()}" var="comment">
			<li>
				<c:out value="${comment.getCreationDate()}"></c:out>
				<c:out value="${comment.getCommentText()}"></c:out>
			</li>
		</c:forEach>
	</ul>
	<form action="NewsServlet" method="post">
		<textarea rows="4" cols="60" name="comment" maxlength="100" required></textarea>
		<input type="hidden" name="command" value="create_comment">
		<input type="hidden" name="page" value="${page}">
		<input type="hidden" name="newsId" value="${msg.getNews().getNewsId()}">
		<input type="submit" value="Post comment">
		<c:if test="${filter!=null}">
			<input type="hidden" name="authorId" value="${filter.getAuthorId()}"/>
			<input type="hidden" name="tagList" value="${filter.getTags()}"/>
		</c:if>
	</form>
	<form action="NewsServlet" method="post" name="form1" style="position:relative; padding-top:70px;">
		<input type="hidden" name="command" value="show_message">
		<input type="hidden" name="page" value="${page}">
		<input type="hidden" name="newsId" id="id" value="${msg.getNews().getNewsId()}">
		<c:if test="${previous!=null}">
			<a href="#" onClick="javascript:submitForm(true)" style="font-size:large"><b>Previous</b></a>
		</c:if>
		<c:if test="${next!=null}">
			<a href="#" onClick="javascript:submitForm(false)" style="float: right; font-size:large"><b>Next</b></a>
		</c:if>
		<c:if test="${filter!=null}">
			<input type="hidden" name="authorId" value="${filter.getAuthorId()}"/>
			<input type="hidden" name="tagList" value="${filter.getTags()}"/>
		</c:if>
	</form>
	<script>
		function submitForm(value) {
			if(value == true){
				document.form1.newsId.value = "${previous}";
				document.form1.submit();
			}else{
				document.form1.newsId.value = "${next}";
				document.form1.submit();
			}
		}
	</script>
</body>
</html>