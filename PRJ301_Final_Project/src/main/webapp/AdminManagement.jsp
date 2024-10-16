<%-- 
    Document   : AdminManagement
    Created on : Jul 3, 2024, 10:04:07 PM
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
                $('.categoryLink').click(function (event) {
                    event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
                    $('#Content').load('<%=request.getContextPath()%>/CategoryManagement/CategoryList');
                });

                $(document).on('click', '#categoryEditLink', function (event) {
                    event.preventDefault();
                    var cID = $(this).data('categoryid');
                    $('#Content').load('<%=request.getContextPath()%>/CategoryManagement/CategoryEdit?cID=' + cID);
                });

            <%-- ---------------------------------------------------------- --%>

                $('.productLink').click(function (event) {
                    event.preventDefault();
                    $('#Content').load('<%=request.getContextPath()%>/ProductManagement/ProductList');
                });

                $(document).on('click', '#productEditLink', function (event) {
                    var pID = $(this).data('productid');
                    $('#Content').load('<%=request.getContextPath()%>/ProductManagement/ProductEdit?pID=' + pID);
                });

                $(document).on('click', '#productDeleteLink', function (event) {
                    var pID = $(this).data('productid');
                    $('#Content').load('<%=request.getContextPath()%>/ProductManagement/ProductDelete?pID=' + pID);
                });

            <%-- ---------------------------------------------------------- --%>

                $('.orderLink').click(function (event) {
                    event.preventDefault();
                    $('#Content').load('<%=request.getContextPath()%>/OrderManagement/OrderList');
                });

                $(document).on('click', '#showOrderLink', function (event) {
                    event.preventDefault();
                    var oID = $(this).data('orderid');
                    $('#Content').load('<%=request.getContextPath()%>/OrderManagement/ShowOrderItems?oID=' + oID);
                });

            <%-- ---------------------------------------------------------- --%>

                $('.shippingLink').click(function (event) {
                    event.preventDefault();
                    $('#Content').load('<%=request.getContextPath()%>/ShippingManagement/ShippingList');
                });

            <% String action = (String) request.getAttribute("action");
                if (action != null && action.equals("CategoryList")) {%>
                console.log('Loading CategoryList on page load');
                $('#Content').load('<%=request.getContextPath()%>/CategoryManagement/CategoryList');
            <% } else if (action != null && action.equals("ProductList")) {%>
                console.log('Loading ProductList');
                $('#Content').load('<%=request.getContextPath()%>/ProductManagement/ProductList');
            <%} else if (action != null && action.equals("OrderList")) {%>
                console.log('Loading Order');
                $('#Content').load('<%=request.getContextPath()%>/OrderManagement/OrderList');
            <%}%>
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
                            <a href="#" class="productLink">Product</a>
                        </div>
                        <div class="row">
                            <a href="#" class="categoryLink">Category</a>
                        </div>
                        <div class="row">
                            <a href="#" class="orderLink">Order</a>
                        </div>
                        <div class="row">
                            <a href="#" class="shippingLink">Shipping</a>
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