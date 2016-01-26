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
@WebServlet(name = "WarehouseServlet")
public class WarehouseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Magazyny");
        request.setAttribute("panel-detail-name", "Nazwa magazynu");
        request.getRequestDispatcher("contractors.jsp").forward(request, response);
    }
}
