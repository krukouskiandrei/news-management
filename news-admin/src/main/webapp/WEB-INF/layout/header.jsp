<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-6 text-center">
    <h1>News portal - Administration</h1>
</div>
<div class="col-md-6 logout">
    <div class="col-sm-10 text-center">
        <h4>Hello, Admin <sec:authentication property="principal.username"/></h4>
    </div>

    <div class="col-sm-2 text-right">
        <form:form action="${pageContext.request.contextPath}/logout" method="POST">
            <button class="btn btn-danger" type="submit">Logout</button>
        </form:form>
    </div>
    <div class="col-sm-offset-8 col-sm-4 text-right">
        <a href="?lang=en" class="btn btn-warning">en</a>
        <a href="?lang=ru" class="btn btn-success">ru</a>
    </div>
</div>

<%--
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table style="width: 100%">
    <tr>
        <td align="left">
            <h3>
                News admin
            </h3>
        </td>
        <td align="right">Login:
            <sec:authentication property="principal.username"/>
        </td>
        <br/>
        <td>
            <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                <button class="btn btn-sm" type="submit">
                    Logout
                </button>
            </form:form>
        </td>
        <td>
            <a href="?lang=en">en</a> <a href="?lang=ru">ru</a>
        </td>
    </tr>
</table>
<hr/>--%>
