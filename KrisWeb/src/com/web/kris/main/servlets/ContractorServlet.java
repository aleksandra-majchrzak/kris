package com.web.kris.main.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mohru on 2016-01-25.
 */
@WebServlet(name = "ContractorServlet")
public class ContractorServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Kontrahenci");
        request.setAttribute("panel-detail-name", "Nazwa Kontrahenta");
        request.getRequestDispatcher("contractors.jsp").forward(request, response);
    }
}
