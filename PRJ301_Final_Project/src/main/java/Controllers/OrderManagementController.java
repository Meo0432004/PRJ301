/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import DAO.OrderItemsDAO;
import DAO.ShippingDAO;
import Models.Order;
import Models.OrderItems;
import Models.Shipping;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NHAT
 */
public class OrderManagementController extends HttpServlet {

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
            out.println("<title>Servlet OrderManagementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderManagementController at " + request.getContextPath() + "</h1>");
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
        if (uri.endsWith("/") || uri.endsWith("OrderList")) {
            orderList(request, response);
        } else if (uri.contains("ShowOrderItems")) {
            showOrderItems(request, response);
        }else if(uri.endsWith("BackToOrder")){
            response.sendRedirect(request.getContextPath()+"/Admin?action=OrderList");
        }
    }

    void orderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO odao = new OrderDAO();
        ArrayList<Order> listO = odao.getAllOrder();
        request.setAttribute("listO", listO);
        ShippingDAO shdao = new ShippingDAO();
        
        request.getRequestDispatcher("/OrderManagement.jsp").forward(request, response);
    }

    void showOrderItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oID_raw = request.getParameter("oID");
        int oID = Integer.parseInt(oID_raw);
        OrderItemsDAO OrItdao = new OrderItemsDAO();
        ArrayList<OrderItems> listOrIt = OrItdao.getAllOrderItemsByOrderID(oID);
        request.setAttribute("listOrIt", listOrIt);
        request.getRequestDispatcher("/showOrderItems.jsp").forward(request, response);
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
        processRequest(request, response);
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
