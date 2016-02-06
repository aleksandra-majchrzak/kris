package com.web.kris.main.servlets;

import com.web.kris.main.entities.Document;
import com.web.kris.main.enums.DocumentType;
import com.web.kris.main.managers.ContractorsManager;
import com.web.kris.main.managers.DocumentsManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohru on 2016-01-25.
 */
@WebServlet(name = "DcumentServlet")
public class DocumentServlet extends HttpServlet {

    private List<Document> documents;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String documentToDeleteIndex = request.getParameter("documentToDeleteIndex");

        if(documentToDeleteIndex != null){
            int index = Integer.valueOf(documentToDeleteIndex);

            DocumentsManager.getInstance().deleteDocument(documents.get(index).getId());

            request.setAttribute("panel-name", "Dokumenty");

            documents = DocumentsManager.getInstance().getAllDocuments();
            request.setAttribute("documents", documents);

            request.getRequestDispatcher("documents/documents.jsp").forward(request, response);
            return;
        }

        String documentId = request.getParameter("documentId");

        Document documentToSave = new Document();

        if(documentId != null)
            documentToSave.setId(documentId);

        documentToSave.setNumber(request.getParameter("number"));
        documentToSave.setType(DocumentType.values()[Integer.valueOf(request.getParameter("docType"))]);
        documentToSave.setContractor(ContractorsManager.getInstance().getContractor(request.getParameter("contractorDocId")));
        documentToSave.setDocumentDate(new Date());
        documentToSave.setPaymentDate(new Date());
        documentToSave.setDescription(request.getParameter("description"));
        documentToSave.setPaymentForm(Integer.valueOf(request.getParameter("paymentForm")));
        documentToSave.setValue(Double.valueOf(request.getParameter("value")));

        DocumentsManager.getInstance().saveDocument(documentToSave);

        request.setAttribute("panel-name", "Dokumenty");

        documents = DocumentsManager.getInstance().getAllDocuments();
        request.setAttribute("documents", documents);

        request.getRequestDispatcher("documents/documents.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Dokumenty");

        documents = DocumentsManager.getInstance().getAllDocuments();

        request.setAttribute("documents", documents);

        String documentIndex = request.getParameter("documentIndex");

        if(documentIndex != null){
            int index = Integer.valueOf(documentIndex);
            Document document = documents.get(index);

            request.setAttribute("document", document);
            request.setAttribute("documentIndex", index);
        }


        String addNewDocument = request.getParameter("addNewDocument");
        if(addNewDocument != null) {
            request.setAttribute("addNewDocument", true);
            request.setAttribute("contractorsDoc", ContractorsManager.getInstance().getAllContractors());
        }

        String editDocumentIndex = request.getParameter("documentToEditIndex");

        if(editDocumentIndex != null) {
            request.setAttribute("addNewDocument", true);

            int index = Integer.valueOf(editDocumentIndex);
            Document documentToEdit = documents.get(index);

            request.setAttribute("documentToEdit", documentToEdit);
            request.setAttribute("contractorsDoc", ContractorsManager.getInstance().getAllContractors());
        }

        request.getRequestDispatcher("documents/documents.jsp").forward(request, response);
    }
}
