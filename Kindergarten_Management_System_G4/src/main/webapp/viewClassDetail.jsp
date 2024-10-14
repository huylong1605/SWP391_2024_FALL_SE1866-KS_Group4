<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Class Detail</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        .card-title {
            font-size: 1.8rem;
            font-weight: bold;
        }

        .card-text {
            font-size: 1rem;
            margin-bottom: 10px;
        }

        .btn-back {
            margin-right: 10px;
        }

        .icon-text {
            display: flex;
            align-items: center;
        }

        .icon-text i {
            margin-right: 8px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <h2 class="text-center mb-4">Class Detail</h2>

            <%--<c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>--%>

            <c:if test="${not empty classesDAL}">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-primary">${classesDAL.className}</h5>
                        <hr>
                        <p class="card-text icon-text">
                            <i class="fas fa-graduation-cap text-secondary"></i>
                            <strong>Level:</strong> ${classesDAL.classLevelName}
                        </p>
                        <p class="card-text icon-text">
                            <i class="fas fa-user-tie text-secondary"></i>
                            <strong>Teacher:</strong> ${classesDAL.fullname}
                        </p>
                        <p class="card-text icon-text">
                            <i class="fas fa-door-closed text-secondary"></i>
                            <strong>Room:</strong> ${classesDAL.roomNumber}
                        </p>
                        <p class="card-text icon-text">
                            <i class="fas fa-info-circle text-secondary"></i>
                            <strong>Description:</strong> ${classesDAL.description}
                        </p>
                        <p class="card-text icon-text">
                            <i class="fas fa-users text-secondary"></i>
                            <strong>Capacity:</strong> ${classesDAL.capacity}
                        </p>
                        <p class="card-text icon-text">
                            <i class="fas fa-envelope text-secondary"></i>
                            <strong>Email:</strong> ${classesDAL.email}
                        </p>

                        <div class="text-center mt-4">
                            <a href="updateClass?classId=${classesDAL.classId}" class="btn btn-primary btn-back">
                                <i class="fas fa-edit"></i> Edit
                            </a>
                            <a href="listClass" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i> Back to List
                            </a>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="/Views/common/footer.jsp" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
