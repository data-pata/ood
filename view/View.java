package view;

import controller.Controller;
import integration.NoSuchItemException;
import integration.dataobjects.EAN;

public class View {
	private Controller ctrl;
	
	public View(Controller ctrl) {
		this.ctrl = ctrl;
		hardCodedInteraction();
	}

	private void hardCodedInteraction() {

		startNewSale(); // return value? 

		enterItem("7310090348139");
		enterItem("7310090348139");		
		enterItem("7340083440717"); // NoSuchItemException
		enterItem("7300156486424");
		enterItem("77315009042");
		enterItem("8717418553401");

		endSale();

		enterCashPayment(200);
		enterCashPayment(300);

	}

	private void startNewSale() {
		ctrl.startNewSale(); 
	}
		
	private void enterItem(String itemID) {
		//check if ID is 11 or 13 EAN number or 4 digit PLU
		// create EAN and PLU objects to encapsulate id truthiness
		// EAN and PLU could inherit or implement an abstract or interface?!  
		// return error if wrong format
		// else call ctrl with it - ctrl decides what to do next
		
		try {

			EAN ean = new EAN(itemID); 
			System.out.println( ctrl.enterItem(ean) );
			System.out.println("----------------------\n");
		} 
			// catch exception from EAN constructor
			// if ean cannot be created the format of the identifier is wrong
			// and results in a prompt to user
		catch (NoSuchItemException e) { //NoSuchItemException
			// catch exception from itemRegistry if product not found
			System.out.println(e.getMessage());
			System.out.println("----------------------\n");
			return;
		}
		
	}

	private void endSale() {
		int total = ctrl.ringUpTotal();
		System.out.println(String.format("Att betala:	%d kr",total ));
	}

	private void enterCashPayment(int cashAmount) {
		
		try { 
			int cashback = ctrl.pay(cashAmount);
			System.out.println(String.format("Tillbaka:	%d kr", cashback));
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			endSale();
		}


	}
}