package com.web.kris.main.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Mohru on 2016-06-26.
 */
public class DocumentPositionsList extends ArrayList<DocumentPosition> {

    private List<Observer> observers = new ArrayList<Observer>();
    private ArrayList<String> removedPositionsId = new ArrayList<String>();

    public DocumentPositionsList() {
        super();
    }

    @Override
    public boolean add(DocumentPosition position){
        boolean result = super.add(position);

        notifyObservers();
        return result;
    }

    public void edit(DocumentPosition position){
        int positionOrdinal = position.getOrdinal();

        DocumentPosition positionToEdit = this.get(positionOrdinal -1);

        positionToEdit.setGrossValue(position.getGrossValue());
        positionToEdit.setNetValue(position.getNetValue());
        positionToEdit.setQuantity(position.getQuantity());

        notifyObservers();
    }

    @Override
    public DocumentPosition remove(int position){

        for(int i = position +1; i< this.size(); ++i){
            DocumentPosition pos = this.get(i);
            pos.setOrdinal(pos.getOrdinal() -1);
        }

        notifyObservers();

        DocumentPosition removed = super.remove(position);

        if(! removed.id.equals(""))
            removedPositionsId.add(removed.id);

        return removed;
    }

    public DocumentPosition getByItemId(String itemId){
        for(DocumentPosition position : this){
            if(position.getItem().getId().equals(itemId))
                return position;
        }

        return null;
    }

    public boolean containsById(String itemId){

        for(DocumentPosition position : this){
            if(position.getItem().getId().equals(itemId))
                return true;
        }

        return false;
    }

    public double getDocumentValueNet(){
        double sum = 0;

        for(DocumentPosition position: this)
            sum += position.getNetValue();

        return sum;
    }

    public double getDocumentValueGross(){
        double sum = 0;

        for(DocumentPosition position: this)
            sum += position.getGrossValue();

        return sum;
    }

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    public boolean hasObserverOfType(Observer o){
        for(Observer observer : observers){
            if(observer.getClass() == o.getClass())
                return true;
        }

        return false;
    }

    private void notifyObservers(){
        for(Observer observer : observers){
            observer.update(null, null);	// jakie tu powinny byc wartosci?
        }
    }

    public ArrayList<String> getRemovedPositionsId() {
        return removedPositionsId;
    }
}