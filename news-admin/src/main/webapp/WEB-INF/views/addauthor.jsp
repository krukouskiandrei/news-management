<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="${contextPath}/resources/script/jquery-3.1.0.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/script/author.js" type="text/javascript"></script>
<div class="authors">
	<div class="authors-list">
		<springform:form modelAttribute="author">
			<springform:errors path="authorName" cssClass="error"/>
		</springform:form>
	
		<c:forEach items="${authorsList}" var="author">
        	<div class="author">
            	<springform:form id="form${author.idAuthor}" modelAttribute="author" action="${contextPath}/addauthor/updateAuthor" method="POST">
                	<springform:hidden  name="idAuthor" path="idAuthor" value="${author.idAuthor}"/>
                    <springform:hidden name="expiredDate" id="exp${author.idAuthor}" path="expiredDate" value="${author.expiredDate}" />
                    <p><spring:message code="addauthor.name"/> 
                    	<springform:input id="${author.idAuthor}" name="authorName"
                                             path="authorName" readonly="true" value="${author.authorName}"/>
                    </p>
                </springform:form>
          	</div>
            <div class="authorsBut">
            	<button id="editBut${author.idAuthor}"
                            class="edit-button" onclick="showHiddenButtons(${author.idAuthor})"><spring:message code="addauthor.button.edit"/>
                </button>
                <button id="updateBut${author.idAuthor}"
                            class="edit-button" style="display: none"
                            onclick="updateAuthor(${author.idAuthor})"><spring:message code="addauthor.button.update"/>
                </button>
                <button id="expireBut${author.idAuthor}"
                            class="edit-button" style="display: none"
                            onclick="expireAuthor(${author.idAuthor})"><spring:message code="addauthor.button.expire"/>
                </button>
                <button id="cancelBut${author.idAuthor}"
                            class="edit-button" style="display: none"
                            onclick="hideButtons(${author.idAuthor})"><spring:message code="addauthor.button.cancel"/>
                </button>
        	</div>
       </c:forEach>    
	</div>

	<div class="addauthor">
    	<springform:form id="addform" action="${contextPath}/addauthor/createauthor" modelAttribute="author" method="POST">
	        <p><spring:message code="addauthor.addauthor.title"/> 
	        	<springform:input name="authorName" path="authorName"/>
    	    </p>
    	    <button onclick="addAuthor()"><spring:message code="addauthor.save"/></button>
    	</springform:form>
	</div>
</div>