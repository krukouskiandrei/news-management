<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="${contextPath}/resources/script/jquery-3.1.0.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/script/author.js" type="text/javascript"></script>
<div class="authors-list">
	<spring:form modelAttribute="author">
		<spring:errors path="authorName" cssClass="error-msg"/>
	</spring:form>
	
	<c:forEach items="${authorsList}" var="author">
        <div class="author">
            <spring:form id="form${author.idAuthor}" modelAttribute="author" action="${contextPath}/addauthor/updateAuthor" method="POST">
                        <spring:hidden  name="idAuthor" path="idAuthor" value="${author.idAuthor}"/>
                        <spring:hidden name="expiredDate" id="exp${author.idAuthor}" path="expiredDate" value="${author.expiredDate}" />

                        <p>Author: 
                        	<spring:input id="${author.idAuthor}" name="authorName"
                                             path="authorName" readonly="true" value="${author.authorName}"/>
                        </p>
                    </spring:form>
                </div>
                <div>
                    <button id="editBut${author.idAuthor}"
                            class="edit-button" onclick="showHiddenButtons(${author.idAuthor})">edit
                    </button>
                    <button id="updateBut${author.idAuthor}"
                            class="edit-button" style="display: none"
                            onclick="updateAuthor(${author.idAuthor})">update
                    </button>
                    <button id="expireBut${author.idAuthor}"
                            class="edit-button" style="display: none"
                            onclick="expireAuthor(${author.idAuthor})">expire
                    </button>
                    <button id="cancelBut${author.idAuthor}"
                            class="edit-button" style="display: none"
                            onclick="hideButtons(${author.idAuthor})">cancel
                    </button>
                </div>
            
    </c:forEach>    
</div>

<div>
    <spring:form id="addform" action="${contextPath}/addauthor/createauthor" modelAttribute="author" method="POST">

        <p>Add Author: <spring:input name="authorName" path="authorName"/>
        	<button onclick="addAuthor()">save</button></p>
    </spring:form>


</div>