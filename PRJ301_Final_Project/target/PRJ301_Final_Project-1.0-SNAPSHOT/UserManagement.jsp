<%-- 
    Document   : UserManagement
    Created on : Jul 7, 2024, 9:52:24 AM
    Author     : NHAT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.profileLink').click(function (event) {
                    event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
                    $('#Content').load('<%=request.getContextPath()%>/User/Profile');
                });

                $('.userHistoryOrderLink').click(function (event) {
                    event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
                    $('#Content').load('<%=request.getContextPath()%>/User/UserHistoryOrder');
                });

            <%String action = (String) request.getAttribute("action");
                if (action != null) {
                    if (action.equals("Profile")) {%>
                $('#Content').load('<%=request.getContextPath()%>/User/Profile');
            <%}
                }%>
            });
        </script>
    </head>
    <body>
        <jsp:include page="Header.jsp"></jsp:include>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <div class="container-fluid">
                        <div class="row">
                            <a href="#" class="profileLink">Profile</a>
                        </div>
                        <div class="row">
                            <a href="#" class="userHistoryOrderLink">History Order</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-10" id="Content">
                    <!-- Nội dung sẽ được tải vào đây -->
                </div>
            </div>
        </div>

    </body>
</html>
