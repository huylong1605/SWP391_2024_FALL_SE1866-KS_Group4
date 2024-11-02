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
    <div class="container">
        <div class="header-section">
            <div class="combo-boxes">
                <!-- Combo box for selecting class -->
                <form action="listSchedule" method="get" class="d-flex align-items-center">
                    <div class="form-group mr-2">
                        <label for="classSelect" class="sr-only">Select Class</label>
                        <select id="classSelect" name="classSelect" class="form-control form-control-sm"
                                onchange="this.form.submit()">
                            <option value="" disabled selected>Select Class</option>
                            <c:forEach var="listclasses" items="${Classes}">
                                <option value="${listclasses.classId}"
                                        <c:if test="${listclasses.classId == param.classSelect}">selected</c:if>>
                                        ${listclasses.className}
                                </option>
                            </c:forEach>
                        </select>
                    </div>


                    <!-- Combo box for selecting date -->
                    <div class="form-group">
                        <label for="startDate" class="sr-only">Start Date</label>
                        <select id="startDate" name="startDate" class="form-control form-control-sm" onchange="this.form.submit()">
                            <!-- Tùy chọn mặc định cho Start Date -->
                            <option value="" <c:if test="${param.startDate == null || param.startDate == ''}">selected</c:if>>Start Date</option>
                            <c:forEach var="date" items="${dates}">
                                <option value="${date}" <c:if test="${date == param.startDate}">selected</c:if>>${date}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <p>To</p>

                    <div class="form-group">
                        <label for="endDate" class="sr-only">End Date</label>
                        <select id="endDate" name="endDate" class="form-control form-control-sm" onchange="this.form.submit()">
                            <!-- Tùy chọn mặc định cho End Date -->
                            <option value="" <c:if test="${param.endDate == null || param.endDate == ''}">selected</c:if>>End Date</option>
                            <c:forEach var="date" items="${dates}">
                                <option value="${date}" <c:if test="${date == param.endDate}">selected</c:if>>${date}</option>
                            </c:forEach>
                        </select>
                    </div>

                </form>
            </div>
            <!-- Create new schedule button -->
            <a href="createSchedule" class="btn btn-primary btn-sm">Add class to Schedule</a>
        </div>

    <!-- Display schedule list -->
    <div id="scheduleList" class="schedule-list">
        <div class="table-container">
            <div>teacher: <span>${schedules[0].teacher}</span></div>
            <table class="table table-bordered table-hover">
                <thead class="thead-dark">
                <tr>
                    <th>Day of the Week</th>
                    <th>Subject</th>
                    <th>Slot Time</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="schedule" items="${schedules}">
                    <tr>
                        <td>${schedule.dayOfWeek}</td>
                        <td>${schedule.subject_name}</td>
                        <td class="slot-time">
                                ${schedule.slotName}
                            <div class="time-range">(${schedule.startTime} - ${schedule.endTime})</div>
                        </td>
                        <td>${schedule.dateOfDay}</td>
                        <td><a href="editSchedule?schedulesId=${schedule.scheduleId}"
                               class="btn btn-warning btn-sm">Edit</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    // Hàm để tạo danh sách tùy chọn ngày
    function populateDateOptions() {
        const endDate = document.getElementById('endDate');
        const today = new Date();
        const Datee = new Date('2026-12-31'); // Giới hạn ngày đến cuối năm 2026

        // Tạo tùy chọn từ hôm nay đến ngày 2026-12-31
        while (today <= Datee) {
            const option = document.createElement('option');
            option.value = today.toISOString().split('T')[0]; // Định dạng: YYYY-MM-DD
            option.textContent = option.value; // Hiển thị ngày cũng theo định dạng YYYY-MM-DD

            endDate.appendChild(option);

            // Tăng ngày lên 1
            today.setDate(today.getDate() + 1);
        }
    }

    function populateDateOptions() {
        const startDate = document.getElementById('startDate');
        const today = new Date();
        const Datee = new Date('2026-12-31'); // Giới hạn ngày đến cuối năm 2026

        // Tạo tùy chọn từ hôm nay đến ngày 2026-12-31
        while (today <= Datee) {
            const option = document.createElement('option');
            option.value = today.toISOString().split('T')[0]; // Định dạng: YYYY-MM-DD
            option.textContent = option.value; // Hiển thị ngày cũng theo định dạng YYYY-MM-DD

            startDate.appendChild(option);

            // Tăng ngày lên 1
            today.setDate(today.getDate() + 1);
        }
    }

    // Gọi hàm để tạo tùy chọn khi tải trang
    document.addEventListener('DOMContentLoaded', populateDateOptions);
</script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
