<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subject List</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- DataTable CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <!-- Font Awesome CSS for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>

<body>
<!-- Sidebar -->
<%@ include file="subject-manage-sidebar.jsp" %>

<div class="mt-5 main-content">
    <h2>Subject List</h2>

    <c:if test="${param.success ne null}">
        <div class="alert alert-success" role="alert">
            Success!
        </div>
    </c:if>
    <c:if test="${param.fail ne null}">
        <div class="alert alert-danger" role="alert">
            Failed!
        </div>
    </c:if>


    <c:if test="${sessionScope.errorMessage != null}">
        <div class="alert alert-danger" role="alert">
                ${sessionScope.errorMessage}
        </div>
        <%
            session.removeAttribute("errorMessage");
        %>
    </c:if>

    <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#addsubjectModal">Add subject</button>

    <table id="subjectTable" class="table table-striped">
        <thead>
        <tr>
            <th>Subject ID</th>
            <th>Subject Code</th>
            <th>Subject Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="subject" items="${subjectList}" varStatus="index">
            <tr>
                <td>${index.index + 1}</td>
                <td>${subject.subjectCode}</td>
                <td>${subject.subjectName}</td>
                <td>${subject.description}</td>
                <td>
                    <span class="btn btn-success">${subject.status}</span>
                </td>
                <td>
                    <div style="display: flex; gap: 5px;">
                    <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#subjectInfoModal_${subject.subjectId}">Info</button>
                    <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#editsubjectModal_${subject.subjectId}">Edit</button>
                    <a type="button" class="btn btn-danger btn-sm" href="subject?action=delete&subjectId=${subject.subjectId}" onclick="return confirm('Are you sure to delete this subject?')">Delete</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:forEach var="subject" items="${subjectList}">

    <!-- Edit subject Modal -->
    <div class="modal fade" id="editsubjectModal_${subject.subjectId}" tabindex="-1" role="dialog" aria-labelledby="editsubjectModalLabel_${subject.subjectId}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editsubjectModalLabel_${subject.subjectId}">Edit subject</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <!-- Edit subject Form -->
                    <form action="subject" method="POST">
                        <div class="modal-body">
                            <input type="hidden" name="subjectId" value="${subject.subjectId}"/>
                            <input type="hidden" name="action" value="update"/>

                            <div class="form-group">
                                <label for="subjectCode">Subject Code</label>
                                <input type="text" class="form-control" name="subjectCode" value="${subject.subjectCode}" required>
                            </div>

                            <div class="form-group">
                                <label for="subjectName">Subject Name</label>
                                <input type="text" class="form-control" name="subjectName" value="${subject.subjectName}" required>
                            </div>

                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea class="form-control" name="description" required>${subject.description}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="subjectName">Subject Status</label>
                                <select name="status" class="form-control">
                                    <option value="active" ${subject.status ==  'Active' ?  "selected" : ""}>Active</option>
                                    <option value="In active" ${subject.status ==  'In active' ?  "selected" : ""}>In Active</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="userId">User ID</label>
                                <input type="number" class="form-control" name="userId" value="${subject.userId}" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- subject Info Modal -->
    <div class="modal fade" id="subjectInfoModal_${subject.subjectId}" tabindex="-1" role="dialog" aria-labelledby="subjectInfoModalLabel_${subject.subjectId}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="subjectInfoModalLabel_${subject.subjectId}">subject Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <p><strong>ID:</strong> ${subject.subjectId}</p>
                    <p><strong>Title:</strong> ${subject.subjectCode}</p>
                    <p><strong>Notes:</strong> ${subject.subjectName}</p>
                    <p><strong>Status:</strong> ${subject.status}</p>
                </div>
            </div>
        </div>
    </div>

</c:forEach>

<!-- Add subject Modal -->
<div class="modal fade" id="addsubjectModal" tabindex="-1" role="dialog" aria-labelledby="addsubjectModalLabel" aria-hidden="true">
    <!-- Modal Content -->
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="addsubjectModalLabel">Add subject</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <!-- Modal Body -->
            <div class="modal-body">
                <!-- Add subject Form -->
                <form action="subject" method="POST">
                    <input type="hidden" name="action" value="add"/>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="subjectCode">Subject Code</label>
                            <input type="text" class="form-control" id="subjectCode" name="subjectCode" placeholder="Enter subject code" required>
                        </div>

                        <div class="form-group">
                            <label for="subjectName">Subject Name</label>
                            <input type="text" class="form-control" id="subjectName" name="subjectName" placeholder="Enter subject name" required>
                        </div>

                        <div class="form-group">
                            <label for="description">Description</label>
                            <textarea class="form-control" id="description" name="description" placeholder="Enter description" required></textarea>
                        </div>
                        <select name="status" class="form-control">
                            <option value="active">Active</option>
                            <option value="In active">In Active</option>
                        </select>

                        <div class="form-group">
                            <label for="userId">User ID</label>
                            <input type="number" class="form-control" id="userId" name="userId" placeholder="Enter user ID" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Add Subject</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- DataTable JS -->
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>

<script>
    $(document).ready(function () {
        $('#subjectTable').DataTable({
            "autoWidth": false,
            "searching": false
        });
    });
</script>

</body>
</html>
