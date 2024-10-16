package Controllers;

import DAO.OrderDAO;
import DAO.UserDAO;
import Models.Order;
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
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("Profile")) {
            Profile(request, response);
        }else if(uri.contains("UserHistoryOrder")){
            userHistoryOrder(request, response);
        }
    }

    void Profile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username =(String) session.getAttribute("user");
        UserDAO udao = new UserDAO();
        User u = udao.getUserByU(username);
        request.setAttribute("user", u);
        getCustomerAddress(request, response);
        request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
    }
    
    void userHistoryOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username =(String) session.getAttribute("user");
        UserDAO udao = new UserDAO();
        User u = udao.getUserByU(username);
        OrderDAO odao = new OrderDAO();
        ArrayList<Order> listO = odao.getAllOrderByUserID(u.getUserID());
        request.setAttribute("listO", listO);
        request.getRequestDispatcher("/userHistoryOrder.jsp").forward(request, response);
    }

    void getCustomerAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
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
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("EditProfile")) {
            editProfile(request, response);
        }
    }

    void editProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username_raw = (String) session.getAttribute("user");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String userProvinceCity = request.getParameter("provinceName");
        String userDistrict = request.getParameter("district");
        UserDAO udao = new UserDAO();
        User u_raw = udao.getUserByU(username_raw);
        int userID = u_raw.getUserID();
        User u = new User(userID, username, password, email, phone, userProvinceCity, userDistrict, null, null);
        udao.UpdateUser(u);

        Cookie cookies[] = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("user")) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }
        session.removeAttribute("user");
        session.setAttribute("user", u.getUsername());
        Cookie cookie = new Cookie("user", u.getUsername());
        cookie.setMaxAge(60 * 60 * 24 * 3);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath() + "/UserManagement?action=Profile");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
