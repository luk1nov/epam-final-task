<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page not found</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="app app-error align-content-stretch d-flex flex-wrap">
    <div class="app-error-info">
        <h5>Oops!</h5>
        <span>It seems that the page you are looking for no longer exists.<br>
                We will try our best to fix this soon.</span>
        <a href="/index.jsp" class="btn btn-dark">Go to main page</a>
    </div>
    <div class="app-error-background"></div>
</div>
</body>
</html>
