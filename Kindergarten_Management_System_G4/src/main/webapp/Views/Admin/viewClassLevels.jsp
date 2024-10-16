<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Manage - Class Levels</title>
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
                            <div class="card my-4">
                                <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                        <h6 class="text-white text-capitalize ps-3">Manage Class Levels</h6>
                                    </div>
                                </div>
                                <div class="card-body px-0 pb-2">
                                    <!-- Search input and sort dropdown -->
                                    <div class="row px-4 mb-3">
                                        <div class="col-md-6">
                                            <input type="text" id="searchInput" class="form-control" onkeyup="filterTable()" placeholder="Search by class level name...">
                                        </div>
                                        <div class="col-md-4">
                                            <select id="sortDropdown" class="form-control" onchange="sortTable()">
                                                 <option value="default">Default</option>
                                                <option value="asc">Sort by Name (A-Z)</option>
                                                <option value="desc">Sort by Name (Z-A)</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="table-container">
                                        <table class="table align-items-center mb-0">
                                            <thead>
                                                <tr>
                                                    <th class="text-center">#</th>
                                                    <th class="text-center">Name</th>
                                                    <th class="text-center">Description</th>
                                                    <th class="text-center">Age Range</th>
                                                    <th class="text-center">Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody id="classLevelTableBody">
                                                <c:forEach var="class_level" items="${classLevelList}">
                                                    <tr>
                                                        <td class="text-center">${class_level.classLevelId}</td>
                                                        <td class="text-center classLevelName">${class_level.classLevelName}</td>
                                                        <td class="text-center">${class_level.description}</td>
                                                        <td class="text-center">${class_level.ageRange}</td>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-warning btn-sm"
                                                                    onclick="openEditModal('${class_level.classLevelId}', '${class_level.classLevelName}', '${class_level.description}', '${class_level.ageRange}')">
                                                                Update
                                                            </button>

                                                            <button type="button" class="btn btn-danger btn-sm"
                                                                    onclick="confirmDelete('${class_level.classLevelId}')">
                                                                Delete
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <!-- Add New Class Level Button -->
                            <button type="button" class="btn btn-success" onclick="openAddModal()">Add New Class Level</button>

                            <!-- Pagination -->
                            <div class="pagination-container">
                                <nav>
                                    <ul class="pagination" id="pagination">
                                        <!-- Pagination will be generated here -->
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- Modal for adding a new class level -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/addClassLevel" method="POST" onsubmit="return confirmAdd()">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Add New Class Level</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="addClassName" class="form-label">Class Level Name</label>
                            <input type="text" class="form-control" id="addClassName" name="classLevelName" required>
                        </div>
                        <div class="mb-3">
                            <label for="addDescription" class="form-label">Description</label>
                            <input type="text" class="form-control" id="addDescription" name="description" required>
                        </div>
                        <div class="mb-3">
                            <label for="addAgeRange" class="form-label">Age Range</label>
                            <input type="number" class="form-control" id="addAgeRange" name="ageRange" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal for editing an existing class level -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/updateClassLevel" method="POST" onsubmit="return confirmUpdate()">
                    <input type="hidden" id="editClassLevelId" name="classLevelId">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Edit Class Level</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="editClassName" class="form-label">Class Level Name</label>
                            <input type="text" class="form-control" id="editClassName" name="classLevelName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description</label>
                            <input type="text" class="form-control" id="editDescription" name="description" required>
                        </div>
                        <div class="mb-3">
                            <label for="editAgeRange" class="form-label">Age Range</label>
                            <input type="number" class="form-control" id="editAgeRange" name="ageRange" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Confirm Delete Modal -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/deleteClassLevel" method="POST">
                    <input type="hidden" id="deleteClassLevelId" name="classLevelId">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Delete</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this class level?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Success Modal -->
    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="successModalLabel">Success</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Action completed successfully!</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-bs-dismiss="modal">OK</button>
                </div>
            </div>
        </div>
    </div>

 <!-- Include the common footer JSP file -->
    <%@include file="../common/footer.jsp"%>
    <script>
        let currentPage = 1;
        const rowsPerPage = 5;

        // Function to handle pagination
        function paginateTable(page) {
            const rows = document.querySelectorAll('#classLevelTableBody tr');
            const totalRows = rows.length;
            const totalPages = Math.ceil(totalRows / rowsPerPage);

            currentPage = page;

            // Hide all rows initially
            rows.forEach(row => row.style.display = 'none');

            // Show only rows for the current page
            const start = (currentPage - 1) * rowsPerPage;
            const end = start + rowsPerPage;
            rows.forEach((row, index) => {
                if (index >= start && index < end) {
                    row.style.display = '';
                }
            });

            // Update pagination buttons
            updatePagination(totalPages);
        }

        // Function to generate pagination buttons
        function updatePagination(totalPages) {
            const pagination = document.getElementById('pagination');
            pagination.innerHTML = '';

            for (let i = 1; i <= totalPages; i++) {
                const li = document.createElement('li');
                li.classList.add('page-item');
                if (i === currentPage) li.classList.add('active');

                const a = document.createElement('a');
                a.classList.add('page-link');
                a.href = '#';
                a.textContent = i;
                a.onclick = () => paginateTable(i);

                li.appendChild(a);
                pagination.appendChild(li);
            }
        }

        // Initial pagination when the page loads
        document.addEventListener('DOMContentLoaded', () => {
            paginateTable(1);
        });

        // Filter and sort functions (as defined previously)
        function filterTable() {
            const input = document.getElementById("searchInput").value.toLowerCase();
            const rows = document.querySelectorAll("#classLevelTableBody tr");

            rows.forEach(row => {
                const className = row.querySelector(".classLevelName").textContent.toLowerCase();
                if (className.includes(input)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        }

        function sortTable() {
            const tableBody = document.getElementById("classLevelTableBody");
            const rows = Array.from(tableBody.getElementsByTagName("tr"));
            const sortDirection = document.getElementById("sortDropdown").value;

            if (sortDirection === "default") {
                // Reset về thứ tự ban đầu
                rows.sort((a, b) => {
                    const idA = parseInt(a.querySelector("td").textContent);
                    const idB = parseInt(b.querySelector("td").textContent);
                    return idA - idB;
                });
            } else {
                rows.sort((a, b) => {
                    const nameA = a.querySelector(".classLevelName").textContent.toLowerCase();
                    const nameB = b.querySelector(".classLevelName").textContent.toLowerCase();

                    if (sortDirection === "asc") {
                        return nameA.localeCompare(nameB);
                    } else {
                        return nameB.localeCompare(nameA);
                    }
                });
            }

            // Cập nhật lại thứ tự các hàng trong bảng
            rows.forEach(row => tableBody.appendChild(row));
        }


        // Modals for Add, Edit, Delete, Success (same as defined previously)
        function openAddModal() {
            var addModal = new bootstrap.Modal(document.getElementById('addModal'), {
                keyboard: false
            });
            addModal.show();
        }

        function openEditModal(id, name, description, ageRange) {
            document.getElementById("editClassLevelId").value = id;
            document.getElementById("editClassName").value = name;
            document.getElementById("editDescription").value = description;
            document.getElementById("editAgeRange").value = ageRange;

            var editModal = new bootstrap.Modal(document.getElementById('editModal'), {
                keyboard: false
            });
            editModal.show();
        }

        function confirmDelete(id) {
            document.getElementById("deleteClassLevelId").value = id;
            var deleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'), {
                keyboard: false
            });
            deleteModal.show();
        }

        function confirmAdd() {
            return confirm("Are you sure you want to add this class level?");
        }

        function confirmUpdate() {
            return confirm("Are you sure you want to update this class level?");
        }

        function showSuccessModal() {
            var successModal = new bootstrap.Modal(document.getElementById('successModal'), {
                keyboard: false
            });
            successModal.show();
        }

        // Show success modal after redirect if 'success' parameter is present
        document.addEventListener('DOMContentLoaded', function () {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('success') && urlParams.get('success') === 'true') {
                showSuccessModal();
            }
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
