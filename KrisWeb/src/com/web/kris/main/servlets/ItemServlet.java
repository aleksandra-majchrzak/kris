package com.web.kris.main.servlets;

import com.web.kris.main.entities.Item;
import com.web.kris.main.entities.ItemStocks;
import com.web.kris.main.entities.Price;
import com.web.kris.main.managers.ItemsManager;

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
@WebServlet(name = "ItemServlet")
public class ItemServlet extends HttpServlet {

    private List<Item> items;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String itemToDeleteIndex = request.getParameter("itemToDeleteIndex");

        if(itemToDeleteIndex != null){
            int index = Integer.valueOf(itemToDeleteIndex);

            ItemsManager.getInstance().deleteItem(items.get(index).getId());

            request.setAttribute("panel-name", "Towary");

            items = ItemsManager.getInstance().getAllItems();
            request.setAttribute("items", items);

            request.getRequestDispatcher("items/items.jsp").forward(request, response);
            return;
        }

        // powinnaś tu sprawdzac czy ten towar juz istnieje na bazie- wtedy to jego cene zmieniasz
        Price price = new Price("",
                Double.valueOf(request.getParameter("netPrice")),
                Double.valueOf(request.getParameter("grossPrice")));

        price.setId(ItemsManager.getInstance().savePrice(price));

        String itemId = request.getParameter("itemId");

        Item itemToSave = new Item();

        if(itemId != null)
            itemToSave.setId(itemId);

        itemToSave.setCode(request.getParameter("code"));
        itemToSave.setName(request.getParameter("name"));
        itemToSave.setSize(request.getParameter("size"));
        itemToSave.setMaterial(request.getParameter("material"));
        itemToSave.setPrice(price);
        itemToSave.setDescription(request.getParameter("description"));
        itemToSave.setType(request.getParameter("type"));
        itemToSave.setItemStocks(new ArrayList<ItemStocks>());

        ItemsManager.getInstance().saveItem(itemToSave);

        request.setAttribute("panel-name", "Towary");

        items = ItemsManager.getInstance().getAllItems();
        request.setAttribute("items", items);

        request.getRequestDispatcher("items/items.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("panel-name", "Towary");
        request.setAttribute("panel-detail-name", "Nazwa towaru");

        items = ItemsManager.getInstance().getAllItems();

        request.setAttribute("items", items);

        String itemIndex = request.getParameter("itemIndex");

        if(itemIndex != null){
            int index = Integer.valueOf(itemIndex);
            Item item = items.get(index);

            request.setAttribute("item", item);
            request.setAttribute("itemIndex", index);
        }


        String addNewItem = request.getParameter("addNewItem");
        if(addNewItem != null) {
            request.setAttribute("addNewItem", true);

            request.setAttribute("types", ItemsManager.getInstance().getAllItemTypes());
            request.setAttribute("materials", ItemsManager.getInstance().getAllItemMaterials());
            request.setAttribute("sizes", ItemsManager.getInstance().getAllItemSizes());
        }


        String editItemIndex = request.getParameter("itemToEditIndex");

        if(editItemIndex != null) {
            request.setAttribute("addNewItem", true);

            int index = Integer.valueOf(editItemIndex);
            Item itemToEdit = items.get(index);

            request.setAttribute("types", ItemsManager.getInstance().getAllItemTypes());
            request.setAttribute("materials", ItemsManager.getInstance().getAllItemMaterials());
            request.setAttribute("sizes", ItemsManager.getInstance().getAllItemSizes());

            request.setAttribute("itemToEdit", itemToEdit);
        }

        request.getRequestDispatcher("items/items.jsp").forward(request, response);
    }
}
