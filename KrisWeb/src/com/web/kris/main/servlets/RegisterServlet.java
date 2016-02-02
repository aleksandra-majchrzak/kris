package com.web.kris.main.servlets;

import com.web.kris.main.entities.User;
import com.web.kris.main.managers.DatabaseManager;
import com.web.kris.main.managers.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mohru on 2016-01-24.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("password").equals(request.getParameter("password_confirmation"))) {

            User user = new User(request.getParameter("username"),
                    UserManager.getInstance().hashPswd(request.getParameter("password")),
                    false,
                    false);

            boolean success = this.register(user);

            if (success)
                request.setAttribute("waitMessage", "Rejestracja zakonczyla sie powodzeniem. Musisz poczekac na zaakceptowanie przez administratora.");
            else
                request.setAttribute("errorMessage", "Rejestracja zakoczona niepowodzeniem. Uzytkownik o podanym loginie istnieje.");
        }
        else
            request.setAttribute("passwordErrorMessage", "Rejestracja zakoczona niepowodzeniem. Hasla nie sa zgodne");

//        request.getSession().setAttribute("User", user);
//        response.sendRedirect("/");
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    private boolean register(User user) {
        DatabaseManager.getInstance().establishConnection();

        String userId = UserManager.getInstance().saveUser(user);
        boolean success = !userId.equals("");

        DatabaseManager.getInstance().closeConnection();

        return success;
        /*
        *   tu powinno byc logowanie, autentykacja uzytkownika i ustawianie danych waznych dla calego okresu mieszy zalogowaniem a wylogowaniem0 tobedzie przekazywane potem do jsp
        * */
    }
}
