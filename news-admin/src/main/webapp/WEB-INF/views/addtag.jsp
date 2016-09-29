<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="${contextPath}/resources/script/jquery-3.1.0.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/script/tag.js" type="text/javascript"></script>
<div class="tags-list">
	<spring:form modelAttribute="tag">
		<spring:errors path="tagName" cssClass="error-msg"/>
	</spring:form>
	
	<c:forEach items="${tagsList}" var="tag">
        <div class="tag">
            <spring:form id="form${tag.idTag}" modelAttribute="tag" action="${contextPath}/addtag/updatetag" method="POST">
                        <spring:hidden  name="idTag" path="idTag" value="${tag.idTag}"/>
                        <input type="hidden" id="deleteResult${tag.idTag}" name="deleteTag" value="no"/>
                        <p>Tag: 
                        	<spring:input id="${tag.idTag}" name="tagName"
                                             path="tagName" readonly="true" value="${tag.tagName}"/>
                        </p>
                    </spring:form>
                </div>
                <div>
                    <button id="editBut${tag.idTag}"
                            class="edit-button" onclick="showHiddenButtons(${tag.idTag})">edit
                    </button>
                    <button id="updateBut${tag.idTag}"
                            class="edit-button" style="display: none"
                            onclick="updateTag(${tag.idTag})">update
                    </button>
                    <button id="deleteBut${tag.idTag}"
                            class="delete-button" style="display: none"
                            onclick="deleteTag(${tag.idTag})">delete
                    </button>
                    <button id="cancelBut${tag.idTag}"
                            class="edit-button" style="display: none"
                            onclick="hideButtons(${tag.idTag})">cancel
                    </button>
                </div>
            
    </c:forEach>    
</div>

<div>
    <spring:form id="addform" action="${contextPath}/addtag/createtag" modelAttribute="tag" method="POST">

        <p>Add Tag: <spring:input name="tagName" path="tagName"/>
        	<button onclick="addTag()">save</button></p>
    </spring:form>


</div>