<%-- 
    Document   : ProductManagement
    Created on : Jul 4, 2024, 8:52:38 PM
    Author     : NHAT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <script>
            function previewImage(event) {
                const fileInput = event.target;
                const preview = document.getElementById('productImagePreview');
                const file = fileInput.files[0];
                const reader = new FileReader();

                reader.onload = function (e) {
                    preview.src = e.target.result;
                };

                if (file) {
                    reader.readAsDataURL(file);
                } else {
                    preview.src = '';
                }
            }
        </script>
    </head>
    <body>
        <h2 id="targetHeader">Product Management</h2>
        <form action="ProductManagement" method="post" enctype="multipart/form-data">
            <c:set var="action" value="${requestScope.action}"/>
            <c:set var="product" value="${requestScope.product}"/>
            <c:if test="${action==null || action=='Add'}">
                Product ID:<input type="text" name="productID"/><br><!-- comment -->
            </c:if>
            <c:if test="${action=='Edit' || action=='Delete'}">
                Product ID:<input type="text" value="${product.productID}" readonly name="productID"/><br><!-- comment -->
            </c:if>
            Category Name:
            <select name="categoryID">
                <c:forEach var="category" items="${requestScope.listC}">
                    <c:choose>
                        <c:when test="${product.categoryID == category.categoryID}">
                            <option selected value="${category.categoryID}">
                                ${category.categoryName}
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="${category.categoryID}">
                                ${category.categoryName}
                            </option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select><br><!-- comment -->
            <c:if test="${action=='Delete'}">
                Product Name:<input type="text" value="${product.productName}" readonly name="productName"/><br><!-- comment -->
                Product Description:<input type="text" value="${product.productDescription}" readonly name="productDescription"><br><!-- comment -->
                Product Price:<input type="number" value="${product.productPrice}" readonly name="productPrice"><br><!-- comment -->
                Product Quantity:<input type="number" value="${product.productQuantity}" readonly name="productQuantity"><br><!-- comment -->
                Product Image:<img id="productImagePreview" src="<%=request.getContextPath()%>${product.productImage}" width="80px" height="80px"/><br>
            </c:if>
            <c:if test="${action!='Delete'}">
                Product Name:<input type="text" value="${product.productName}" name="productName"/><br><!-- comment -->
                Product Description:<input type="text" value="${product.productDescription}" name="productDescription"><br><!-- comment -->
                Product Price:<input type="number" value="${product.productPrice}" name="productPrice"><br><!-- comment -->
                Product Quantity:<input type="number" value="${product.productQuantity}" name="productQuantity"><br><!-- comment -->

                Product Image:<img id="productImagePreview" src="<%=request.getContextPath()%>${product.productImage}" width="80px" height="80px"/>

                <input type="file"  class="form-control-file" onchange="previewImage(event)" name="productImage"><br>
            </c:if>
            <input type="hidden" value="${action}" name="action"/><br>
            <c:choose>
                <c:when test="${action=='Add' || action==null}">
                    <button type="submit">Add</button>
                </c:when>
                <c:when test="${action=='Edit'}">
                    <button type="submit">Edit</button>
                </c:when>
                <c:when test="${action=='Delete'}">
                    <button type="submit">Delete</button>
                </c:when>
            </c:choose>

        </form>

        <table border="1px">
            <tr>
                <th>Product ID</th>
                <th>Category Name</th>
                <th>Product Name</th>
                <th>Product Description</th>
                <th>Product Price</th>
                <th>Product Quantity</th>
                <th>Product Image</th>
                <th>Operation</th>
            </tr>
            <c:forEach var="product" items="${requestScope.listP}">
                <tr>
                    <td>${product.productID}</td>
                    <td>${product.categoryName}</td>
                    <td>${product.productName}</td>
                    <td>${product.productDescription}</td>
                    <td>${product.productPrice}</td>
                    <td>${product.productQuantity}</td>
                    <td><img src="<%=request.getContextPath()%>${product.productImage}" width="80px" height="80px"/></td>
                    <td>
                        <a href="#targetHeader" id="productEditLink" data-productID="${product.productID}">Edit</a>
                        <a href="#targetHeader" id="productDeleteLink" data-productID="${product.productID}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
