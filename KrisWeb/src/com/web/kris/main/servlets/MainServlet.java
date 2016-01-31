package com.web.kris.main.servlets;

import com.couchbase.client.java.CouchbaseCluster;
import com.web.kris.main.managers.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Mohru on 2016-01-24.
 */
@WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String message;


    public MainServlet() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException
    {
        // Do required initialization
        message = "";//"Hello World";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {

            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>MyServlet.java:doGet(): Servlet code!</title>");
            writer.println("</head>");
            writer.println("<body>");

            writer.println("<h1>This is a simple java servlet." + message+" </h1>");

            writer.println("</body>");
            writer.println("</html>");
        }

        System.out.print("hello");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
