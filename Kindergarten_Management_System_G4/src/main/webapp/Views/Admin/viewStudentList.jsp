<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Manage - Student List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />

    <!-- Custom CSS for fixed height and pagination -->
    <style>
        .table-container {
            min-height: 350px; /* Đảm bảo chiều cao cố định */
            max-height: 600px; /* Nếu cần */
            overflow: auto;
        }
        .pagination-container {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }
    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
    <!-- Include the common header JSP file -->
    <%@include file="../common/header.jsp"%>

    <!-- Main container for the entire page layout -->
    <div class="containerAll">
        <div class="wrapper">
            <!-- Sidebar section -->
            <aside id="sidebar" class="expand">
                <div class="d-flex">
                    <!-- Button to toggle sidebar visibility -->
                    <button class="toggle-btn" type="button">
                        <i class="lni lni-grid-alt"></i>
                    </button>
                    <!-- Sidebar logo -->
                    <div class="sidebar-logo">
                        <a href="#">Admin Manage</a>
                    </div>
                </div>
                <!-- Sidebar navigation items -->
                <ul class="sidebar-nav">
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/Views/Admin/accountManage" class="sidebar-link">
                            <i class="lni lni-user"></i>
                            <span>Manage Account</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/Views/Admin/notifications" class="sidebar-link">
                            <i class="lni lni-agenda"></i>
                            <span>Manage Notification</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/subject" class="sidebar-link">
                            <i class="lni lni-graduation"></i>
                            <span>Manage Subject</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/viewStudentList" class="sidebar-link">
                            <i class="lni lni-graduation"></i>
                            <span>View List Student</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/listClass" class="sidebar-link">
                            <i class="lni lni-graduation"></i>
                            <span>View list class</span>
                        </a>
                    </li>
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/classLevel" class="sidebar-link">
                            <i class="lni lni-graduation"></i>
                            <span>Manage Class Level</span>
                        </a>
                    </li>
                </ul>
            </aside>

            <!-- Main content section -->
            <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
                <div class="container-fluid py-4">
                    <div class="row">
                        <div class="col-12">

                            <!-- Card container for displaying the student list -->
                            <div class="card my-4">
                                <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                        <h6 class="text-white text-capitalize ps-3">Student List</h6>
                                    </div>
                                </div>
                                <div class="card-body px-0 pb-2">
                                   <!-- Search and Filter Section -->
                                                               <div class="row mb-3" style="margin-left: 100px">
                                                                   <div class="col-md-4">
                                                                       <input type="text" id="searchInput" class="form-control" placeholder="Search by Name" onkeyup="searchTable()">
                                                                   </div>
                                                                   <div class="col-md-4">
                                                                       <select id="sortDropdown" class="form-control" onchange="sortTable()">
                                                                           <option value="default">Default</option>
                                                                           <option value="asc">Sort by Name (A-Z)</option>
                                                                           <option value="desc">Sort by Name (Z-A)</option>
                                                                       </select>
                                                                   </div>
                                                               </div>
                                    <div class="table-responsive p-0">
                                        <!-- Table displaying student details -->
                                        <table class="table align-items-center mb-0">
                                            <thead>
                                                <tr>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">#</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Name</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Date of Birth</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Gender</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Address</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Phone Number</th>
                                                    <th class="text-secondary opacity-7"></th>
                                                </tr>
                                            </thead>
                                            <tbody id="studentTableBody">
                                                <!-- Initialize counter for student numbering -->
                                                <c:set var="counter" value="${(currentPage - 1) * 5 + 1}" />
                                                <!-- Iterate over the list of students -->
                                                <c:forEach var="student" items="${students}">
                                                    <tr>
                                                        <td class="text-center">${counter}</td>
                                                        <td class="text-center studentName">${student.name}</td>
                                                        <td class="text-center">${student.dob}</td>
                                                        <td class="text-center">${student.gender ? 'Male' : 'Female'}</td>
                                                        <td class="text-center">${student.address}</td>
                                                        <td class="text-center">${student.phoneNumber}</td>
                                                        <td class="text-center">
                                                            <!-- Clickable text to view student details in a modal -->
                                                            <p style="cursor: pointer;" class="text-secondary font-weight-bold text-xs"
                                                               onclick="showModal('${student.studentId}', '${student.name}', '${student.dob}', '${student.gender ? 'Male' : 'Female'}', '${student.address}', '${student.phoneNumber}')">View</p>
                                                        </td>
                                                    </tr>
                                                    <!-- Increment the student counter -->
                                                    <c:set var="counter" value="${counter + 1}" />
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <!-- Pagination controls -->
                            <div class="row">
                                <nav class="col-md-7" aria-label="Page navigation">
                                    <ul class="pagination">
                                        <!-- Display previous page link if applicable -->
                                        <c:if test="${currentPage > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="${pageContext.request.contextPath}/viewStudentList?pageNumber=${currentPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:if>

                                        <!-- Loop through pages and display page links -->
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                <a class="page-link" href="${pageContext.request.contextPath}/viewStudentList?pageNumber=${i}">${i}</a>
                                            </li>
                                        </c:forEach>

                                        <!-- Display next page link if applicable -->
                                        <c:if test="${currentPage < totalPages}">
                                            <li class="page-item">
                                                <a class="page-link" href="${pageContext.request.contextPath}/viewStudentList?pageNumber=${currentPage + 1}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- Modal for displaying student information -->
    <div class="modal fade" id="studentModal" tabindex="-1" aria-labelledby="studentModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="studentModalLabel">Student Information</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Form fields to display student information -->
                    <div class="mb-3">
                        <label for="studentName" class="form-label">Name:</label>
                        <input type="text" class="form-control" id="studentName" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="studentDob" class="form-label">Date of Birth:</label>
                        <input type="text" class="form-control" id="studentDob" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="studentGender" class="form-label">Gender:</label>
                        <input type="text" class="form-control" id="studentGender" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="studentAddress" class="form-label">Address:</label>
                        <input type="text" class="form-control" id="studentAddress" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="studentPhoneNumber" class="form-label">Phone Number:</label>
                        <input type="text" class="form-control" id="studentPhoneNumber" readonly>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Function to open the modal with student details
        function showModal(studentId, name, dob, gender, address, phoneNumber) {
            document.getElementById("studentName").value = name;
            document.getElementById("studentDob").value = dob;
            document.getElementById("studentGender").value = gender;
            document.getElementById("studentAddress").value = address;
            document.getElementById("studentPhoneNumber").value = phoneNumber;

            var modal = new bootstrap.Modal(document.getElementById('studentModal'), { keyboard: false });
            modal.show();
        }

        // Search function
        function searchTable() {
            const searchInput = document.getElementById('searchInput').value.toLowerCase();
            const tableRows = document.querySelectorAll('#studentTableBody tr');

            tableRows.forEach((row) => {
                const studentName = row.querySelector('.studentName').textContent.toLowerCase();
                if (studentName.includes(searchInput)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }

        // Sort function
        function sortTable() {
            const tableBody = document.getElementById('studentTableBody');
            const rows = Array.from(tableBody.getElementsByTagName('tr'));
            const sortDirection = document.getElementById('sortDropdown').value;

            if (sortDirection === 'default') {
                rows.sort((a, b) => {
                    const idA = parseInt(a.querySelector('td').textContent);
                    const idB = parseInt(b.querySelector('td').textContent);
                    return idA - idB;
                });
            } else {
                rows.sort((a, b) => {
                    const nameA = a.querySelector('.studentName').textContent.toLowerCase();
                    const nameB = b.querySelector('.studentName').textContent.toLowerCase();
                    return sortDirection === 'asc' ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
                });
            }

            rows.forEach(row => tableBody.appendChild(row));
        }
    </script>

    <!-- Include Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <!-- Include custom JavaScript -->
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <!-- Include the common footer JSP file -->
    <%@include file="../common/footer.jsp"%>
</body>
</html>
