package com.example.krismobile.documents.positions;

import java.util.Observable;
import java.util.Observer;

public class DocumentPositionObserver implements Observer{

	@Override
	public void update(Observable observable, Object data) {
		DocumentPosition position = (DocumentPosition) observable;
		if(data.equals("quantity")){
			double quantity = position.getQuantity();
			
			position.setNetValue(position.getItem().getPrice().getNetPrice() * quantity);
			position.setGrossValue(position.getItem().getPrice().getGrossPrice() * quantity);
		}
		
	}

}
