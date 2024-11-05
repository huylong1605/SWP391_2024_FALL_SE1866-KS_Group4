<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container mt-5">
    <h2>Edit Application</h2>
    <!-- Button to navigate to send-application -->
    <a href="${pageContext.request.contextPath}/teacher/applications" class="btn btn-primary btn-custom">
        Back to List
    </a>
    <!-- Hiển thị thông báo nếu có -->
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/teacher/update-application" method="post">
        <input type="hidden" name="id" value="${application.applicationId}">

        <div class="form-group">
            <label for="applicationContent">Application Content</label>
            <textarea class="form-control" id="applicationContent" name="applicationContent"
                      rows="3" readonly>${application.applicationContent}</textarea>
        </div>

        <div class="form-group">
            <label for="status">Status</label>
            <select id="status" name="status" class="form-control" required>
                <option value="" disabled ${application.status == '' ? 'selected' : ''}>Select Status</option>
                <option value="approved" ${application.status == 'approved' ? 'selected' : ''}>Approved</option>
                <option value="rejected" ${application.status == 'rejected' ? 'selected' : ''}>Rejected</option>
            </select>
        </div>

        <div class="form-group">
            <label for="applicationResponse">Application Response</label>
            <textarea class="form-control" id="applicationResponse" name="applicationResponse"
                      rows="3" required>${application.applicationResponse}</textarea>
        </div>

        <button type="submit" class="btn btn-primary">Update</button>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
