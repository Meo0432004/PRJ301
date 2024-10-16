<%@page import="java.net.URLEncoder"%>
<%@page import="Models.Product"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Main Home</title>
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
            .cart-badge {
                position: absolute;
                top: 0;
                right: 0;
                transform: translate(50%, -50%);
                background-color: red;
                color: white;
                border-radius: 50%;
                padding: 5px 10px;
                font-size: 12px;
            }
        </style>
        <script>
            function showAddToCartAlert() {
                alert("Added to cart successfully!");
            }
        </script>
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="Header.jsp"></jsp:include>

            <div class="container-fluid">
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
                    <h4 class="mt-4">Newest Product</h4>
                    <div class="card product-card">
                        <c:set var="newP" value="${requestScope.newP}"/>
                        <c:if test="${not empty newP}">
                            <img src="<%=request.getContextPath()%>${newP.productImage}" class="card-img-top" alt="${newP.productName}">
                            <div class="card-body">
                                <h5 class="card-title"><a href="<%=request.getContextPath()%>/Product/Detail?pID=${newP.productID}">${newP.productName}</a></h5>
                                <p class="card-text">${newP.productDescription}</p>
                                <p class="price">${newP.formattedPrice} VND</p>
                                <p>Availability: ${newP.productQuantity} units</p>
                                <!-- Buy Button -->
                                <form action="<%=request.getContextPath()%>/Buy" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="buy">
                                    <input type="hidden" name="productID" value="${newP.productID}">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn btn-primary">Buy</button>
                                </form>
                            </c:if>
                            <!-- Add to Cart Button -->
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
                            <form action="Cart" method="post" class="d-inline">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productId" value="${newP.productID}">
                                <input type="hidden" name="currentURL" value="<%= currentURL%>"/>
                                <button type="submit" class="btn btn-outline-primary">Add to Cart</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="row">
                        <c:forEach var="product" items="${requestScope.listP}">
                            <div class="col-md-4">
                                <div class="card product-card">
                                    <img src="<%=request.getContextPath()%>${product.productImage}" class="card-img-top" alt="${product.productName}">
                                    <div class="card-body">
                                        <h5 class="card-title"><a href="<%=request.getContextPath()%>/Product/Detail?pID=${product.productID}">${product.productName}</a></h5>
                                        <p class="card-text">${product.productDescription}</p>
                                        <p class="price">${product.formattedPrice} VND</p>
                                        <p>Availability: ${product.productQuantity} units</p>
                                        <form action="Cart" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="productID" value="${product.productID}">
                                            <input type="hidden" name="currentURL" value="<%= currentURL%>"/>
                                            <button type="submit" class="btn btn-warning">Add to Cart</button>
                                        </form>
                                        <form action="Buy" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="Buy">
                                            <input type="hidden" name="productID" value="${product.productID}">
                                            <input type="hidden" name="productQuantity" value="1">
                                            <button type="submit" class="btn btn-primary">Buy</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
