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
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0px 6px 20px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }

        .login-form h2 {
            margin-bottom: 30px;
            text-align: center;
            font-size: 28px;
            color: #007bff;
        }

        .form-group label {
            font-weight: 600;
            font-size: 14px;
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
            padding: 10px;
            font-size: 16px;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .text-muted {
            color: #6c757d;
            font-size: 14px;
        }

        .login-form p {
            color: red;
            font-weight: bold;
        }

        .login-form input[type="text"],
        .login-form input[type="password"] {
            border-radius: 8px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="login-container">
    <form class="login-form" action="forgetPassword" method="POST">
        <h2>Forget Password</h2>
        <p>${ss}</p>

        <div class="form-group">
            <label for="Email">Email:</label>
            <input type="Email" class="form-control" id="Email" name="Email" value="${param.Email}" required>
        </div>
        <p style="color: red;">${emailNull}</p>


        <button type="submit" class="btn btn-primary btn-block">Submit</button>

        <br>

        <div class="text-center mt-3">
            <a href="Login.jsp" class="text-primary">Back to Login</a>
        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
