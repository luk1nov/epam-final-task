<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
  <title>Return request</title>
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
    <c:import url="components/header.jsp"/>
    <div class="app-content">
      <div class="content-wrapper">
        <div class="container-fluid">
          <div class="row">
            <div class="col">
              <div class="page-description">
                <h1>
                  Order report
                </h1>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6 mx-auto">
              <div class="card">
                <div class="card-body">
                  <div class="example-content">
                    <form class="row g-3" action="controller" method="post" enctype="multipart/form-data">
                      <div class="col-12">
                        <label for="inputOrderReportMessage" class="form-label">Description</label>
                        <textarea rows="3" id="inputOrderReportMessage" name="orderReportMessage" class="form-control"></textarea>
                      </div>
                      <div class="col-12">
                        <label for="inputOrderReportStatus" class="form-label">Status</label>
                        <select name="orderReportStatus" id="inputOrderReportStatus" class="form-select">
                          <option value="WITHOUT_DEFECTS" selected>Without defects</option>
                          <option value="VISIBLE_DEFECT">Visual defect</option>
                          <option value="TECHNICAL_DEFECT">Technical defect</option>
                        </select>
                      </div>
                      <div class="col-12">
                        <label for="formFile" class="form-label">Photo</label>
                        <input name="orderReportPhoto" type="file" class="form-control" id="formFile" required>
                      </div>
                      <input type="hidden" name="orderId" value="${orderId}">
                      <div class="col-12">
                          <input type="hidden" name="command" value="admin_add_new_user">
                          <input type="submit" class="btn btn-primary" value="Return">
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
</body>
</html>