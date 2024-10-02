<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        .login-form {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        .login-form h2 {
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
        .form-check-label {
            margin-left: 5px;
        }
        .forgot-password {
            text-align: center;
            margin-top: 15px;
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
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<%
    String email ="", pass = "", remember = "";
    Cookie[] cookies = request.getCookies();
    if(cookies != null) {
        for (Cookie cook : cookies) {
            if (cook.getName().equals("cookieEmail")) {
                email = cook.getValue();

            } else if (cook.getName().equals("cookiePass")) {
                pass = cook.getValue();
            } else if (cook.getName().equals("cookieRemember")) {
                remember = cook.getValue();
            }
        }
    }
%>
<div class="login-container">
    <form class="login-form" action="login" method="POST">
        <h2>Login</h2>
        <p style="color: red;">${ss}</p
        <p style="color: greenyellow;">${PasswordUpdate}</p>
        <p style="color:  #49ef49;">${registerSuccessful}</p>

        <div class="form-group">
            <label for="Email">Email:</label>
            <input type="email" class="form-control" id="Email" name="Email" value="<%= email%>" required
                   oninvalid="this.setCustomValidity('Please enter  email address')"
                   oninput="this.setCustomValidity('')">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <div class="password-container">
                <input type="password" class="form-control" id="password" name="password" value="<%= pass%>"  required
                       oninvalid="this.setCustomValidity('Please enter your password')"
                       oninput="this.setCustomValidity('')">
                <i class="bi bi-eye-slash" id="togglePasswordIcon" onclick="togglePassword()"></i>
            </div>
        </div>

        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe" value="1"
                    <%
                        if (remember != null && remember.equals("1")) {
                    %>
                   checked="checked"
                    <%
                        }
                    %>
                   />

            <label class="form-check-label" for="rememberMe">Remember me</label>
        </div>

        <p style="color: red;">${message1}</p>

        <button type="submit" class="btn btn-primary btn-block">Login</button>
      <%--  <div class="text-center mt-3">
            <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid

&redirect_uri=http://localhost:8080/Kindergarten_Management_System/login_Gg

&response_type=code

&client_id=1028444732076-4llkdccstoav2g4bkdf3s75cj86kvl82.apps.googleusercontent.com

&approval_prompt=force" class="btn btn-google btn-block">
                <img src="https://img.icons8.com/color/16/000000/google-logo.png"/> Login with Google
            </a>
        </div>--%>
       <br>
        <!-- Đưa "Forgot Password?" xuống dưới nút "Login" -->
        <div class="forgot-password">
            <a href="forgotPassword.jsp">Forgot Password?</a>
        </div>

        <div class="text-center mt-3">
            <p class="text-muted">Don't have an account? <a href="register.jsp">Register here</a></p>
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
</script>

</body>
</html>
