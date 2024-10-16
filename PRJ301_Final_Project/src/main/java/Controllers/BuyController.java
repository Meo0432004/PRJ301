/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.CurrentAdminDAO;
import DAO.OrderDAO;
import DAO.OrderItemsDAO;
import DAO.ProductDAO;
import DAO.ShippingDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Models.Admin;
import Models.Order;
import Models.OrderItems;
import Models.Product;
import Models.Shipping;
import Models.Transaction;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author NHAT
 */
public class BuyController extends HttpServlet {

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
            out.println("<title>Servlet BuyController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>ServletAAAA BuyController at " + request.getContextPath() + "</h1>");
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
        getCustomer(request, response);
        if (uri.endsWith("AllCart")) {
            buyAll(request, response);
        }
    }

    void buyAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie cookies[] = request.getCookies();
        ArrayList<Product> listP = new ArrayList<>();
        ProductDAO pdao = new ProductDAO();
        for (Cookie c : cookies) {
            if (c.getName().contains("cart_")) {
                Product p = new Product();
                String productID = c.getName().substring(c.getName().indexOf("_") + 1);
                p = pdao.getProductById(productID);
                String productQuantity_raw = c.getValue();
                int productQuantity = Integer.parseInt(productQuantity_raw);
                p.setProductQuantity(productQuantity);
                listP.add(p);
            }
        }
        request.setAttribute("listP", listP);
        request.getRequestDispatcher("/Buy.jsp").forward(request, response);
    }

    void getCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie cookies[] = request.getCookies();
        UserDAO udao = new UserDAO();
        for (Cookie c : cookies) {
            if (c.getName().equals("user")) {
                User u = udao.getUserByU(c.getValue());
                request.setAttribute("customer", u);
            }
        }

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            //URL url = new URL("https://esgoo.net/api-tinhthanh/4/0.htm");
            URL url = new URL("https://esgoo.net/api-tinhthanh/1/0.htm");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseJson = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseJson.append(line);
                }

                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(responseJson.toString());
                int error = ((Long) jsonResponse.get("error")).intValue();
                if (error == 0) {
                    JSONArray data = (JSONArray) jsonResponse.get("data");
                    request.setAttribute("provinces", data);
                }
            }
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }
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
        if (action.endsWith("Buy")) {
            getCustomer(request, response);
            Buy(request, response);
        } else if (action.equals("Purchase")) {
            Purchase(request, response);

        }
    }

    void Buy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pID = request.getParameter("productID");
        String productQuantity_raw = request.getParameter("productQuantity");
        int productQuantity = 0;
        try {
            productQuantity = Integer.parseInt(productQuantity_raw);
        } catch (Exception e) {
        }
        ProductDAO pdao = new ProductDAO();
        Product p = pdao.getProductById(pID);
        p.setProductQuantity(productQuantity);
        ArrayList<Product> listP = new ArrayList<>();
        listP.add(p);
        request.setAttribute("listP", listP);
        //request.setAttribute("action", "PurchaseSingle");
        request.getRequestDispatcher("/Buy.jsp").forward(request, response);
    }

    void Purchase(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String isShipping_raw = request.getParameter("purchaseType");
        String userProvinceCity = "";
        String userDistrict = "";
        boolean isShipping = false;
        if (isShipping_raw.equals("shipping")) {
            isShipping = true;
            userProvinceCity = request.getParameter("provinceName");
            userDistrict = request.getParameter("district");
            if (userProvinceCity.equals("") && userDistrict.equals("")) {
                response.sendRedirect(request.getContextPath() + "/Buy/AllCart");
                return;
            }
        }

        manageUser(request, response, isShipping, userProvinceCity, userDistrict);

        manageOrder(request, response, isShipping, userProvinceCity, userDistrict);

        OrderDAO odao = new OrderDAO();
        Order o = odao.getOrderLasted();

        manageOrderItems(request, response, o);

        mangeShipping(request, isShipping, o, userProvinceCity, userDistrict);

        manageTransaction(request);

        deleteCartCookie(request, response);

        response.sendRedirect(request.getContextPath() + "/Home");
    }

    void manageUser(HttpServletRequest request, HttpServletResponse response, boolean isShipping, String userProvinceCity, String userDistrict)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        if (username.equals("") || email.equals("") || phone.equals("")) {
            response.sendRedirect(request.getContextPath() + "/Buy/AllCart");
            return;
        }

        Cookie cookie[] = request.getCookies();
        boolean isCustomer = false;
        for (Cookie c : cookie) {
            if (c.getName().equals("user")) {
                isCustomer = true;
                break;
            }
        }

        UserDAO udao = new UserDAO();
        if (!isCustomer) {
            User guest = new User();
            guest.setUsername(username);
            guest.setEmail(email);
            guest.setPhone(phone);
            if (isShipping) {
                guest.setUserProvinceCity(userProvinceCity);
                guest.setUserDistrict(userDistrict);
            }
            udao.InsertUser(guest, "Guest");
        }
    }

    void manageOrder(HttpServletRequest request, HttpServletResponse response, boolean isShipping, String userProvinceCity, String userDistrict)
            throws ServletException, IOException {
        String[] productPrices = request.getParameterValues("productPrice");
        long totalPrice = 0;
        for (String productPrice : productPrices) {
            totalPrice += Long.parseLong(productPrice);
        }

        UserDAO udao = new UserDAO();
        String username = request.getParameter("username");
        User u = udao.getUserByU(username);
        int userID = u.getUserID();
        OrderDAO odao = new OrderDAO();
        Order o = new Order();
        o.setUserID(userID);
        o.setTotalPrice(totalPrice);
        o.setIsShipping(isShipping);
        odao.insertOrder(o);
    }

    void manageOrderItems(HttpServletRequest request, HttpServletResponse response, Order o) throws ServletException, IOException {
        String[] productIDs = request.getParameterValues("productID");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] productQuantities = request.getParameterValues("productQuantity");
        OrderItemsDAO oidao = new OrderItemsDAO();
        for (int i = 0; i < productIDs.length; i++) {
            OrderItems oi = new OrderItems();
            oi.setOrderID(o.getOrderID());
            oi.setProductID(productIDs[i]);
            int productPrice = Integer.parseInt(productPrices[i]);
            oi.setUnitPrice(productPrice);
            int productQuantity = Integer.parseInt(productQuantities[i]);
            oi.setQuantity(productQuantity);
            oidao.insertOrderItems(oi);
        }
    }

    void mangeShipping(HttpServletRequest request, boolean isShipping, Order o, String userProvinceCity, String userDistrict) {
        String username = request.getParameter("username");
        UserDAO udao = new UserDAO();
        User u = udao.getUserByU(username);
        int userID = u.getUserID();
        if (isShipping) {
            ShippingDAO sdao = new ShippingDAO();
            Shipping s = new Shipping();
            s.setOrderID(o.getOrderID());
            s.setUserID(userID);
            s.setShippingProvince(userProvinceCity);
            s.setShippingDistrict(userDistrict);
            sdao.insertShipping(s);
        }
    }

    void manageTransaction(HttpServletRequest request) {
        TransactionDAO trsDAO = new TransactionDAO();
        Transaction trs = new Transaction();
        CurrentAdminDAO caddao = new CurrentAdminDAO();
        Admin ad = caddao.getCurrentAdmin();
        String[] productIDs = request.getParameterValues("productID");
        String[] productQuantities = request.getParameterValues("productQuantity");
        for (int i = 0; i < productIDs.length; i++) {
            trs.setAdminID(ad.getAdminID());
            trs.setProductID(productIDs[i]);
            int productQuantity = Integer.parseInt(productQuantities[i]);
            trs.setQuantity(productQuantity);
            trsDAO.insertTransaction(trs, "Buy");
        }

    }

    void deleteCartCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().contains("cart_")) {
                    c.setMaxAge(0);
                    c.setPath("/"); // Ensure the path matches exactly with how the cookie was set
                    response.addCookie(c);
                }
            }
        }
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
