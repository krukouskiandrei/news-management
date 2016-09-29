<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="${contextPath}/resources/script/jquery-3.1.0.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/script/tag.js" type="text/javascript"></script>
<div class="tags">
	<div class="tags-list">
		<springform:form modelAttribute="tag">
			<springform:errors path="tagName" cssClass="error"/>
		</springform:form>
		<c:forEach items="${tagsList}" var="tag">
    		<div class="tag">
        		<springform:form id="form${tag.idTag}" modelAttribute="tag" action="${contextPath}/addtag/updatetag" method="POST">
            		<springform:hidden  name="idTag" path="idTag" value="${tag.idTag}"/>
                	<input type="hidden" id="deleteResult${tag.idTag}" name="deleteTag" value="no"/>
                	<p><spring:message code="addtag.name"/> 
                		<springform:input id="${tag.idTag}" name="tagName"
                                      path="tagName" readonly="true" value="${tag.tagName}"/>
             		</p>
         		</springform:form>
     		</div>
        	<div class="tagsBut">
        		<button id="editBut${tag.idTag}"
                        class="edit-button" onclick="showHiddenButtons(${tag.idTag})"><spring:message code="addtag.button.edit"/>
            	</button>
            	<button id="updateBut${tag.idTag}"
                        class="edit-button" style="display: none"
                         onclick="updateTag(${tag.idTag})"><spring:message code="addtag.button.update"/>
            	</button>
            	<button id="deleteBut${tag.idTag}"
                       class="del-button" style="display: none"
                        onclick="deleteTag(${tag.idTag})"><spring:message code="addtag.button.delete"/>
            	</button>
            	<button id="cancelBut${tag.idTag}"
                       class="edit-button" style="display: none"
                         onclick="hideButtons(${tag.idTag})"><spring:message code="addtag.button.cancel"/>
            	</button>
      		</div>
   		</c:forEach>    
	</div>

	<div class="addtag">
    	<springform:form id="addform" action="${contextPath}/addtag/createtag" modelAttribute="tag" method="POST">
	        <p><spring:message code="addtag.addtag.title"/> <springform:input name="tagName" path="tagName"/></p>
    	</springform:form>
    	<button onclick="addTag()"><spring:message code="addtag.save"/></button>
	</div>
</div>