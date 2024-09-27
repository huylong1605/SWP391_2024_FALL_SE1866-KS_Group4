<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/15/2024
  Time: 5:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        .login-form h2 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="login-container">
    <form class="login-form" action="login" method="POST">
        <h2 class="text-center">Login</h2>
        <p style="color: red"> ${ss} </p>
        <div class="form-group">
            <label for="Email">Email:</label>
            <input type="text" class="form-control" id="Email" name="Email" value="${Email}" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password"value="${password}" required>
            <br>
            <div class="input-group-append">
                <a href="checkPhone.jsp" >Forget Password</a>
            </div>
        </div>
        <%--<p style="color: red"> ${role} </p>--%>
        <p style="color: red"> ${message1} </p>
        <button type="submit" class="btn btn-primary btn-block">Login</button>
        <div class="text-center mt-3">
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
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

