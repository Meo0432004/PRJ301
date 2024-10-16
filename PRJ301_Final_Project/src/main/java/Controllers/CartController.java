/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.ProductDAO;
import Models.CartItem;
import Models.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NHAT
 */
public class CartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String productID = request.getParameter("productID");
        String currentURL = request.getParameter("currentURL");

        ProductDAO productsDAO = new ProductDAO();
        Product product = productsDAO.getProductById(productID);
        if (action != null && productID != null) {
            switch (action) {
                case "increase":
                    increaseQuantity(request, response, productID);
                    response.sendRedirect(request.getContextPath() + "/Cart");
                    break;
                case "decrease":
                    decreaseQuantity(request, response, productID);
                    response.sendRedirect(request.getContextPath() + "/Cart");
                    break;
                case "remove":
                    removeFromCart(response, productID);
                    response.sendRedirect(request.getContextPath() + "/Cart");
                    break;

                case "add":
                    addToCart(request, response, product, 1);
                    request.setAttribute("url", currentURL);
                    request.setAttribute("message", "Item added to cart successfully!");
                    response.sendRedirect(currentURL);
                    break;
                default:
                    // Handle other actions
                    break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Map<String, CartItem> cart = getCartFromCookies(cookies);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/Cart.jsp").forward(request, response);
    }

    public boolean checkQuantity(String productID, int currentQuantity) {
        ProductDAO pdao = new ProductDAO();
        Product p = pdao.getProductById(productID);
        if (currentQuantity > p.getProductQuantity()) {
            return false;
        }
        return true;
    }

    private void increaseQuantity(HttpServletRequest request, HttpServletResponse response, String productId) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("cart_" + productId)) {
                int quantity = Integer.parseInt(cookie.getValue());
                if (!checkQuantity(productId, quantity + 1)) {
                    try {
                        response.getWriter().println("<script>alert('You have reached the maximum quantity for this product.');</script>");
                    } catch (Exception e) {
                    }
                    break;
                } else {
                    cookie.setValue(String.valueOf(quantity + 1));
                    cookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    private void decreaseQuantity(HttpServletRequest request, HttpServletResponse response, String productId) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("cart_" + productId)) {
                int quantity = Integer.parseInt(cookie.getValue());
                if (quantity > 1) {
                    cookie.setValue(String.valueOf(quantity - 1));
                    cookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
                    cookie.setPath("/");
                    response.addCookie(cookie);
                } else {
                    // Optionally, remove the cookie if quantity becomes 0
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                break;
            }
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response, Product product, int quantity) {
        boolean found = false;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("cart_" + product.getProductID())) {
                    int newQuantity = Integer.parseInt(c.getValue()) + quantity;
                    if (!checkQuantity(product.getProductID(), newQuantity)) {
                        found = true;
                        try {
                            response.getWriter().println("<script>alert('You have reached the maximum quantity for this product.');</script>");
                            response.getWriter().flush();
                        } catch (Exception e) {
                        }
                        break;
                    } else {
                        c.setValue(String.valueOf(newQuantity));
                        c.setPath("/");
                        response.addCookie(c);
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!found) {
            String cookieName = "cart_" + product.getProductID();
            Cookie cookie = new Cookie(cookieName, String.valueOf(quantity));
            cookie.setMaxAge(60 * 60 * 24 * 3); // Cookie lasts for 7 days
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    private void removeFromCart(HttpServletResponse response, String productId) {
        String cookieName = "cart_" + productId;
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0); // Delete the cookie
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private Map<String, CartItem> getCartFromCookies(Cookie[] cookies) {
        Map<String, CartItem> cart = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("cart_")) {
                    String productId = cookie.getName().substring(5);
                    int quantity = Integer.parseInt(cookie.getValue());
                    Product product = new ProductDAO().getProductById(productId);
                    if (product != null) {
                        cart.put(productId, new CartItem(product, quantity));
                    }
                }
            }
        }
        return cart;
    }
}
