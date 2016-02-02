package com.web.kris.main.servlets;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.Contractor;
import com.web.kris.main.managers.ContractorsManager;
import com.web.kris.main.managers.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohru on 2016-01-25.
 */
@WebServlet(name = "ContractorServlet")
public class ContractorServlet extends HttpServlet {

    private List<Contractor> contractors;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Kontrahenci");
        request.setAttribute("panel-detail-name", "Nazwa Kontrahenta");

        if(contractors == null || request.getParameter("refresh") != null)
            contractors = ContractorsManager.getInstance().getAllContractors();

        request.setAttribute("contractors", contractors);

        String contractorIndex = request.getParameter("contractorIndex");

        if(contractorIndex != null){
            int index = Integer.valueOf(contractorIndex);
            Contractor contractor = contractors.get(index);

            request.setAttribute("contractor", contractor);
        }
        else{
            request.removeAttribute("contractor");
        }

        String addNewContractor = request.getParameter("addNewContractor");
        if(addNewContractor != null)
            request.setAttribute("addNewContractor", true);
        else
            request.removeAttribute("addNewContractor");

        request.getRequestDispatcher("contractors/contractors.jsp").forward(request, response);
    }
}
