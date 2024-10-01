<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        .register-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
        }

        .register-form {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }

        .register-form h2 {
            margin-bottom: 25px;
            text-align: center;
        }

        .form-group label {
            font-weight: 600;
        }

        .password-container {
            position: relative;
        }

        .password-container input[type="password"],
        .password-container input[type="text"] {
            padding-right: 40px; /* Space for the eye icon */
        }

        .password-container .bi-eye,
        .password-container .bi-eye-slash {
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            cursor: pointer;
            font-size: 1.2rem;
            color: #6c757d;
        }

        .btn-primary {
            background-color: #007bff;
            border: none;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .text-muted {
            color: #6c757d;
        }

        .form-check-inline {
            display: inline-flex;
            align-items: center;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="register-container">
    <form class="register-form" action="register" method="POST">
        <h2>Register</h2>
        <p style="color: red;">${ss}</p>

        <div class="row">
            <!-- Bên trái -->
            <div class="col-md-6">
                <div class="form-group">
                    <label for="fullname">Full Name:</label>
                    <input type="text" class="form-control" id="fullname" name="fullname" value="${param.fullname}"
                           required>
                    <p style="color: red"> ${fullname_too_long} </p>


                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" value="${param.email}" required>
                    <% if (request.getAttribute("email_not_match") != null) { %>
                    <p style="color:red;"><%= request.getAttribute("email_not_match") %>
                    </p>
                    <% } %>
                    <% if (request.getAttribute("email_exits") != null) { %>
                    <p style="color:red;"><%= request.getAttribute("email_exits") %>
                    </p>
                    <% } %>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="${param.phone}" required>
                    <% if (request.getAttribute("phone_exits") != null) { %>
                    <p style="color:red;"><%= request.getAttribute("phone_exits") %>
                    </p>
                    <% } %>
                    <% if (request.getAttribute("phone_not_match") != null) { %>
                    <p style="color:red;"><%= request.getAttribute("phone_not_match") %>
                    </p>
                    <% } %>
                </div>
                <div class="form-group">
                    <label>Gender:</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="male"
                               value="1" ${gender == '1' ? 'checked' : ''}>
                        <label class="form-check-label" for="male">Male</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="female"
                               value="0" ${gender == '0' ? 'checked' : ''}>
                        <label class="form-check-label" for="female">Female</label>
                    </div>
                    <p style="color: red"> ${gender_null} </p>
                </div>


            </div>

            <!-- Bên phải -->
            <div class="col-md-6">
                <div class="form-group">
                    <label for="password">Password:</label>
                    <div class="password-container">
                        <input type="password" class="form-control" id="password" name="password"
                               value="${param.password}" required>
                        <i class="bi bi-eye-slash" id="togglePasswordIcon" onclick="togglePassword()"></i>
                    </div>
                    <p style="color: red"> ${password_too_long} </p>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password:</label>
                    <div class="password-container">
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                               value="${param.confirmPassword}" required>
                        <i class="bi bi-eye-slash" id="toggleConfirmPasswordIcon" onclick="toggleConfirmPassword()"></i>
                    </div>
                    <p style="color: red"> ${Password_not_match} </p>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Address</label>
                    <div class="password-container">
                        <input type="text" class="form-control" id="Address" name="address" value="${param.address}"
                               required>

                    </div>
                    <p style="color: red"> ${address_too_long} </p>
                </div>


            </div>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Register</button>

        <div class="text-center mt-3">
            <p class="text-muted">Already have an account? <a href="Login.jsp">Login here</a></p>
        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    function togglePassword() {
        var passwordField = document.getElementById("password");
        var toggleIcon = document.getElementById("togglePasswordIcon");

        if (passwordField.type === "password") {
            passwordField.type = "text";
            toggleIcon.classList.remove("bi-eye-slash");
            toggleIcon.classList.add("bi-eye");
        } else {
            passwordField.type = "password";
            toggleIcon.classList.remove("bi-eye");
            toggleIcon.classList.add("bi-eye-slash");
        }
    }

    function toggleConfirmPassword() {
        var confirmPasswordField = document.getElementById("confirmPassword");
        var toggleConfirmIcon = document.getElementById("toggleConfirmPasswordIcon");

        if (confirmPasswordField.type === "password") {
            confirmPasswordField.type = "text";
            toggleConfirmIcon.classList.remove("bi-eye-slash");
            toggleConfirmIcon.classList.add("bi-eye");
        } else {
            confirmPasswordField.type = "password";
            toggleConfirmIcon.classList.remove("bi-eye");
            toggleConfirmIcon.classList.add("bi-eye-slash");
        }
    }
</script>

</body>
</html>
