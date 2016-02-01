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
@WebServlet(name = "ItemServlet")
public class ItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Towary");
        request.setAttribute("panel-detail-name", "Nazwa towaru");
        request.getRequestDispatcher("items.jsp").forward(request, response);
    }
}