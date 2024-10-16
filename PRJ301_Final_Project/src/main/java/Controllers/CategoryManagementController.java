package Controllers;

import DAO.CategoryDAO;
import Models.Category;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryManagementController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/") || uri.endsWith("/CategoryList")) {
            request.setAttribute("action", "Add");
            categoryList(request, response);
        } else if (uri.contains("/CategoryEdit")) {
            request.setAttribute("action", "Edit");
            categoryEdit(request, response);
        }
    }

    void categoryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO cdao = new CategoryDAO();
        ArrayList<Category> listC = cdao.getAllCategory();
        request.setAttribute("listC", listC);
        request.getRequestDispatcher("/CategoryManagement.jsp").forward(request, response);
    }

    void categoryEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryID = request.getParameter("cID");
        CategoryDAO cdao = new CategoryDAO();
        Category c = cdao.getCategoryByID(categoryID);
        request.setAttribute("category", c);
        ArrayList<Category> listC = cdao.getAllCategory();
        request.setAttribute("listC", listC);
        request.getRequestDispatcher("/CategoryManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("Add")){
            categoryAddPost(request, response);
        }
        else if (action.equals("Edit")) {
            categoryEditPost(request, response);
        }
    }
    
    void categoryAddPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryID = request.getParameter("categoryID");
        String categoryName = request.getParameter("categoryName");
        String categoryDescription = request.getParameter("categoryDescription");
        CategoryDAO cdao = new CategoryDAO();
        Category c = new Category(categoryID, categoryName, categoryDescription);
        cdao.insertCategory(c);
        response.sendRedirect(request.getContextPath() + "/Admin?action=CategoryList");
    }

    void categoryEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryID = request.getParameter("categoryID");
        String categoryName = request.getParameter("categoryName");
        String categoryDescription = request.getParameter("categoryDescription");
        CategoryDAO cdao = new CategoryDAO();
        Category c = new Category(categoryID, categoryName, categoryDescription);
        cdao.updateCategory(c);
        response.sendRedirect(request.getContextPath() + "/Admin?action=CategoryList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
