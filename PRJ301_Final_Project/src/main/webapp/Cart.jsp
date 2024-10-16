<%@ page import="Models.CartItem" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Map<String, CartItem> cart = (Map<String, CartItem>) request.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Shopping Cart</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Your Shopping Cart</h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${cart != null && !cart.isEmpty()}">
                        <c:forEach var="item" items="${cart.values()}">
                            <tr>
                                <td>${item.getProduct().getProductName()}</td>
                                <td>
                                    <form action="Cart" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="decrease">
                                        <input type="hidden" name="productID" value="${item.getProduct().getProductID()}">
                                        <button type="submit" class="btn btn-secondary btn-sm">-</button>
                                    </form>
                                    ${item.getQuantity()}
                                    <form action="Cart" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="increase">
                                        <input type="hidden" name="productID" value="${item.getProduct().getProductID()}">
                                        <button type="submit" class="btn btn-secondary btn-sm">+</button>
                                    </form>
                                </td>
                                <td>${item.getProduct().getProductPrice()} VND</td>
                                <td>${item.getQuantity() * item.getProduct().getProductPrice()} VND</td>
                                <td>
                                    <form action="Cart" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="productID" value="${item.getProduct().getProductID()}">
                                        <button type="submit" class="btn btn-danger">Remove</button>
                                    </form>
                                    <form action="Buy" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="Buy">
                                        <input type="hidden" name="productID" value="${item.getProduct().getProductID()}">
                                        <input type="hidden" name="productQuantity" value="${item.getQuantity()}">
                                        <button type="submit" class="btn btn-success">Buy</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${cart == null || cart.isEmpty()}">
                        <tr>
                            <td colspan="5" class="text-center">Your cart is empty</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            <a href="<%=request.getContextPath()%>/Home" class="btn btn-primary">Back to Home</a>
            <a href="<%=request.getContextPath()%>/Buy/AllCart" class="btn btn-danger">Buy All</a> 
        </div>
    </body>
</html>
