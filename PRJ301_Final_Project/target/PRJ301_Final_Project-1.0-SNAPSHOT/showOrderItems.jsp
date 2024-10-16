<%-- 
    Document   : showOrderItems
    Created on : Jul 6, 2024, 4:53:11 PM
    Author     : NHAT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Order Items</h2>
        <a href="<%=request.getContextPath()%>/OrderManagement/BackToOrder">Order</a>
        <c:if test="${requestScope.listOrIt==null}">
            <h3>There is no Order Items</h3>
        </c:if>
        <c:if  test="${requestScope.listOrIt!=null}">
            <table border="1px">
                <tr>
                    <th>Order Items ID</th>
                    <th>Order ID</th>
                    <th>Product Name</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                </tr>
                <c:forEach var="OrIt" items="${requestScope.listOrIt}">
                    <tr>
                        <td>${OrIt.orderItemID}</td>
                        <td>${OrIt.orderID}</td>
                        <td>${OrIt.productName}</td>
                        <td>${OrIt.unitPrice}</td>
                        <td>${OrIt.quantity}</td>
                        <td>${OrIt.unitPrice * OrIt.quantity}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
