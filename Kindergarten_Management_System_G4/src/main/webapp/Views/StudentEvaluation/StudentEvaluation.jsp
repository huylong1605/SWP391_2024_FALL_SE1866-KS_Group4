<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Evaluation</title>
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
    <h2 style="font-family: 'Roboto', Helvetica, Arial, sans-serif">Your Evaluations</h2>
    <a href="${pageContext.request.contextPath}/Views/HomePage/HomePage.jsp" type="button" class="btn btn-primary mb-3">
        Back
    </a>

    <!-- Form to select term -->
    <form id="termSelectionForm" class="form-inline mb-3" onsubmit="return validateForm()">
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
    <!-- Export to Word Button -->
    <button type="button" id="exportButton" class="btn btn-success" onclick="exportToWord()" disabled>Export to Word
    </button>

    <!-- Table to show evaluations -->
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>Student Name</th>
            <th>Class Name</th>
            <th>Date of Birth</th>
            <th>Gender</th>
            <th>Ranking</th>
            <th>Description</th>
            <th>Evaluation Date</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty evaluations}">
                <c:forEach var="evaluation" items="${evaluations}">
                    <tr>
                        <td>${evaluation.studentName}</td>
                        <td>${evaluation.className}</td>
                        <td>${evaluation.dateOfBirth}</td>
                        <td>${evaluation.gender}</td>
                        <td>${evaluation.ranking}</td>
                        <td>${evaluation.description}</td>
                        <td>${evaluation.evaluationDate}</td>
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
        const termId = document.getElementById("termId").value;
        if (!termId) {
            alert("Please select a term.");
            return false;
        }
        checkExportButton();
        return true;
    }

    function checkExportButton() {
        const termId = document.getElementById("termId").value;
        document.getElementById("exportButton").disabled = !termId;
    }

    // Attach event listener to term select box
    document.getElementById("termId").addEventListener("change", checkExportButton);

    function exportToWord() {
        const termId = document.getElementById("termId").value;
        const linkDownload = `http://localhost:8080${window.location.origin}${pageContext.request.contextPath}/student/evaluations/export?termId=` + termId;
        window.location.href = linkDownload;
    }
</script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
