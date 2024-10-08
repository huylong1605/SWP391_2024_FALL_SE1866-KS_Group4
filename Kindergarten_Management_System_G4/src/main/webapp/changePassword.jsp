<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
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
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="register-container">
    <form class="register-form" action="changePassword" method="POST">
        <h2>Change Password</h2>

        <c:if test="${not empty oldNewPassFalse}">
            <p style="color:red;">${oldNewPassFalse}</p>
        </c:if>
        <c:if test="${not empty notMatch}">
            <p style="color:red;">${notMatch}</p>
        </c:if>
        <c:if test="${not empty passwordFalse}">
            <p style="color:red;">${passwordFalse}</p>
        </c:if>
        <c:if test="${not empty emailFalse}">
            <p style="color:red;">${emailFalse}</p>
        </c:if>
        <c:if test="${not empty lengthFalse}">
            <p style="color:red;">${lengthFalse}</p>
        </c:if>
        <c:if test="${not empty updateSuccessful}">
            <p style="color:lawngreen;">${updateSuccessful}</p>
        </c:if>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${sessionScope.user.email}" required>
        </div>

        <br>
        <div class="form-group">
            <label for="oldPassword">Old Password:</label>
            <div class="password-container">
                <input type="password" class="form-control" id="oldPassword" name="oldPassword" value="${param.oldPassword}" required
                       oninvalid="this.setCustomValidity('Please enter your old password')"
                       oninput="this.setCustomValidity('')">
                <i class="bi bi-eye-slash" id="toggleOldPasswordIcon" onclick="toggleOldPassword()"></i>
            </div>
        </div>

        <br>
        <div class="form-group">
            <label for="newPassword">New Password:</label>
            <div class="password-container">
                <input type="password" class="form-control" id="newPassword" name="newPassword" required
                       oninvalid="this.setCustomValidity('Please enter your new password')"
                       oninput="this.setCustomValidity('')">
                <i class="bi bi-eye-slash" id="toggleNewPasswordIcon" onclick="toggleNewPassword()"></i>
            </div>
        </div>

        <br>
        <div class="form-group">
            <label for="confirmNewPassword">Confirm New Password:</label>
            <div class="password-container">
                <input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword" required
                       oninvalid="this.setCustomValidity('Please confirm your new password')"
                       oninput="this.setCustomValidity('')">
                <i class="bi bi-eye-slash" id="toggleConfirmNewPasswordIcon" onclick="toggleConfirmNewPassword()"></i>
            </div>
        </div>

        <br>
        <button type="submit" class="btn btn-primary btn-block">Change Password</button>

        <div class="text-center mt-3">
            <p class="text-muted">Forgot your password? <a href="forgotPassword.jsp">Forget Password here</a></p>
        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    function toggleOldPassword() {
        var passwordField = document.getElementById("oldPassword");
        var toggleIcon = document.getElementById("toggleOldPasswordIcon");

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

    function toggleNewPassword() {
        var passwordField = document.getElementById("newPassword");
        var toggleIcon = document.getElementById("toggleNewPasswordIcon");

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

    function toggleConfirmNewPassword() {
        var passwordField = document.getElementById("confirmNewPassword");
        var toggleIcon = document.getElementById("toggleConfirmNewPasswordIcon");

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
