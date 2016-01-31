package com.web.kris.main.servlets;

import com.web.kris.main.managers.DatabaseManager;

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

        this.login();

        if(request.getMethod().equals("login"))
            this.login();
        else if(request.getMethod().equals("logout"))
            this.logout();
        else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    private void login(){
        DatabaseManager.getInstance().establishConnection();

        /*
        *   tu powinno byc logowanie, autentykacja uzytkownika i ustawianie danych waznych dla calego okresu mieszy zalogowaniem a wylogowaniem0 tobedzie przekazywane potem do jsp
        * */
    }

    private void logout(){

        /*
        *   tu powinno byc wylogowywanie i reserowanie o uzytkowniku (zeby nie wyswietlal sie na nav barze)
        * */

        DatabaseManager.getInstance().closeConnection();
    }

}
