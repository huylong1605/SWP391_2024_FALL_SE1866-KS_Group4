<%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 9/27/2024
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <title>Kider - Preschool Website Template</title>
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <meta content="" name="keywords">
  <meta content="" name="description">

  <!-- Favicon -->
  <link href="img/favicon.ico" rel="icon">

  <!-- Google Web Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Inter:wght@600&family=Lobster+Two:wght@700&display=swap" rel="stylesheet">

  <!-- Icon Font Stylesheet -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

  <!-- Libraries Stylesheet -->
  <link href="${pageContext.request.contextPath}/lib/animate/animate.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

  <!-- Customized Bootstrap Stylesheet -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

  <!-- Template Stylesheet -->
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

</head>
<body>
<div class="container-xxl bg-white p-0">
  <!-- Spinner Start -->
  <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
    <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  <!-- Spinner End -->


  <!-- Navbar Start -->
  <%@ include file="/Views/common/header.jsp" %>
  <!-- Navbar End -->


  <!-- Carousel Start -->
  <div class="container-fluid p-0 mb-5">
    <div class="owl-carousel header-carousel position-relative">
      <div class="owl-carousel-item position-relative">
        <img class="img-fluid" src="${pageContext.request.contextPath}/img/carousel-1.jpg" alt="">
        <div class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center" style="background: rgba(0, 0, 0, .2);">
          <div class="container">
            <div class="row justify-content-start">
              <div class="col-10 col-lg-8">
                <h1 class="display-2 text-white mb-4">The Best Kindergarten School For Your Child</h1>
                <p class="fs-5 fw-medium text-white mb-4 pb-2">Vero elitr justo clita lorem. Ipsum dolor at sed stet sit diam no. Kasd rebum ipsum et diam justo clita et kasd rebum sea elitr.</p>
                <a href="" class="btn btn-primary rounded-pill py-sm-3 px-sm-5 me-3">Learn More</a>
                <a href="" class="btn btn-dark rounded-pill py-sm-3 px-sm-5">Our Classes</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Carousel End -->


  <!-- Facilities Start -->
  <div class="container-xxl py-5">
    <div class="container">
      <div class="text-center mx-auto mb-5" style="max-width: 600px;">
        <h1 class="mb-3">School Facilities</h1>
        <p>Eirmod sed ipsum dolor sit rebum labore magna erat. Tempor ut dolore lorem kasd vero ipsum sit eirmod sit. Ipsum diam justo sed rebum vero dolor duo.</p>
      </div>
      <div class="row g-4">
        <div class="col-lg-3 col-sm-6">
          <div class="facility-item">
            <div class="facility-icon bg-primary">
              <span class="bg-primary"></span>
              <i class="fa fa-bus-alt fa-3x text-primary"></i>
              <span class="bg-primary"></span>
            </div>
            <div class="facility-text bg-primary">
              <h3 class="text-primary mb-3">School Bus</h3>
              <p class="mb-0">Eirmod sed ipsum dolor sit rebum magna erat lorem kasd vero ipsum sit</p>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6">
          <div class="facility-item">
            <div class="facility-icon bg-success">
              <span class="bg-success"></span>
              <i class="fa fa-futbol fa-3x text-success"></i>
              <span class="bg-success"></span>
            </div>
            <div class="facility-text bg-success">
              <h3 class="text-success mb-3">Playground</h3>
              <p class="mb-0">Eirmod sed ipsum dolor sit rebum magna erat lorem kasd vero ipsum sit</p>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6">
          <div class="facility-item">
            <div class="facility-icon bg-warning">
              <span class="bg-warning"></span>
              <i class="fa fa-home fa-3x text-warning"></i>
              <span class="bg-warning"></span>
            </div>
            <div class="facility-text bg-warning">
              <h3 class="text-warning mb-3">Healthy Canteen</h3>
              <p class="mb-0">Eirmod sed ipsum dolor sit rebum magna erat lorem kasd vero ipsum sit</p>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-sm-6">
          <div class="facility-item">
            <div class="facility-icon bg-info">
              <span class="bg-info"></span>
              <i class="fa fa-chalkboard-teacher fa-3x text-info"></i>
              <span class="bg-info"></span>
            </div>
            <div class="facility-text bg-info">
              <h3 class="text-info mb-3">Positive Learning</h3>
              <p class="mb-0">Eirmod sed ipsum dolor sit rebum magna erat lorem kasd vero ipsum sit</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Facilities End -->


  <!-- About Start -->
  <div class="container-xxl py-5">
    <div class="container">
      <div class="row g-5 align-items-center">
        <div class="col-lg-6">
          <h1 class="mb-4">Learn More About Our Work And Our Cultural Activities</h1>
          <p>Tempor erat elitr rebum at clita. Diam dolor diam ipsum sit. Aliqu diam amet diam et eos. Clita erat ipsum et lorem et sit, sed stet lorem sit clita duo justo magna dolore erat amet</p>
          <p class="mb-4">Stet no et lorem dolor et diam, amet duo ut dolore vero eos. No stet est diam rebum amet diam ipsum. Clita clita labore, dolor duo nonumy clita sit at, sed sit sanctus dolor eos, ipsum labore duo duo sit no sea diam. Et dolor et kasd ea. Eirmod diam at dolor est vero nonumy magna.</p>
          <div class="row g-4 align-items-center">
            <div class="col-sm-6">
              <a class="btn btn-primary rounded-pill py-3 px-5" href="">Read More</a>
            </div>
            <div class="col-sm-6">
              <div class="d-flex align-items-center">
                <img class="rounded-circle flex-shrink-0" src="${pageContext.request.contextPath}/img/user.jpg" alt="" style="width: 45px; height: 45px;">
                <div class="ms-3">
                  <h6 class="text-primary mb-1">Jhon Doe</h6>
                  <small>CEO & Founder</small>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-6 about-img">
          <div class="row">
            <div class="col-12 text-center">
              <img class="img-fluid w-75 rounded-circle bg-light p-3" src="${pageContext.request.contextPath}/img/about-1.jpg" alt="">
            </div>
            <div class="col-6 text-start" style="margin-top: -150px;">
              <img class="img-fluid w-100 rounded-circle bg-light p-3" src="${pageContext.request.contextPath}/img/about-2.jpg" alt="">
            </div>
            <div class="col-6 text-end" style="margin-top: -150px;">
              <img class="img-fluid w-100 rounded-circle bg-light p-3" src="${pageContext.request.contextPath}/img/about-3.jpg" alt="">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- About End -->


  <!-- Team Start -->
  <div class="container-xxl py-5">
    <div class="container">
      <div class="text-center mx-auto mb-5" style="max-width: 600px;">
        <h1 class="mb-3">Popular Teachers</h1>
        <p>Eirmod sed ipsum dolor sit rebum labore magna erat. Tempor ut dolore lorem kasd vero ipsum sit
          eirmod sit. Ipsum diam justo sed rebum vero dolor duo.</p>
      </div>
      <div class="row g-4">
        <div class="col-lg-4 col-md-6">
          <div class="team-item position-relative">
            <img class="img-fluid rounded-circle w-75" src="${pageContext.request.contextPath}/img/team-1.jpg" alt="">
            <div class="team-text">
              <h3>Full Name</h3>
              <p>Designation</p>
              <div class="d-flex align-items-center">
                <a class="btn btn-square btn-primary mx-1" href=""><i class="fab fa-facebook-f"></i></a>
                <a class="btn btn-square btn-primary  mx-1" href=""><i class="fab fa-twitter"></i></a>
                <a class="btn btn-square btn-primary  mx-1" href=""><i class="fab fa-instagram"></i></a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="team-item position-relative">
            <img class="img-fluid rounded-circle w-75" src="${pageContext.request.contextPath}/img/team-2.jpg" alt="">
            <div class="team-text">
              <h3>Full Name</h3>
              <p>Designation</p>
              <div class="d-flex align-items-center">
                <a class="btn btn-square btn-primary mx-1" href=""><i class="fab fa-facebook-f"></i></a>
                <a class="btn btn-square btn-primary  mx-1" href=""><i class="fab fa-twitter"></i></a>
                <a class="btn btn-square btn-primary  mx-1" href=""><i class="fab fa-instagram"></i></a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="team-item position-relative">
            <img class="img-fluid rounded-circle w-75" src="${pageContext.request.contextPath}/img/team-3.jpg" alt="">
            <div class="team-text">
              <h3>Full Name</h3>
              <p>Designation</p>
              <div class="d-flex align-items-center">
                <a class="btn btn-square btn-primary mx-1" href=""><i class="fab fa-facebook-f"></i></a>
                <a class="btn btn-square btn-primary  mx-1" href=""><i class="fab fa-twitter"></i></a>
                <a class="btn btn-square btn-primary  mx-1" href=""><i class="fab fa-instagram"></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Team End -->


  <!-- Testimonial Start -->
  <div class="container-xxl py-5">
    <div class="container">
      <div class="text-center mx-auto mb-5" style="max-width: 600px;">
        <h1 class="mb-3">Our Clients Say!</h1>
        <p>Eirmod sed ipsum dolor sit rebum labore magna erat. Tempor ut dolore lorem kasd vero ipsum sit eirmod sit. Ipsum diam justo sed rebum vero dolor duo.</p>
      </div>
      <div class="owl-carousel testimonial-carousel">
        <div class="testimonial-item bg-light rounded p-5">
          <p class="fs-5">Tempor stet labore dolor clita stet diam amet ipsum dolor duo ipsum rebum stet dolor amet diam stet. Est stet ea lorem amet est kasd kasd erat eos</p>
          <div class="d-flex align-items-center bg-white me-n5" style="border-radius: 50px 0 0 50px;">
            <img class="img-fluid flex-shrink-0 rounded-circle" src="${pageContext.request.contextPath}/img/testimonial-1.jpg" style="width: 90px; height: 90px;">
            <div class="ps-3">
              <h3 class="mb-1">Client Name</h3>
              <span>Profession</span>
            </div>
            <i class="fa fa-quote-right fa-3x text-primary ms-auto d-none d-sm-flex"></i>
          </div>
        </div>
        <div class="testimonial-item bg-light rounded p-5">
          <p class="fs-5">Tempor stet labore dolor clita stet diam amet ipsum dolor duo ipsum rebum stet dolor amet diam stet. Est stet ea lorem amet est kasd kasd erat eos</p>
          <div class="d-flex align-items-center bg-white me-n5" style="border-radius: 50px 0 0 50px;">
            <img class="img-fluid flex-shrink-0 rounded-circle" src="${pageContext.request.contextPath}/img/testimonial-2.jpg" style="width: 90px; height: 90px;">
            <div class="ps-3">
              <h3 class="mb-1">Client Name</h3>
              <span>Profession</span>
            </div>
            <i class="fa fa-quote-right fa-3x text-primary ms-auto d-none d-sm-flex"></i>
          </div>
        </div>
        <div class="testimonial-item bg-light rounded p-5">
          <p class="fs-5">Tempor stet labore dolor clita stet diam amet ipsum dolor duo ipsum rebum stet dolor amet diam stet. Est stet ea lorem amet est kasd kasd erat eos</p>
          <div class="d-flex align-items-center bg-white me-n5" style="border-radius: 50px 0 0 50px;">
            <img class="img-fluid flex-shrink-0 rounded-circle" src="${pageContext.request.contextPath}/img/testimonial-3.jpg" style="width: 90px; height: 90px;">
            <div class="ps-3">
              <h3 class="mb-1">Client Name</h3>
              <span>Profession</span>
            </div>
            <i class="fa fa-quote-right fa-3x text-primary ms-auto d-none d-sm-flex"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Testimonial End -->


  <!-- Footer Start -->
  <%@ include file="/Views/common/footer.jsp" %>
  <!-- Footer End -->


  <!-- Back to Top -->
  <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
</div>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/wow/wow.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/waypoints/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
