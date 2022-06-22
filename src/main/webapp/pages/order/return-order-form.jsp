<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.finish_rent"/></title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
</head>
<body>

<div class="app full-width-header align-content-stretch d-flex flex-wrap">
    <div class="app-container" style="width: 100vw; margin-right: 280px;">
        <c:import url="../components/header.jsp"/>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1><fmt:message key="label.finish_rent"/></h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="${pageContext.request.contextPath}/pages/components/message.jsp"/>
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <div class="example-content">
                                        <form class="row g-3 needs-validation" action="/controller" method="POST" enctype="multipart/form-data" novalidate oninput='reportText.setCustomValidity(!/^[\s\wА-яЁё.,!?]{0,200}$/.test(reportText.value) ? "invalid message" : "")'>
                                            <div class="col-12">
                                                <label for="inputReportStatus" class="form-label"><fmt:message key="label.defects"/></label>
                                                <select name="reportStatus" class="form-control" id="inputReportStatus" required>
                                                    <option value="VISIBLE_DEFECT"><fmt:message key="label.visible_defects"/></option>
                                                    <option value="TECHNICAL_DEFECT"><fmt:message key="label.technical_defects"/></option>
                                                    <option value="WITHOUT_DEFECTS" selected><fmt:message key="label.without_defects"/></option>
                                                </select>
                                            </div>
                                            <div class="col-12">
                                                <label for="inputText" class="form-label"><fmt:message key="label.rent_desc"/></label>
                                                <textarea required maxlength="200" rows="4" name="reportText" class="form-control" id="inputText"><c:out value='${report.reportText}'/></textarea>
                                            </div>
                                            <div class="col-12">
                                                <label for="inputReportPhoto" class="form-label"><fmt:message key="label.photo"/></label>
                                                <input name="reportPhoto" type="file" class="form-control" id="inputReportPhoto" required>
                                            </div>
                                            <input type="hidden" name="command" value="finish_rent">
                                            <c:choose>
                                                <c:when test="${orderReport != null}">
                                                    <input type="hidden" name="orderId" value="<c:out value='${report.orderId}'/>">
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="hidden" name="orderId" value="<c:out value='${orderId}'/>">
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="col-12">
                                                <input type="submit" class="btn btn-primary" value="<fmt:message key="label.action_save"/>"/>
                                            </div>
                                        </form>
                                    </div>
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
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script>
    <c:if test="${report.reportStatus ne null}">
        document.querySelector('#inputReportStatus option[value="<c:out value="${report.reportStatus}"/>"]').setAttribute('selected', 'selected');
    </c:if>
</script>
</body>
</html>