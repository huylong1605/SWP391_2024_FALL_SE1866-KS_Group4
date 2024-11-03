<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Parent Term List</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTable CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <!-- Font Awesome CSS for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

<%@ include file="Views/common/header.jsp" %>


<div class="mt-5 main-content container">
    <h2>List of Terms</h2>
    <a href="Views/HomePage/HomePage.jsp" type="button" class="btn btn-primary mb-3">
        Back
    </a>

    <table id="termTable" class="table table-striped">
        <thead>
        <tr>
            <th>Term Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Year</th>
            <th>Information</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="term" items="${termList}" varStatus="index">
            <tr>
                <td>${term.termName}</td>
                <td>${term.startDate}</td>
                <td>${term.endDate}</td>
                <td>${term.year}</td>
                <td>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-success" data-toggle="modal"
                                data-target="#termInfoModal_${term.termId}">Detail
                        </button>

                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:forEach var="term" items="${termList}">
    <!-- Term Info Modal -->
    <div class="modal fade" id="termInfoModal_${term.termId}" tabindex="-1" role="dialog"
         aria-labelledby="termInfoModalLabel_${term.termId}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Term Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p><strong>Term Name:</strong> ${term.termName}</p>
                    <p><strong>Start Date:</strong> ${term.startDate}</p>
                    <p><strong>End Date:</strong> ${term.endDate}</p>
                    <p><strong>Year:</strong> ${term.year}</p>
                </div>
            </div>
        </div>
    </div>
</c:forEach>


<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- DataTable JS -->
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>

<script>

    function openDeleteModal(event, termId) {
        $('#deleteConfirmModal').modal('show');
        event.preventDefault(); // Prevent page reload

        // Set up the delete URL
        const deleteUrl = "term?action=delete&termId=" + termId;

        // Assign delete URL to confirmDeleteBtn
        document.getElementById('confirmDeleteBtn').onclick = function () {
            // Send delete request via fetch API
            fetch(deleteUrl, {method: 'POST'})  // Ensure method is POST for deletion
                .then(response => {
                    if (response.ok) {
                        // Show success message and close modal
                        document.getElementById('deleteModalBody').innerHTML = 'Term deleted successfully!';
                        document.getElementById('deleteModalFooter').style.display = 'none';
                        setTimeout(() => {
                            $('#deleteConfirmModal').modal('hide');
                            location.reload(); // Reload the page
                        }, 2000);
                    } else {
                        // Show failure message
                        document.getElementById('deleteModalBody').innerHTML = 'Failed to delete the term. Please try again.';
                    }
                })
                .catch(error => {
                    // Handle any errors
                    document.getElementById('deleteModalBody').innerHTML = 'An error occurred. Please try again.';
                });
        };

    }


    // Thêm sự kiện submit cho form khi document được load
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("addTermForm");
        form.addEventListener("submit", validateAddTermForm);
    });

    // Hàm hiển thị thông báo
    function showNotification(message) {
        const notification = document.getElementById("notification");
        notification.textContent = message;
        notification.style.display = "block";

        setTimeout(function () {
            $('#addTermModal').modal('hide');
            notification.style.display = "none";

            document.getElementById("addTermForm").submit();
        }, 3000);
    }

    function showEditNotification(termId, message) {
        const notification = document.getElementById("notification_edit_" + termId);
        notification.textContent = message;
        notification.style.display = "block";

        setTimeout(function () {
            $("#editTermModal_" + termId).modal('hide');

            setTimeout(function () {
                notification.style.display = "none";
                notification.textContent = "";
            }, 500);
        }, 5000);
    }


    function calculateMonthDifference(startDate, endDate) {
        const startYear = startDate.getFullYear();
        const startMonth = startDate.getMonth();
        const startDay = startDate.getDate();

        const endYear = endDate.getFullYear();
        const endMonth = endDate.getMonth();
        const endDay = endDate.getDate();

        // Tính tổng số tháng giữa hai ngày dựa trên sự khác biệt của năm và tháng
        let monthDifference = (endYear - startYear) * 12 + (endMonth - startMonth);

        // Nếu ngày kết thúc nhỏ hơn ngày bắt đầu, trừ đi 1 tháng
        if (endDay < startDay) {
            monthDifference--;
        }

        return monthDifference;
    }

    function validateAddTermForm(event) {

        event.preventDefault();
        const termName = document.getElementById("addTermName").value.trim();
        const startDate = document.getElementById("addStartDate").value.trim();
        const endDate = document.getElementById("addEndDate").value.trim();
        const year = document.getElementById("addYear").value.trim();

        // Các phần tử hiển thị lỗi
        const termNameError = document.getElementById("termNameError");
        const startDateError = document.getElementById("startDateError");
        const endDateError = document.getElementById("endDateError");
        const yearError = document.getElementById("yearError");

        // Reset lỗi trước khi validate
        termNameError.textContent = "";
        startDateError.textContent = "";
        endDateError.textContent = "";
        yearError.textContent = "";

        let isValid = true;

        // Ckeck Name
        if (termName.length > 20) {
            termNameError.textContent = "Term Name must not exceed 20 characters.";
            isValid = false;
        } else if (termName.length < 3) {
            termNameError.textContent = "Term Name must be at least 3 characters.";
            isValid = false;
        } else if (/[^a-zA-Z0-9\s]/.test(termName)) {
            termNameError.textContent = "Term Name must not contain special characters.";
            isValid = false;
        } else if (termName.trim() === "") {
            termNameError.textContent = "Term Name cannot be only spaces.";
            isValid = false;
        }

        // Validate the date range
        if (!isNaN(Date.parse(startDate)) && !isNaN(Date.parse(endDate))) {

            const startDateObj = new Date(startDate);
            const endDateObj = new Date(endDate);

            if (startDateObj > endDateObj) {
                startDateError.textContent = "Start Date cannot be after End Date.";
                isValid = false;
            } else if (startDateObj.getTime() === endDateObj.getTime()) {
                endDateError.textContent = "End Date must be after Start Date.";
                isValid = false;
            }

            const currentDate = new Date();
            if (startDateObj < currentDate.setHours(0, 0, 0, 0)) {
                startDateError.textContent = "Start Date cannot be in the past.";
                isValid = false;
            }
            // Tính khoảng cách tháng giữa startDate và endDate
            const monthDifference = calculateMonthDifference(startDateObj, endDateObj);

            if (monthDifference < 1) {
                endDateError.textContent = "Start Date and End Date must be at least 1 month apart.";
                isValid = false;
            }


        } else {
            if (isNaN(Date.parse(startDate))) {
                startDateError.textContent = "Start Date is invalid.";
                isValid = false;
            }
            if (isNaN(Date.parse(endDate))) {
                endDateError.textContent = "End Date is invalid.";
                isValid = false;
            }
        }

        // Kiểm tra định dạng của trường Year
        if (isNaN(year) || year < 2000 || year > new Date().getFullYear() + 10) {
            yearError.textContent = "Please enter a valid year between 2000 and the next 10 years.";
            isValid = false;
        }

        if (isValid) {
            showNotification("Term added successfully!");
        }

        return isValid;
    }


    // Hàm kiểm tra form cập nhật term (update term)
    function validateEditTermForm(event, termId) {
        event.preventDefault();
        // if(true){
        //     console.log(termId)
        //     let name = 'editTermNameError_e_' + termId;
        //     console.log(name)
        //     document.getElementById(name).textContent = "Term Name must not exceed 20 characters.";
        //
        //     return false;
        // }
        const termName = document.getElementById(`editTermName_` + termId).value.trim();
        const startDate = document.getElementById(`editStartDate_` + termId).value.trim();
        const endDate = document.getElementById(`editEndDate_` + termId).value.trim();
        const year = document.getElementById(`editYear_` + termId).value.trim();

        const termNameError = document.getElementById(`editTermNameError_e_` + termId);
        const startDateError = document.getElementById(`editStartDateError_e_` + termId);
        const endDateError = document.getElementById(`editEndDateError_e_` + termId);
        const yearError = document.getElementById(`editYearError_e_` + termId);

        // Reset error messages
        termNameError.textContent = "";
        startDateError.textContent = "";
        endDateError.textContent = "";
        yearError.textContent = "";

        let isValid = true;

        // Check name
        if (termName.length > 20) {
            termNameError.textContent = "Term Name must not exceed 20 characters.";
            isValid = false;
        } else if (termName.length < 3) {
            termNameError.textContent = "Term Name must be at least 3 characters.";
            isValid = false;
        } else if (/[^a-zA-Z0-9\s]/.test(termName)) {
            termNameError.textContent = "Term Name must not contain special characters.";
            isValid = false;
        } else if (termName.trim() === "") {
            termNameError.textContent = "Term Name cannot be empty.";
            isValid = false;
        }

        // Validate the date range
        if (!isNaN(Date.parse(startDate)) && !isNaN(Date.parse(endDate))) {
            // Check that start date is not after end date
            const startDateObj = new Date(startDate);
            const endDateObj = new Date(endDate);

            if (startDateObj > endDateObj) {
                startDateError.textContent = "Start Date cannot be after End Date.";
                isValid = false;
            } else if (startDateObj.getTime() === endDateObj.getTime()) {
                // Check if both dates are the same
                endDateError.textContent = "End Date must be after Start Date.";
                isValid = false;
            }

            const currentDate = new Date();
            if (startDateObj < currentDate.setHours(0, 0, 0, 0)) {
                startDateError.textContent = "Start Date cannot be in the past.";
                isValid = false;
            }
            // Tính khoảng cách tháng giữa startDate và endDate
            const monthDifference = calculateMonthDifference(startDateObj, endDateObj);

            if (monthDifference < 1) {
                endDateError.textContent = "Start Date and End Date must be at least 1 month apart.";
                isValid = false;
            }

        } else {
            if (isNaN(Date.parse(startDate))) {
                startDateError.textContent = "Start Date is invalid.";
                isValid = false;
            }
            if (isNaN(Date.parse(endDate))) {
                endDateError.textContent = "End Date is invalid.";
                isValid = false;
            }
        }


        // Check if year is within a valid range
        if (isNaN(year) || year < 2000 || year > new Date().getFullYear() + 10) {
            yearError.textContent = "Please enter a valid year between 2000 and the next 10 years.";
            isValid = false;
        }


        if (isValid) {
            // Tạo chuỗi dữ liệu URL-encoded
            const data = "action=update&termId=" + encodeURIComponent(termId) +
                "&termName=" + encodeURIComponent(termName) +
                "&startDate=" + encodeURIComponent(startDate) +
                "&endDate=" + encodeURIComponent(endDate) +
                "&year=" + encodeURIComponent(year);

            // Khởi tạo XMLHttpRequest
            const xhr = new XMLHttpRequest();

            // Mở kết nối với phương thức POST và URL đúng
            xhr.open("POST", "term", true);

            // Thiết lập header để gửi dữ liệu URL-encoded
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            // Xử lý phản hồi từ server
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const response = xhr.responseText.trim();
                    if (response === "success") {
                        showEditNotification(termId, "Term updated successfully!");
                        setTimeout(() => {
                            window.location.reload();
                        }, 2000)
                    } else {
                        showEditNotification(termId, response);
                    }
                }
            };

            // Xử lý lỗi mạng hoặc lỗi gửi yêu cầu
            xhr.onerror = function () {
                showEditNotification(termId, "Edit failed !");
            };

            // Gửi dữ liệu qua POST
            xhr.send(data);

            return false;  // Ngăn form submit mặc định
        }

        return isValid;

    }


    $(document).ready(function () {
        $('#termTable').DataTable({
            "searching": false
        });
    });

</script>


<%@include file="Views/common/footer.jsp" %>
</body>
</html>
