<%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 10/2/2024
  Time: 10:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Basic -->
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <!-- Site Metas -->
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>

    <title>Brighton</title>

    <!-- slider stylesheet -->
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.1.3/assets/owl.carousel.min.css"/>

    <!-- bootstrap core css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>

    <!-- fonts style -->
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700|Poppins:400,700|Roboto:400,700&display=swap"
          rel="stylesheet"/>

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/style-Homepage.css" rel="stylesheet"/>
    <!-- responsive style -->
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet"/>

    <style>
        .parent-items button {
            margin: 12px 0 12px 0;
            height: 60px;
            background-color: green;
            margin-left: 20px;
            border: 1px solid white;
        }

        .parent-items button:hover {
            margin: 12px 0 12px 0;
            height: 60px;
            margin-left: 20px;
            transform: scale(1.2);
            background-color: #fd4d4d;
        }
    </style>


</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="hero_area">
    <!-- slider section -->
    <section class="slider_section position-relative">
        <div class="container row">
            <div id="carouselExampleIndicators" class="carousel slide col-md-6" data-ride="carousel" style="padding-top: 30px">
                <div class="parent-items">
                    <button class="btn btn-primary" style="width: 40%" onclick="openStudentModal()">
                        View Child Information
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/student/evaluations/list">Student Evaluation</a>
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/parent-notification">NEWS</a>
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/scheduleStudent?parentId=${sessionScope.user.userID}">View Schedule Of Child </a>
                    </button>

                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/parent-term">View Semester</a>
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/registerStudent.jsp">Register New Child</a>
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/parent-subject">View Subjects</a>
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/view-applications">Send/View Application</a>
                    </button>
                    <button class="btn btn-primary" style="width: 40%">
                        <a class="text-light" href="${pageContext.request.contextPath}/Views/Parent/viewChildAttendance?userId=${sessionScope.user.userID}">View Attendance Of Child</a>
                    </button>
                </div>
            </div>
            <div class=" col-md-6">
                <img src="${pageContext.request.contextPath}/img/slider-bg.jpg" style="width: 800px;" alt="">
            </div>
        </div>
    </section>
    <!-- end slider section -->
<!-- Modal -->
<div class="modal fade" id="studentModal" tabindex="-1" aria-labelledby="studentModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="studentModalLabel">View Child Information</h5>
            </div>
            <div class="modal-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date of Birth</th>
                            <th>Gender</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${listChild}">
                            <tr>
                                <td>${student.name}</td>
                                <td>${student.dob}</td>
                                <td>${student.gender ? 'Male' : 'Female'}</td>
                                <td>${student.address}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



    <!-- Success Modal - Visible only if registrationSuccess is set -->
        <c:if test="${not empty registrationSuccess}">
            <div class="modal fade show" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-modal="true" role="dialog" style="display: block;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="successModalLabel">Registration Status</h5>
                        </div>
                        <div class="modal-body">
                            ${registrationSuccess}
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="window.location.href='${pageContext.request.contextPath}/Views/HomePage/HomePage.jsp'">OK</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
</div>


<!-- about section -->
<section class="about_section ">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="img-box">
                    <img src="${pageContext.request.contextPath}/img/about-img.jpg" alt="">
                </div>
            </div>
            <div class="col-md-5 col-lg-4">
                <div class="detail-box">
                    <div class="heading_container">
                        <h2 style="font-family: 'Roboto', Helvetica, Arial, sans-serif">
                            A few things about us
                        </h2>
                    </div>
                    <p>
                        The Kindergarten Management System is a comprehensive software solution designed to optimize management operations in kindergartens. The system supports everything from managing student and teacher records, timetables, to tracking and updating childrens development progress. With a friendly and easy-to-use interface, the system helps automate administrative management processes such as attendance and timetables.
                    </p>
                    <div>
                        <a href="">
                            Read More
                        </a>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
<script>
    function openStudentModal() {
        new bootstrap.Modal(document.getElementById('studentModal')).show();
    }
</script>

<%@ include file="/Views/common/footer.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</body>
</html>
