<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>	
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form action="NewsServlet" method="get">
		<p align="center" >
			<select name="author dropdown">
				<c:forEach items="${requestScope.AuthorList}" var="author">
				<c:choose>
					<c:when test="${filtered && author.getAuthorId()==filter.getAuthorId()}">
						<option value="${author.getAuthorId()}" selected="selected">
						<c:out value="${author.getAuthorName()}"/>
						</option>
					</c:when>
					<c:otherwise>
						<option value="${author.getAuthorId()}"><c:out value="${author.getAuthorName()}"/></option>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</select>
			<select name="tag dropdown" onChange="addTag();" id="sl">
				<option value="0">...</option>
				<c:forEach items="${requestScope.TagList}" var="tag">
					<option value="${tag.getTagId()}" id="opt${tag.getTagId()}">
					<c:out value="${tag.getTagName()}"/></option>
				</c:forEach>
			</select>
			<input type="hidden" name="tagList" value="${filter.getTags()}" id="tagList">
			<input type="hidden" name="page" value="1">
			<button name="command" value="filter_news">Filter</button>
			<button name="command" value="show_news">Reset</button>
		</p>
	</form>
	<p align="center" id="tagOut"></p>
	<ul>
		<c:forEach items="${requestScope.NewsList}" var="news">
			<li><p><c:out value="${news.getNews().getTitle()}"/>
			<c:out value="(by ${ news.getAuthor().getAuthorName()}) ${news.getNews().getModificationDate()}"/></p>
				<p><c:out value="${news.getNews().getShortText()}"/></p>
				<p align="right"><c:forEach items="${news.getTags()}" var="tag">
				<c:out value="${tag.getTagName()}"/></c:forEach>
				<c:out value="Comments(${ news.getComments().size()})"/>
				</p>
				<form action="NewsServlet" method="post" id="${news.getNews().getNewsId()}">
					<input type="hidden" name="command" value="show_message">
					<input type="hidden" name="newsId" value="${news.getNews().getNewsId()}">
					<input type="hidden" name="page" value="${page}">
					<c:if test="${filtered}">
						<input type="hidden" name="tagList" value="${filter.getTags()}">
						<input type="hidden" name="authorId" value="${filter.getAuthorId()}">
					</c:if>
					<h3 align="right"><a href="javascript:submitForm(${news.getNews().getNewsId()});" >View</a></h3>
				</form>
			</li>
		</c:forEach>
	</ul>
	<br>
	<form action="NewsServlet">
		<c:choose>
		<c:when test="${filtered}">
			<input type="hidden" name="command" value="filter_news">
			<input type="hidden" name="tagList" value="${filter.getTags()}">
			<input type="hidden" name="author dropdown" value="${filter.getAuthorId()}">
		</c:when>
		<c:otherwise>
			<input type="hidden" name="command" value="show_news">
		</c:otherwise>
		</c:choose>
		<c:forEach items="${requestScope.pages}" var="pageNumber">
			<c:choose>
			<c:when test="${pageNumber==page}">
				<button name="page" value="${pageNumber}">
					<b><c:out value="${pageNumber}"/></b>
				</button>
			</c:when>
			<c:otherwise>
				<button name="page" value="${pageNumber}">
					<c:out value="${pageNumber}"/>
				</button>
			</c:otherwise>
			</c:choose>
		</c:forEach>
	</form>
	<script>
		var t= JSON.parse(document.getElementById("tagList").value);
		document.getElementById("tagOut").innerHTML = "Tags: ";
		for(i=0; i<t.length; i++){
			document.getElementById("tagOut").innerHTML += 
				document.getElementById("opt"+t[i]).text + " ";
		}
		Array.prototype.contains = function(elem){
			for (var i in this)			{
				if (this[i] == elem) return true;
			}
			return false;
		}
		function submitForm(id) {
	    	document.getElementById(id).submit();
		}
		function addTag(){
			var tags= JSON.parse(document.getElementById("tagList").value);
			var sl = document.getElementById("sl").value;
			if(!tags.contains(sl)){
				tags.push(document.getElementById("sl").value);
				document.getElementById("tagOut").innerHTML = "Tags: ";
				for(i=0; i<tags.length; i++){
					document.getElementById("tagOut").innerHTML += 
						document.getElementById("sl").options[tags[i]].text + " ";
				}
				document.getElementById("sl").value = 0;
				document.getElementById("tagList").value = '['+tags+']';
			}else{
				document.getElementById("sl").value = 0;
				alert("Tag already selected!");
			}
		}
	</script>
</body>
</html>