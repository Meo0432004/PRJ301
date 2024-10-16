<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <a href="<%=request.getContextPath()%>/Home">Back to Home</a>
                            <h3 class="text-center">Login</h3>
                        </div>
                        <div class="card-body">

                            <c:if test="${not empty requestScope.error}">
                                <div class="alert alert-danger">${requestScope.error}</div>
                            </c:if>
                            <form action="Account" method="post">
                                <div class="form-group">
                                    <label for="username">Enter username:</label>
                                    <input type="text" id="username" name="username" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label for="password">Enter password:</label>
                                    <input type="password" id="password" name="password" class="form-control" required>
                                </div>
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-6">
                                            <input type="submit" class="btn btn-primary btn-block" name="btnLogin" value="Login"/>
                                        </div>
                                        <div class="col-6 text-right">
                                            <a href="<%=request.getContextPath()%>/Account/Register" class="btn btn-secondary btn-block">Register</a>
                                        </div>
                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
