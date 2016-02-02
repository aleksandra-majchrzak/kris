package com.web.kris.main.servlets;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.web.kris.main.entities.Contractor;
import com.web.kris.main.enums.ContractorType;
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

        String contractorId = request.getParameter("contractorId");

        Contractor contractorToSave = new Contractor();

        if(contractorId != null)
            contractorToSave.setId(contractorId);

        contractorToSave.setCode(request.getParameter("code"));
        contractorToSave.setAddress(request.getParameter("address"));
        contractorToSave.setDescription(request.getParameter("description"));
        contractorToSave.setNIP(request.getParameter("NIP"));
        contractorToSave.setType(ContractorType.values()[Integer.valueOf(request.getParameter("type"))]);

        ContractorsManager.getInstance().saveContractor(contractorToSave);

        request.setAttribute("panel-name", "Kontrahenci");

        contractors = ContractorsManager.getInstance().getAllContractors();
        request.setAttribute("contractors", contractors);

        request.getRequestDispatcher("contractors/contractors.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Kontrahenci");

    //    if(contractors == null || request.getParameter("refresh") != null)
        contractors = ContractorsManager.getInstance().getAllContractors();

        request.setAttribute("contractors", contractors);

        String contractorIndex = request.getParameter("contractorIndex");

        if(contractorIndex != null){
            int index = Integer.valueOf(contractorIndex);
            Contractor contractor = contractors.get(index);

            request.setAttribute("contractor", contractor);
            request.setAttribute("contractorIndex", index);
        }


        String addNewContractor = request.getParameter("addNewContractor");
        if(addNewContractor != null)
            request.setAttribute("addNewContractor", true);


        String editContractorIndex = request.getParameter("contractorToEditIndex");

        if(editContractorIndex != null) {
            request.setAttribute("addNewContractor", true);

            int index = Integer.valueOf(editContractorIndex);
            Contractor contractorToEdit = contractors.get(index);

            request.setAttribute("contractorToEdit", contractorToEdit);
        }

        request.getRequestDispatcher("contractors/contractors.jsp").forward(request, response);
    }
}
