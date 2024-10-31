<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Schedule Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
        }
        .form-group {
            margin: 0 10px;
            width: auto;
        }
        .combo-boxes {
            display: flex;
            align-items: center;
        }
        .schedule-list {
            margin-top: 40px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<%@ include file="/Views/common/header.jsp" %>

<div class="container">
    <!-- Top section with combo boxes and create button -->
    <div class="header-section">
        <div class="combo-boxes">
            <!-- Combo box for selecting class -->
            <div class="form-group">
                <label for="classSelect" class="sr-only">Select Class</label>
                <select id="classSelect" name="classSelect" class="form-control form-control-sm">
                    <option value="" disabled selected>Select Class</option>
                    <c:forEach var="classes" items="${listClass}">
                        <option value="${classes.classId}"
                                <c:if test="${classes.classId == param.class_id}">selected</c:if>>
                                ${classes.className}
                        </option>
                    </c:forEach>
                    <!-- Add more classes as needed -->
                </select>
            </div>

            <!-- Combo box for selecting date -->
            <div class="form-group">
                <label for="dateSelect" class="sr-only">Select Date</label>
                <select id="dateSelect" name="dateSelect" class="form-control form-control-sm">
                    <option value="2024-11-01">November 1, 2024</option>
                    <option value="2024-11-02">November 2, 2024</option>
                    <option value="2024-11-03">November 3, 2024</option>
                    <!-- Add more dates as needed -->
                </select>
            </div>
        </div>

        <!-- Create new schedule button -->
        <a href="/createSchedule.jsp" class="btn btn-primary btn-sm">Create New Schedule</a>
    </div>

    <!-- Display schedule list -->
    <div id="scheduleList" class="schedule-list">
        <h3>Schedule List</h3>
        <!-- This section will dynamically list schedules based on the selected class and date -->
        <p>No schedules available. Please select a class and date to view.</p>
    </div>
</div>

<%@ include file="/Views/common/footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
