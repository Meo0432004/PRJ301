<%-- 
    Document   : ProductDetail
    Created on : Jun 18, 2024, 10:30:57 PM
    Author     : NGUYEN LE NHAT - CE181840
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Detail</title>
        <!-- Link Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Link Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
        <style>
            /* Custom styles */
            .product-card {
                margin-bottom: 20px;
            }
            .product-card .card-body {
                padding: 20px;
            }
            .product-card .card-title {
                font-size: 1.4rem;
                font-weight: bold;
            }
            .product-card .card-text {
                margin-top: 10px;
            }
            .product-card .price {
                font-size: 1.2rem;
                font-weight: bold;
                color: #007bff;
                margin-top: 10px;
            }
            .product-card img {
                width: 100%;
                height: 200px;
                object-fit: contain;
                background-color: #f8f9fa;
            }
            .header {
                background-color: #f8f9fa;
                padding: 10px 0;
                margin-bottom: 20px;
            }
            .header img {
                width: 40px;
                height: 40px;
                margin-right: 10px;
            }
            .header .navbar-brand {
                font-size: 1.5rem;
                font-weight: bold;
            }
            .header .search-bar {
                max-width: 100%;
            }
            .header .search-bar input {
                width: 100%;
            }
            .active-category {
                background-color: #007bff;
                color: white !important;
            }
        </style>
        <script>
            function doReview(event) {
                event.preventDefault();
                var isLogin = '<%=session.getAttribute("user") != null %>';
                if (isLogin === 'false') {
                    if (confirm("You need to Login")) {
                        window.location = "<%=request.getContextPath()%>/Account/Login";
                    }
                } else {
                    document.getElementById("commentForm").submit();
                }
            }
        </script>
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="Header.jsp"></jsp:include>

            <div class="container">
                <div class="row">
                    <div class="col-md-3">
                        <h4>Categories</h4>
                        <div class="list-group">
                            <a class="list-group-item list-group-item-action" href="<%=request.getContextPath()%>/Home">
                            <i class="bi bi-house-fill"></i> Home
                        </a>
                        <c:forEach var="category" items="${listC}">
                            <a class="list-group-item list-group-item-action ${tagControl == category.categoryID ? 'active-category' : ''}" href="<%=request.getContextPath()%>/Category?cId=${category.categoryID}">${category.categoryName}</a>
                        </c:forEach>
                    </div>
                </div>

                <div class="col-md-9">
                    <div class="card product-card">
                        <img src="<%=request.getContextPath()%>${productDetail.productImage}" class="card-img-top" alt="${productDetail.productName}">
                        <div class="card-body">
                            <h5 class="card-title">${productDetail.productName}</h5>
                            <p class="card-text">${productDetail.productDescription}</p>
                            <p class="price">${productDetail.formattedPrice} VND</p>
                            <p>Availability: ${productDetail.productQuantity} units</p>
                            <%
                                String url = (String) request.getAttribute("url");
                                String path = request.getContextPath();
                                String query = request.getQueryString();
                                String currentURL = "";
                                if (url == null) {
                                    currentURL = path;
                                } else {
                                    currentURL = url + "?" + query;
                                }
                            %>
                            <form action="<%=request.getContextPath()%>/Cart" method="post" class="d-inline">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productID" value="${productDetail.productID}">
                                <input type="hidden" name="currentURL" value="<%=currentURL%>"/>
                                <button type="submit" class="btn btn-warning">Add to Cart</button>
                            </form>
                            <form action="<%=request.getContextPath()%>/Buy" method="post" class="d-inline">
                                <input type="hidden" name="action" value="Buy">
                                <input type="hidden" name="productID" value="${productDetail.productID}">
                                <input type="hidden" name="productQuantity" value="1">
                                <button type="submit" class="btn btn-primary">Buy</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <h3>Review</h3><br>
                <br>

                <form id="commentForm" action="<%=request.getContextPath()%>/Product" method="post">
                    <input type="hidden" name="action" value="review"/>
                    <input type="hidden" name="productID" value="${productDetail.productID}"/>
                    <input type="hidden" name="username" value="<%=session.getAttribute("user")%>"/>
                    <input type="hidden" name="currentURL" value="<%=currentURL%>"/>
                    <textarea placeholder="Your comment" name="reviewText"></textarea><br>
                    <button type="submit" onclick="doReview(event)">Post</button>
                </form>
            </div>
            <hr><hr>
            <c:if test="${empty requestScope.listR }">
                <h4>There is no review from customer</h4>
            </c:if>
            <c:forEach var="r" items="${requestScope.listR}">
                <div class="row">
                    <textarea readonly>${r.customerUsername}: ${r.reviewText}</textarea>
                </div>
                <hr><hr>
            </c:forEach>

        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
