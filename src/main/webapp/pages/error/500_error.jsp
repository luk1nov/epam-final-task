<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/neptune.png" />
</head>
<body>
<div class="app app-error align-content-stretch d-flex flex-wrap">
    <div class="app-error-info">
        <h5>Oops!</h5>
        <div class="card error-card">
            <span class="error-stacktrace">
                Request from ${pageContext.errorData.requestURI} is failed<br/>
                Status code : ${pageContext.errorData.statusCode}<br/>
                Servlet name : ${pageContext.errorData.servletName}<br/>
                Exception: ${pageContext.exception} Message:${error}<br/>
                <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
                    ${trace}<br/>
                </c:forEach>
            </span>
        </div>
        <a href="/index.jsp" class="btn btn-dark">Go to main page</a>
    </div>
    <div class="app-error-background"></div>
</div>
</body>
</html>
