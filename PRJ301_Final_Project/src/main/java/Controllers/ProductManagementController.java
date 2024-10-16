/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AdminDAO;
import DAO.CategoryDAO;
import DAO.CurrentAdminDAO;
import DAO.ProductDAO;
import DAO.TransactionDAO;
import Models.Admin;
import Models.Category;
import Models.Product;
import Models.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NHAT
 */
@MultipartConfig
public class ProductManagementController extends HttpServlet {

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
            out.println("<title>Servlet ProductManagementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManagementController at " + request.getContextPath() + "</h1>");
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

        if (uri.endsWith("/") || uri.endsWith("ProductList")) {
            request.setAttribute("action", "Add");
            productList(request, response);
        } else if (uri.contains("ProductEdit")) {
            request.setAttribute("action", "Edit");
            productEdit(request, response);
        } else if (uri.contains("ProductDelete")) {
            request.setAttribute("action", "Delete");
            productDelete(request, response);
        }
    }

    void productList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO pdao = new ProductDAO();
        List<Product> listP = new ArrayList<>();
        listP = pdao.getAllProduct("admin");
        request.setAttribute("listP", listP);

        ArrayList<Category> listC = new ArrayList<>();
        CategoryDAO cdao = new CategoryDAO();
        listC = cdao.getAllCategory();
        request.setAttribute("listC", listC);
        request.getRequestDispatcher("/ProductManagement.jsp").forward(request, response);
    }

    void productEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pID = request.getParameter("pID");
        ProductDAO pdao = new ProductDAO();
        Product product = pdao.getProductById(pID);
        request.setAttribute("product", product);

        List<Product> listP = new ArrayList<>();
        listP = pdao.getAllProduct("admin");
        request.setAttribute("listP", listP);

        CategoryDAO cdao = new CategoryDAO();
        ArrayList<Category> listC = cdao.getAllCategory();
        request.setAttribute("listC", listC);
        //request.getRequestDispatcher("/ProductManagement.jsp").forward(request, response);
        productList(request, response);
    }

    void productDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pID = request.getParameter("pID");
        ProductDAO pdao = new ProductDAO();
        Product product = pdao.getProductById(pID);
        request.setAttribute("product", product);

        List<Product> listP = new ArrayList<>();
        listP = pdao.getAllProduct("admin");
        request.setAttribute("listP", listP);

        CategoryDAO cdao = new CategoryDAO();
        ArrayList<Category> listC = cdao.getAllCategory();
        request.setAttribute("listC", listC);
        request.getRequestDispatcher("/ProductManagement.jsp").forward(request, response);
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
        if (action.equals("Add")) {
            productAddPost(request, response);
        } else if (action.equals("Edit")) {
            productEditPost(request, response);
        } else if (action.equals("Delete")) {
            productDeletePost(request, response);
        }
    }

    void productAddPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        String categoryID = request.getParameter("categoryID");
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        String productPrice_raw = request.getParameter("productPrice");
        String productQuantity_raw = request.getParameter("productQuantity");
        Part part = request.getPart("productImage");
        String realPath = request.getServletContext().getRealPath("/images");
        String fileName = part.getSubmittedFileName();
        String productImage = "/images/" + fileName;
        File uploadFile = new File(realPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }
        try {
            //part.write(realPath + File.separator + fileName);
            long productPrice = Long.parseLong(productPrice_raw);
            int productQuantity = Integer.parseInt(productQuantity_raw);
            ProductDAO pdao = new ProductDAO();
            Product p = new Product(productID, productName, productDescription, productQuantity, productPrice, productImage, categoryID, null);
            pdao.insertProduct(p);
            manageTransaction(request, productID, productQuantity * 2, "In");

            response.sendRedirect(request.getContextPath() + "/Admin?action=ProductList");
        } catch (Exception e) {
        }
    }

    void productEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO pdao = new ProductDAO();
        String productID = request.getParameter("productID");
        String categoryID = request.getParameter("categoryID");
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        String productPrice_raw = request.getParameter("productPrice");
        String productQuantity_raw = request.getParameter("productQuantity");
        Part part = request.getPart("productImage");
        String realPath = request.getServletContext().getRealPath("/images");
        String fileName = part.getSubmittedFileName();
        String productImage = "/images/" + fileName;
        File uploadFile = new File(realPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }
        if (fileName.equals("")) {
            Product p_old = pdao.getProductById(productID);
            productImage = p_old.getProductImage();
        }
        try {
            long productPrice = Long.parseLong(productPrice_raw);
            int productQuantity = Integer.parseInt(productQuantity_raw);

            Product p = new Product(productID, productName, productDescription, productQuantity, productPrice, productImage, categoryID, null);

            String transactionType = manageTransactionType(productID, productQuantity);
            if (!transactionType.equals("")) {
                manageTransaction(request, productID, productQuantity, transactionType);
            }
            if (transactionType.equals("Out")) {
                Product p_old = pdao.getProductById(productID);
                p.setProductQuantity(p_old.getProductQuantity());
                pdao.updateProduct(p);
            } else {
                pdao.updateProduct(p);
            }
            response.sendRedirect(request.getContextPath() + "/Admin?action=ProductList");
        } catch (Exception e) {
        }
    }

    void productDeletePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        Product p = new Product();
        p.setProductID(productID);
        ProductDAO pdao = new ProductDAO();
        manageTransaction(request, productID, 0, "Out");
        pdao.deleteProduct(p);
        response.sendRedirect(request.getContextPath() + "/Admin?action=ProductList");
    }

    String manageTransactionType(String productID, int newProductQuantity) {
        ProductDAO pdao = new ProductDAO();
        Product p_old = pdao.getProductById(productID);
        if (p_old == null) {
            return "In";
        } else {
            if (p_old.getProductQuantity() > newProductQuantity) {
                return "Out";
            } else if (p_old.getProductQuantity() < newProductQuantity) {
                return "In";
            }
        }
        return "";
    }

    void manageTransaction(HttpServletRequest request, String productID, int productQuantity, String transactionType) {
        TransactionDAO trdao = new TransactionDAO();
        Transaction tr = new Transaction();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("admin");
        AdminDAO addao = new AdminDAO();
        Admin ad = addao.getAdminByU(username);
        ProductDAO pdao = new ProductDAO();
        Product p = pdao.getProductById(productID);
        if (transactionType.equals("In")) {
            if (p != null) {
                productQuantity = productQuantity - p.getProductQuantity();
            }
        } else if (transactionType.equals("Out")) {
            productQuantity = p.getProductQuantity() - productQuantity;
        }
        tr.setAdminID(ad.getAdminID());
        tr.setProductID(productID);
        tr.setQuantity(productQuantity);
        trdao.insertTransaction(tr, transactionType);
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
