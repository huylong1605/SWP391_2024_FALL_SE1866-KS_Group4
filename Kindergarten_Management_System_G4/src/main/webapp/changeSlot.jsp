<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Change Slot</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .subject-title {
            font-size: 1.5em;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }
        .arrow {
            font-size: 2em;
            color: #007bff;
            text-align: center;
            padding-top: 50px;
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-5">
    <h2 class="mb-4">Change Slot</h2>

    <!-- Display error messages if any -->
    <% if (request.getAttribute("sunday") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("sunday") %>
    </div>
    <% } %>
    <% if (request.getAttribute("ExistSchedule") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("ExistSchedule") %>
    </div>
    <% } %>
    <% if (request.getAttribute("dateFalseUpdate") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("dateFalseUpdate") %>
    </div>
    <% } %>
    <% String dates = request.getParameter("dateChange"); %>
    <!-- Hiển thị môn học (Subject) ở trên đầu -->
    <div class="subject-title">
        ${subjectById.subjectName}
    </div>

    <form action="changeSlotTeacher" method="post">
        <input type="hidden" name="scheduleIdd" value="${param.scheduleIdd != null ? param.scheduleIdd : schedules.scheduleId}"/>
        <input type="hidden" name="classIds" value="${param.classIds != null ? param.classIds : schedules.classId}">
        <div class="row">
            <!-- Left Column - Display current details -->
            <div class="col-md-5">
                <!-- Hiển thị ngày hiện tại -->
                <div class="mb-3">
                    <label for="date" class="form-label">Current Date</label>
                    <input type="text" class="form-control" id="date" name="date" value="${schedules.dateOfDay}" readonly>
                </div>

                <!-- Hiển thị slot hiện tại -->
                <div class="mb-3">
                    <label for="slot" class="form-label">Current Slot</label>
                    <input type="text" class="form-control" id="slot" name="slot"
                           value="${slot.slotName} (${slot.startTime} - ${slot.endTime})" readonly>
                </div>
            </div>

            <!-- Arrow in the Middle -->
            <div class="col-md-2 d-flex align-items-center justify-content-center">
                <div class="arrow"><i class="fas fa-arrow-right"></i></div>
            </div>

            <!-- Right Column - Input new details -->
            <div class="col-md-5">
                <!-- Trường nhập liệu để thay đổi ngày -->
                <div class="mb-3">
                    <label for="dateChange" class="form-label">New Date <span class="text-danger">*</span></label>
                    <input type="date" class="form-control" id="dateChange" name="dateChange" value="<%= (dates != null) ? dates : "" %>" required>
                </div>

                <!-- Trường chọn để thay đổi slot -->
                <div class="mb-3">
                    <label for="slotChange" class="form-label">New Slot <span class="text-danger">*</span></label>
                    <select class="form-select" id="slotChange" name="slotChange" required>

                        <option value="" disabled selected> Select New Slot</option>
                        <c:forEach var="slots" items="${listSlot}">
                        <option value="${slots.slotId}" <c:if test="${slots.slotId == param.slotChange}">selected</c:if>>
                            ${slots.slotName} - (${slots.startTime} - ${slots.endTime})
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>

        <!-- Action Buttons - Centered below -->
        <div class="action-buttons">
            <button type="submit" class="btn btn-primary">Update Slot</button>
            <a href="${pageContext.request.contextPath}/Views/Teacher/teacherSchedule?teacherId=${sessionScope.user.userID}" class="btn btn-primary">Back To Schedule</a>

        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
</body>
</html>
