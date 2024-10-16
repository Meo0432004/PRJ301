/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.ReviewDAO;
import DAO.UserDAO;
import Models.Category;
import Models.Product;
import Models.Review;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author NHAT
 */
public class ProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        request.setAttribute("url", uri);
        if (uri.contains("Detail")) {
            ProductDetail(request, response);
        } else if (uri.contains("Search")) {
            ProductSearch(request, response);
        }
    }

    void ProductDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pId = request.getParameter("pID");
        if (pId != null && !pId.isEmpty()) {
            CategoryDAO cDAO = new CategoryDAO();
            ArrayList<Category> listC = cDAO.getAllCategory();
            request.setAttribute("listC", listC);

            ProductDAO pDAO = new ProductDAO();
            Product p = pDAO.getProductById(pId);
            request.setAttribute("productDetail", p);

            ReviewDAO rdao = new ReviewDAO();
            ArrayList<Review> listR = rdao.getReviewByProductID(pId);
            request.setAttribute("listR", listR);
            request.getRequestDispatcher("/ProductDetail.jsp").forward(request, response);
        } else {
            response.sendRedirect("Home");
        }
    }

    void ProductSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//cho no nhan tieng viet
        String pSearch = request.getParameter("pSearch");
        ProductDAO pDAO = new ProductDAO();
        ArrayList<Product> listSP = pDAO.getAllProductsByName(pSearch);
        request.setAttribute("listP", listSP);

        Product newestProduct = pDAO.getNewestProduct();
        request.setAttribute("newP", newestProduct);

        CategoryDAO cDAO = new CategoryDAO();
        ArrayList<Category> listC = cDAO.getAllCategory();
        request.setAttribute("listC", listC);

        request.setAttribute("searchText", pSearch);
        request.getRequestDispatcher("/Home.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("review")) {
            Review(request, response);
        }

    }

    void Review(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        String reviewText = request.getParameter("reviewText");
        String username = request.getParameter("username");
        String currentURL = request.getParameter("currentURL");
        UserDAO udao = new UserDAO();
        User u = udao.getUserByU(username);
        ReviewDAO rdao = new ReviewDAO();
        Review r = new Review();
        r.setProductID(productID);
        r.setCustomerID(u.getUserID());
        r.setReviewText(reviewText);
        int count = 0;
        count = rdao.insertReview(r);
        response.sendRedirect(request.getContextPath() + "/Product/Detail?pID=" + productID);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
