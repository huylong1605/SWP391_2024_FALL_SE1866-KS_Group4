<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Student Evaluation List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        table {
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            text-align: center;
        }

        th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container mt-5">
    <h2 style="font-family: 'Roboto', Helvetica, Arial, sans-serif">Student Evaluation List</h2>

    <!-- Form to select class and term -->
    <form action="${pageContext.request.contextPath}/teacher/evaluations/list" method="get" class="form-inline mb-3"
          onsubmit="return validateForm()">
        <!-- Class Dropdown -->
        <select name="classId" id="classId" class="form-control mr-2">
            <option value="">Select Class</option>
            <c:forEach var="classItem" items="${classes}">
                <option value="${classItem.classId}" ${classItem.classId == classIdSelected ? 'selected' : ''}>
                        ${classItem.className}
                </option>
            </c:forEach>
        </select>

        <!-- Term Dropdown -->
        <select name="termId" id="termId" class="form-control mr-2">
            <option value="">Select Term</option>
            <c:forEach var="term" items="${terms}">
                <option value="${term.termId}" ${term.termId == termIdSelected ? 'selected' : ''}>
                        ${term.termName}
                </option>
            </c:forEach>
        </select>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary">Show Evaluations</button>
    </form>

    <!-- Export to Excel Button -->
    <button id="exportButton" class="btn btn-success" onclick="exportToExcel()" disabled>Export to Excel</button>


    <!-- Table to show evaluations -->
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>Student Name</th>
            <th>Date of Birth</th>
            <th>Gender</th>
            <th>Ranking</th>
            <th>Description</th>
            <th>Evaluation Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty evaluations}">
                <c:forEach var="evaluation" items="${evaluations}">
                    <tr>
                        <td>${evaluation.studentName}</td>
                        <td>${evaluation.dateOfBirth}</td>
                        <td>${evaluation.gender}</td>
                        <td>${evaluation.ranking}</td>
                        <td>${evaluation.description}</td>
                        <td>${evaluation.evaluationDate}</td>
                        <td>
                            <!-- Link to view details -->
                            <a href="${pageContext.request.contextPath}/teacher/evaluations/detail/${evaluation.evaluationId}"
                               class="btn btn-info">Evaluation</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="7" class="text-center">No data found.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<script>
    checkExportButton();

    function validateForm() {
        const classId = document.getElementById("classId").value;
        const termId = document.getElementById("termId").value;
        if (!classId) {
            alert("Please select a class.");
            return false;
        }

        if (!termId) {
            alert("Please select a term.");
            return false;
        }
        checkExportButton();
        return true;
    }

    function checkExportButton() {
        const classId = document.getElementById("classId").value;
        const termId = document.getElementById("termId").value;
        document.getElementById("exportButton").disabled = !(classId && termId);
    }

    // Attach event listeners to select boxes
    document.getElementById("classId").addEventListener("change", checkExportButton);
    document.getElementById("termId").addEventListener("change", checkExportButton);

    function exportToExcel() {
        const classId = document.getElementById("classId").value;
        const termId = document.getElementById("termId").value;
        const linkDownload = `http://localhost:8086${window.location.origin}${pageContext.request.contextPath}/teacher/evaluations/export?classId=` + classId + `&termId=` + termId;
        window.location.href = linkDownload;
    }

</script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
