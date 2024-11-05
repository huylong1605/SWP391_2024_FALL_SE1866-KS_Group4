<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Evaluation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container mt-5">
    <h2 style="font-family: 'Roboto', Helvetica, Arial, sans-serif"> Evaluation</h2>
    <!-- Button to navigate back to the evaluation list -->


    <!-- Display message if any -->
    <c:if test="${not empty message}">
        <div class="alert alert-info">
                ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/teacher/evaluations/update-evaluation" method="post">
        <input type="hidden" name="id" value="${evaluation.evaluationId}">

        <!-- Student Name (Read-only) -->
        <div class="form-group">
            <label for="studentName">Student Name</label>
            <input type="text" class="form-control" id="studentName" name="studentName"
                   value="${evaluation.studentName}" readonly>
        </div>

        <!-- Class (Read-only) -->
        <div class="form-group">
            <label for="classId">Class</label>
            <input type="text" class="form-control" id="classId" name="classId" value="${evaluation.className}"
                   readonly>
        </div>

        <!-- Term (Read-only) -->
        <div class="form-group">
            <label for="termId">Term</label>
            <input type="text" class="form-control" id="termId" name="termId" value="${evaluation.termName}" readonly>
        </div>

        <!-- Ranking (Editable Select Box) -->
        <div class="form-group">
            <label for="ranking">Ranking</label>
            <select class="form-control" id="ranking" name="ranking" required>
                <option value="Excellent" ${evaluation.ranking == 'Excellent' ? 'selected' : ''}>Excellent</option>
                <option value="Good" ${evaluation.ranking == 'Good' ? 'selected' : ''}>Good</option>
                <option value="Average" ${evaluation.ranking == 'Average' ? 'selected' : ''}>Average</option>
            </select>
        </div>

        <!-- Description (Editable) -->
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"
                      required>${evaluation.description}</textarea>
        </div>

        <button type="submit" class="btn btn-primary">Evaluation</button>
        <a href="${pageContext.request.contextPath}/teacher/evaluations/list" class="btn btn-primary btn-custom">
            Back to List
        </a>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
