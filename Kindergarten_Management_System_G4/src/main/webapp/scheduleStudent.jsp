<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Schedule</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .table-container {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            max-width: 900px;
            margin: auto;
        }
        .table thead th {
            text-align: center;
            background-color: #007bff;
            color: #fff;
        }
        .table tbody td {
            vertical-align: middle;
            text-align: center;
            font-size: 1em;
        }
        .slot-time {
            font-weight: bold;
            color: #007bff;
        }
        .time-range {
            font-size: 0.85em;
            color: #555;
        }
        .schedule-info {
            font-size: 1.2em;
            font-weight: bold;
            color: #333;
            margin-bottom: 15px;
            text-align: center;
            background-color: #e9ecef;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
        }
        .schedule-info div {
            padding: 5px 15px;
            border-right: 1px solid #ccc;
        }
        .schedule-info div:last-child {
            border-right: none;
        }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: #f2f2f2;
        }
        .dropdown-container {
            margin-bottom: 20px;
            text-align: center;
        }
        .dropdown-container select {
            width: 250px;
            padding: 8px;
            font-size: 1em;
            border-radius: 4px;
            border: 1px solid #ced4da;
        }
        .combo-boxes {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .combo-boxes select {
            margin: 0 10px;
            padding: 8px;
            font-size: 1em;
            border-radius: 4px;
            border: 1px solid #ced4da;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-5">
    <h2 class="text-center mb-4">Student Schedule</h2>

    <!-- Combo box for selecting week -->
    <div class="combo-boxes">
        <form action="scheduleStudent" method="get" class="d-flex align-items-center">
            <label for="weekSelector" class="mr-2">Select Week:</label>
            <input type="hidden" name="parentId" value="${param.parentId}"/>
            <select id="weekSelector" name="weekSelector" class="form-control" onchange="this.form.submit()">
                <c:forEach var="week" items="${weeks}">
                    <!-- Kiểm tra nếu tuần hiện tại hoặc tuần đã chọn khớp -->
                    <option value="${week}" <c:if test="${selectedWeek == null ? week == currentWeek : week ==  selectedWeek }">selected</c:if>>
                            ${week}
                    </option>
                </c:forEach>
            </select>


           <%-- <select id="term" name="term" class="form-control" onchange="this.form.submit()">
                <c:forEach var="term" items="${terms}">
                    <option value="${term.termId}"
                            <c:if test="${listScheduleStudent[0].termName == term.termName || selectedTerm == term.termId}">selected</c:if>>
                            ${term.termName}
                    </option>
                </c:forEach>
            </select>--%>

        </form>

    </div>

    <!-- Hiển thị thông tin Term và Class bên ngoài bảng -->
    <div class="schedule-info">
        <div>Term: <span>${listScheduleStudent[0].termName}</span></div>
        <div>Class: <span>${listScheduleStudent[0].className}</span></div>
        <div>Teacher: <span>${listScheduleStudent[0].teacher}</span></div>
        <div>Room: <span>${listScheduleStudent[0].room}</span></div>
    </div>

    <div class="table-container">
        <table class="table table-bordered table-hover table-striped">
            <thead class="thead-dark">
            <tr>
                <th>Day of the Week</th>
                <th>Subject</th>
                <th>Slot Time</th>
                <th>Date</th>
                <th>Attendance</th>
            </tr>
            </thead>
            <tbody id="scheduleTable">
            <c:forEach var="schedule" items="${listScheduleStudent}">
                <tr>
                    <td><strong>${schedule.dayOfWeek}</strong></td>
                    <td>${schedule.subject_name}</td>
                    <td class="slot-time">
                            ${schedule.slotName}
                        <div class="time-range">(${schedule.startTime} - ${schedule.endTime})</div>
                    </td>
                    <td>${schedule.dateOfDay}</td>
                    <td>
                        <c:choose>

                            <c:when test="${schedule.attendance == 1}">
                                <span style="color: green;">Present</span>
                            </c:when>

                            <c:when test="${schedule.attendance == 0}">
                                <span style="color: red;">Absent</span>
                            </c:when>

                            <c:otherwise>
                                <span style="color: gray;">Not Yet</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="text-right mt-3">
            <a href="${pageContext.request.contextPath}/Views/HomePage/HomePage.jsp" class="btn btn-primary">Back to Home Page</a>
        </div>
    </div>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    // Kiểm tra nếu tuần hiện tại đã được chọn và form chưa submit lần đầu tiên
    window.onload = function() {
        const weekSelector = document.getElementById("weekSelector");
        const currentWeek = "${currentWeek}"; // Tuần hiện tại từ server
        const selectedWeek = weekSelector.value;

        // Kiểm tra nếu tuần hiện tại đã là tuần được chọn
        if (selectedWeek === currentWeek) {
            // Submit form để chọn tuần hiện tại mà không cần thao tác từ người dùng
            document.getElementById("weekForm").submit();
        }
    };
</script>
</body>
</html>
