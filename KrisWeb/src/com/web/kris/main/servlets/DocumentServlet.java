package com.web.kris.main.servlets;

import com.web.kris.main.entities.Document;
import com.web.kris.main.managers.DocumentsManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mohru on 2016-01-25.
 */
@WebServlet(name = "DcumentServlet")
public class DocumentServlet extends HttpServlet {

    private List<Document> documents;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Dokumenty");
        request.setAttribute("panel-detail-name", "Nazwa dokumentu");

        documents = DocumentsManager.getInstance().getAllDocuments();

        request.setAttribute("documents", documents);

        String documentIndex = request.getParameter("documentIndex");

        if(documentIndex != null){
            int index = Integer.valueOf(documentIndex);
            Document document = documents.get(index);

            request.setAttribute("document", document);
        }
        else{
            request.removeAttribute("document");
        }

        String addNewDocument = request.getParameter("addNewDocument");
        if(addNewDocument != null)
            request.setAttribute("addNewDocument", true);
        else
            request.removeAttribute("addNewDocument");

        request.getRequestDispatcher("documents/documents.jsp").forward(request, response);
    }
}
