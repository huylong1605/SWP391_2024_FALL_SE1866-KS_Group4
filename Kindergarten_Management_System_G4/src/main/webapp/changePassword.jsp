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
    <style>
        .form-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
        }

        .form-box {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }

        .form-box h2 {
            margin-bottom: 25px;
            text-align: center;
        }

        .alert {
            margin-bottom: 20px;
        }

        .password-container {
            position: relative;
        }

        .password-container input[type="password"],
        .password-container input[type="text"] {
            padding-right: 40px;
        }

        .password-container .bi-eye,
        .password-container .bi-eye-slash {
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            cursor: pointer;
        }

        .btn-primary {
            background-color: #007bff;
            border: none;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container form-container">
    <div class="form-box">
        <h2>Change Password</h2>

        <!-- Hiển thị thông báo lỗi/success -->
        <c:if test="${not empty oldNewPassFalse}">
            <div class="alert alert-danger">${oldNewPassFalse}</div>
        </c:if>
        <c:if test="${not empty notMatch}">
            <div class="alert alert-danger">${notMatch}</div>
        </c:if>
        <c:if test="${not empty passwordFalse}">
            <div class="alert alert-danger">${passwordFalse}</div>
        </c:if>
        <c:if test="${not empty emailFalse}">
            <div class="alert alert-danger">${emailFalse}</div>
        </c:if>
        <c:if test="${not empty lengthFalse}">
            <div class="alert alert-danger">${lengthFalse}</div>
        </c:if>
        <c:if test="${not empty updateSuccessful}">
            <div class="alert alert-success">${updateSuccessful}</div>
        </c:if>

        <!-- Form đổi mật khẩu -->
        <form action="changePassword" method="POST">
            <div class="form-group">
                <label for="email">email: <span style="color: red;">*</span></label>

                <input type="email" class="form-control" id="email" name="email" value="${sessionScope.user.email}" required>
            </div>

            <div class="form-group">
                <label for="oldPassword">Old Password: <span style="color: red;">*</span></label>

                <div class="password-container">
                    <input type="password" class="form-control" id="oldPassword" name="oldPassword"  required>
                    <i class="bi bi-eye-slash" id="toggleOldPasswordIcon" onclick="toggleOldPassword()"></i>
                </div>
            </div>

            <div class="form-group">
                <label for="newPassword">New Password: <span style="color: red;">*</span></label>

                <div class="password-container">
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                    <i class="bi bi-eye-slash" id="toggleNewPasswordIcon" onclick="toggleNewPassword()"></i>
                </div>
            </div>

            <div class="form-group">
                <label for="confirmNewPassword">confirmNewPassword: <span style="color: red;">*</span></label>
                <
                <div class="password-container">
                    <input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword" required>
                    <i class="bi bi-eye-slash" id="toggleConfirmNewPasswordIcon" onclick="toggleConfirmNewPassword()"></i>
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-block mb-3">Change Password</button>
        </form>
        <br>
        <a href="viewprofile" class="btn btn-secondary btn-block" role="button">View Profile</a>
    </div>
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
        passwordField.type = (passwordField.type === "password") ? "text" : "password";
        toggleIcon.classList.toggle("bi-eye");
        toggleIcon.classList.toggle("bi-eye-slash");
    }

    function toggleNewPassword() {
        var passwordField = document.getElementById("newPassword");
        var toggleIcon = document.getElementById("toggleNewPasswordIcon");
        passwordField.type = (passwordField.type === "password") ? "text" : "password";
        toggleIcon.classList.toggle("bi-eye");
        toggleIcon.classList.toggle("bi-eye-slash");
    }

    function toggleConfirmNewPassword() {
        var passwordField = document.getElementById("confirmNewPassword");
        var toggleIcon = document.getElementById("toggleConfirmNewPasswordIcon");
        passwordField.type = (passwordField.type === "password") ? "text" : "password";
        toggleIcon.classList.toggle("bi-eye");
        toggleIcon.classList.toggle("bi-eye-slash");
    }
</script>
</body>
</html>
