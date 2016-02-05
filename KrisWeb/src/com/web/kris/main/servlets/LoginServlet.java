package com.web.kris.main.servlets;

import com.web.kris.main.entities.User;
import com.web.kris.main.managers.DatabaseManager;
import com.web.kris.main.managers.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mohru on 2016-01-24.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("username");
        String password = request.getParameter("password");

        User user =  this.login(login, password);

        request.getSession().setAttribute("user", user);

        if (user == null) {
            request.setAttribute("errorMessage", "Bledny login lub haslo.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("logout") != null) {
            request.getSession().removeAttribute("user");
            this.logout();
            response.sendRedirect("/");

        }
    }

    private User login(String login, String password){
        String host = getServletContext().getInitParameter("host");
        String port =  getServletContext().getInitParameter("port");
        DatabaseManager.getInstance().establishConnection(host, port);

        return UsersManager.getInstance().authenticateUser(login, password);

    }

    private void logout(){

        DatabaseManager.getInstance().closeConnection();
    }

}
