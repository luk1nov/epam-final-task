<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order report</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/highlight/styles/github-gist.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/select2/css/select2.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="app align-content-stretch d-flex flex-wrap">
    <%@include file="../admin-sidebar.jsp"%>
    <div class="app-container">
        <%@include file="../admin-header.jsp"%>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>Report for order #<c:out value="${orderId}"/></h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <img src="data:image/jpg;base64,<c:out value="${report.photo}"/>" class="card-img-top">
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${report.reportStatus eq 'WITHOUT_DEFECTS'}">
                                            <div class="alert alert-custom alert-success" role="alert">
                                                <div class="custom-alert-icon icon-success"><i class="material-icons-outlined">done</i></div>
                                                <div class="alert-content">
                                                    <span class="alert-title">Without defects</span>
                                                    <c:if test="${report.reportText.isPresent()}">
                                                        <span class="alert-text"><c:out value="${report.reportText.get()}"/></span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test="${report.reportStatus eq 'VISIBLE_DEFECT'}">
                                            <div class="alert alert-custom alert-danger" role="alert">
                                                <div class="custom-alert-icon icon-danger"><i class="material-icons-outlined">warning</i></div>
                                                <div class="alert-content">
                                                    <span class="alert-title">Visible defect</span>
                                                    <c:if test="${report.reportText.isPresent()}">
                                                        <span class="alert-text"><c:out value="${report.reportText.get()}"/></span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test="${report.reportStatus eq 'TECHNICAL_DEFECT'}">
                                            <div class="alert alert-custom alert-danger" role="alert">
                                                <div class="custom-alert-icon icon-danger"><i class="material-icons-outlined">warning</i></div>
                                                <div class="alert-content">
                                                    <span class="alert-title">Technical defect</span>
                                                    <c:if test="${report.reportText.isPresent()}">
                                                        <span class="alert-text"><c:out value="${report.reportText.get()}"/></span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-custom alert-primary" role="alert">
                                                <div class="custom-alert-icon icon-primary"><i class="material-icons-outlined">done</i></div>
                                                <div class="alert-content">
                                                    <span class="alert-title"><c:out value="${report.reportStatus}"/></span>
                                                    <c:if test="${report.reportText.isPresent()}">
                                                        <span class="alert-text"><c:out value="${report.reportText.get()}"/></span>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Javascripts -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/select2/js/select2.full.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/highlight/highlight.pack.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script>
    document.querySelector('#inputBrand option[value="${car.brand}"]').setAttribute('selected', 'selected');
    $('#inputBrand').select2();
</script>
</body>
</html>
