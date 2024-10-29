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
        }
        .table thead th {
            text-align: center;
        }
        .table tbody td {
            vertical-align: middle;
            text-align: center;
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
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-5">
    <h2 class="text-center mb-4">Student Schedule</h2>

    <!-- Hiển thị thông tin Term và Class bên ngoài bảng -->
    <div class="schedule-info">
        <div>Term: <span>${listScheduleStudent[0].termName}</span></div>
        <div>Class: <span>${listScheduleStudent[0].className}</span></div>
    </div>

    <div class="table-container">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th>Day of the Week</th>
                <th>Subject</th>
                <th>Slot Time</th>
                <th>Date</th>
                <th>Room</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="schedule" items="${listScheduleStudent}">
                <tr>
                    <td>${schedule.dayOfWeek}</td>
                    <td>${schedule.subject_name}</td>
                    <td class="slot-time">
                            ${schedule.slotName}
                        <div class="time-range">(${schedule.startTime} - ${schedule.endTime})</div>
                    </td>
                    <td>${schedule.dateOfDay}</td>
                    <td>${schedule.room}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
