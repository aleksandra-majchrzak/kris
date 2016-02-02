package com.web.kris.main.servlets;

import com.web.kris.main.entities.User;
import com.web.kris.main.managers.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mohru on 2016-02-02.
 */
public class UserServlet extends HttpServlet {
    private List<User> users;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        users = UsersManager.getInstance().getAllUsers();

        req.setAttribute("users", users);
        req.getRequestDispatcher("admin-panel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userToActivateIndex = req.getParameter("userToActivateIndex");

        if(userToActivateIndex != null){
            int index = Integer.valueOf(userToActivateIndex);
            User user = users.get(index);
            user.setIsActive(true);

            UsersManager.getInstance().saveUser(user);

            users = UsersManager.getInstance().getAllUsers();
        }


        String userToDeleteeIndex = req.getParameter("userToDeleteIndex");

        if(userToDeleteeIndex != null){
            int index = Integer.valueOf(userToDeleteeIndex);
            User user = users.get(index);

            UsersManager.getInstance().deleteUser(user.getId());

            users = UsersManager.getInstance().getAllUsers();
        }

        req.setAttribute("users", users);

        req.getRequestDispatcher("admin-panel.jsp").forward(req, resp);
    }

}
