<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<c:choose>
    <c:when test="${fn:contains(message, 'fail')}">
        <div class="alert alert-danger alert-style-light" role="alert">
            <fmt:message key="${fn:escapeXml(message)}"/>.
        </div>
    </c:when>
    <c:when test="${fn:contains(message, 'success')}">
        <div class="alert alert-success alert-style-light" role="alert">
            <fmt:message key="${fn:escapeXml(message)}"/>.
        </div>
    </c:when>
</c:choose>
