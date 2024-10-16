<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Models.Category" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Categories Management</title>
        <!-- Link Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS for delete confirmation -->
        <style>
            .delete-btn {
                margin-left: 5px;
            }
            .confirm-delete {
                display: none;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1>Category Management</h1>
            <!-- Add New Category Form -->
            <form action="CategoryManagement" method="post" class="mb-3">
                <c:set var="action" value="${requestScope.action}"/>
                <div class="form-group">
                    <label for="categoryId">ID:</label>
                    <c:if test="${action == 'Edit'}">
                        <input type="text" class="form-control" id="categoryID" value="${requestScope.category.categoryID}"  name="categoryID" readonly>
                    </c:if>
                    <c:if test="${action == 'Add' || action==null}">
                        <input type="text" class="form-control" id="categoryID" value="${requestScope.category.categoryID}"  name="categoryID" required>
                    </c:if>

                </div>
                <div class="form-group">
                    <label for="categoryName">Name:</label>
                    <input type="text" class="form-control" id="categoryName" value="${requestScope.category.categoryName}" name="categoryName" required>
                </div>
                <div class="form-group">
                    <label for="categoryDescription">Description:</label>
                    <textarea class="form-control" id="categoryDescription" name="categoryDescription" required>${requestScope.category.categoryDescription}</textarea>
                </div>


                <input type="hidden" name="action" value="${action}"/>
                <c:if test="${action == 'Add'}">
                    <button type="submit" class="btn btn-success">Add Category</button>
                </c:if>
                <c:if test="${action == 'Edit'}">
                    <button type="submit" class="btn btn-success">Edit</button>
                </c:if>
            </form>

            <!-- Table of Categories -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${requestScope.listC}">
                        <tr id="row_${category.categoryID}">
                            <td>${category.categoryID}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryDescription}</td>
                            <td>
                                <!-- Edit Button -->
                                <a href="#" id="categoryEditLink" data-categoryID="${category.categoryID}">Edit</a>
                                <!-- Delete Button -->
                                <%--<button class="btn btn-danger delete-btn" data-category-id="${category.categoryID}">Delete</button>--%>
                                <!-- Update Form - hidden initially -->
                                <form class="update-form" id="update_form_${category.categoryID}" style="display: none;">
                                    <input type="hidden" name="categoryId" value="${category.categoryID}">
                                    <div class="form-group">
                                        <label for="categoryName_${category.categoryID}">Name:</label>
                                        <input type="text" class="form-control" id="categoryName_${category.categoryID}" name="categoryName" value="${category.categoryName}">
                                    </div>
                                    <div class="form-group">
                                        <label for="categoryDescription_${category.categoryID}">Description:</label>
                                        <textarea class="form-control" id="categoryDescription_${category.categoryID}" name="categoryDescription">${category.categoryDescription}</textarea>
                                    </div>

                                    <button type="button" class="btn btn-primary update-btn">Update</button>
                                    <button type="button" class="btn btn-secondary cancel-btn">Cancel</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JavaScript and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>       

    </body>
</html>