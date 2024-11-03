<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Attendance List</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <style>
    /* CSS để định vị Toast ở giữa trên cùng */
    .toast-container {
      position: fixed;
      top: 20px;
      right: 20px;
      z-index: 9999;
    }
  </style>
</head>
<body>
<div class="container mt-5">
<%--  <c:if test="${not empty successMessage}">--%>
<%--    <div class="alert alert-success alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">--%>
<%--        ${successMessage}--%>
<%--      <button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
<%--        <span aria-hidden="true">&times;</span>--%>
<%--      </button>--%>
<%--    </div>--%>
<%--  </c:if>--%>
  <h2 class="text-center">Attendance List for Class ${className} on ${date} (Slot: ${slotName})</h2>
  <form action="${pageContext.request.contextPath}/Views/Teacher/attendanceStudent" method="post">
    <input type="hidden" name="classId" value="${classId}">
    <input type="hidden" name="date" value="${date}">
    <input type="hidden" name="slotId" value="${slotId}">
    <input type="hidden" name="className" value="${className}"> <!-- Thêm dòng này -->
    <input type="hidden" name="slotName" value="${slotName}"> <!-- Thêm dòng này -->

    <div class="table-responsive">
      <table class="table table-bordered">
        <thead>
        <tr>
          <th class="text-center">Student ID</th>
          <th class="text-center">Student Name</th>
          <th class="text-center">Date of Birth</th>
          <th class="text-center">Attendance Status</th>
          <th class="text-center">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="student" items="${attendanceList}">
          <tr>
            <td class="text-center">${student.studentId}</td>
            <td class="text-center">${student.studentName}</td>
            <td class="text-center">${student.dateOfBirth}</td>
            <td class="text-center">
              <span id="statusText_${student.studentId}">${student.attendStatus ? 'Present' : 'Absent'}</span>
              <input type="hidden" id="attendStatus_${student.studentId}" name="attendStatus" value="${student.attendStatus}">
              <input type="hidden" name="studentId" value="${student.studentId}"> <!-- Thêm dòng này -->
            </td>

            <td class="text-center">
              <button type="button" class="btn btn-warning" onclick="toggleAttendance(${student.studentId})">
                Toggle Attendance
              </button>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="text-center">
      <button type="submit" class="btn btn-primary">Save Attendance</button>
    </div>
  </form>
</div>

<!-- Toast hiển thị thông báo thành công -->
<div class="toast-container">
  <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000">
    <div class="toast-header">
      <strong class="mr-auto text-success">Success</strong>
      <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="toast-body">
      ${sessionScope.successMessage}
    </div>
  </div>
</div>

<script>
  function toggleAttendance(studentId) {
    var attendanceStatusElement = document.getElementById("attendStatus_" + studentId);
    var currentStatus = attendanceStatusElement.value === "true";
    var newStatus = !currentStatus;
    attendanceStatusElement.value = newStatus;
    var statusTextElement = document.getElementById("statusText_" + studentId);
    statusTextElement.textContent = newStatus ? "Present" : "Absent";
  }

  // Hiển thị Toast nếu có thông báo thành công trong session
  $(document).ready(function() {
    <c:if test="${not empty sessionScope.successMessage}">
    $('#successToast').toast('show');
    <%-- Xóa thông báo sau khi hiển thị --%>
    <c:remove var="successMessage" scope="session" />
    </c:if>
  });
</script>
</body>
</html>
