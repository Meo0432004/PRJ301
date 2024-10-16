package Controllers;

import DAO.AdminDAO;
import DAO.UserDAO;
import Models.Admin;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author NHAT
 */
public class AccountController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AccountController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AccountController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("Login")) {
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        } else if (uri.contains("Register")) {
            if(request.getParameter("error")!=null){
                request.setAttribute("error", "This username already exsits!");
            }
            RegisterGet(request, response);
        } else if (uri.endsWith("LogOut")) {
            LogOut(request, response);
        }
    }
    
    void RegisterGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    request.getRequestDispatcher("/Register.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", jsonResponse.get("error_text"));
                    request.getRequestDispatcher("/Register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Lỗi khi gửi yêu cầu đến API: " + responseCode);
                request.getRequestDispatcher("/Register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
            request.getRequestDispatcher("/Register.jsp").forward(request, response);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    void LogOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie cookie[] = request.getCookies();
        HttpSession session = request.getSession();
        for (Cookie c : cookie) {
            if (c.getName().equals("user") || c.getName().equals("admin")) {
                c.setMaxAge(0);
                c.setPath("/"); // Ensure the path is set to the same path as when the cookie was created
                response.addCookie(c);
                session.removeAttribute("user");
                session.removeAttribute("admin");
                session.removeAttribute("guest");
            }
        }
        deleteCartCookie(request, response);
        response.sendRedirect(request.getContextPath() + "/Home");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("btnLogin") != null) {
            Login(request, response);
        } else if (request.getParameter("btnRegister") != null) {
            RegisterPost(request, response);
        }
    }
    
    void Login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDAO udao = new UserDAO();
        User u = udao.getUserByUAndP(username, password);
        
        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", u.getUsername());
            Cookie cookie = new Cookie("user", u.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 3);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/Home");
        } else {
            AdminDAO addao = new AdminDAO();
            Admin ad = addao.getAdminByUandP(username, password);
            if (ad != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", ad.getUsername());
                Cookie cookie = new Cookie("admin", ad.getUsername());
                cookie.setMaxAge(60 * 60 * 24 * 3);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.sendRedirect(request.getContextPath() + "/Home");
                
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        }
    }
    
    void RegisterPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String ProvinceCity = request.getParameter("provinceName");
        String district = request.getParameter("district");
        User u = new User(0, username, password, email, phone, ProvinceCity, district, null, null);
        UserDAO udao = new UserDAO();
        if (udao.getUserByU(username) != null) {
            response.sendRedirect(request.getContextPath() + "/Account/Register?error=true");
        } else {
            if (udao.InsertUser(u, "Customer") != 0) {
                response.sendRedirect(request.getContextPath() + "/Account/Login");
            } else {
                response.sendRedirect(request.getContextPath() + "/Account/Register");
            }
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
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

