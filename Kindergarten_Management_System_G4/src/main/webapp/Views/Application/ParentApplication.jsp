<%--
  Created by IntelliJ IDEA.
  User: anhla
  Date: 11/1/2024
  Time: 10:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Parent Application</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTable CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <!-- Font Awesome CSS for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

<%@ include file="../common/header.jsp" %>

<div class="container mt-5">
    <h2>Submit an Application</h2>

    <a href="${pageContext.request.contextPath}/view-applications" class="btn btn-primary btn-custom">Back To List</a>
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>

    <form action="send-application" method="post" onsubmit="return validateContent()">
        <input type="hidden" name="action" value="submitApplication">

        <div class="form-group">
            <label for="title">Application Type</label>
            <select class="form-control" id="title" name="title" required>
                <option value="" disabled selected>Choose Application Type</option>
                <option value="Leave of Absence Form">Leave of Absence Form</option>
                <option value="Class Change Request">Class Change Request</option>
                <option value="Permission to Leave School">Permission to Leave School</option>
                <option value="Certificate Request Form">Certificate Request Form</option>
                <option value="Long-term Leave Request">Long-term Leave Request</option>
                <option value="Scholarship Application Form">Scholarship Application Form</option>
                <option value="Extracurricular Activity Registration">Extracurricular Activity Registration</option>
                <option value="Re-admission Application Form">Re-admission Application Form</option>
            </select>
        </div>


        <div class="form-group">
            <label for="applicationContent">Application Content</label>
            <textarea class="form-control" id="applicationContent" name="applicationContent" rows="4" required></textarea>
            <div class="invalid-feedback">Application Content cannot be empty or contain only whitespace.</div>
        </div>

        <button type="submit" class="btn btn-primary">Submit Application</button>
    </form>
</div>


<%@include file="../common/footer.jsp" %>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- DataTable JS -->
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
<script>
    function validateContent() {
        const contentField = document.getElementById("applicationContent");
        const content = contentField.value.trim();
        const isValid = content.length > 0;

        if (!isValid) {
            contentField.classList.add("is-invalid");
            return false; // Ngăn chặn gửi form
        } else {
            contentField.classList.remove("is-invalid");
            return true; // Cho phép gửi form
        }
    }
</script>
</body>
</html>
