package pos.view;

import java.io.IOException;

import pos.model.InsufficientPaymentException;

import pos.controller.Controller;
import pos.controller.OperationFailedException;
import pos.dataobjects.EAN;
import pos.dataobjects.InvalidEanException;
import pos.integration.NoSuchItemException;
import pos.util.LogHandler;

public class View {
	private Controller ctrl;
	private LogHandler logHandler;
	private TotalRevenueView revenueView;

	public View(Controller ctrl) {
		this.ctrl = ctrl;
		this.revenueView = new TotalRevenueView();
		try {
			this.logHandler = new LogHandler();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void hardCodedInteraction() {

		startNewSale(); // return value? 

		enterItem("7310090348139"); 	// blandsaft EAN-13
		enterItem("7310090348139");		// blandsaft
		enterItem("7340083440717"); 	// EAN-kod finns ej i itemRegistry -> NoSuchItemException
		enterItem("0000000000000");		// Scanning this EAN triggers the not connected Exception
		enterItem("7300156486424", 3); 	// 3 st leverpastej
		enterItem("77315009");			// dax wax EAN-8
		enterItem("8717418553401"); 	// lejonkungen dvd
		
		endSale();

		enterCashPayment(200);
		enterCashPayment(320);

	}
	private void startNewSale() {
		printAction("STARTING NEW SALE");
		ctrl.startNewSale();
		ctrl.addSaleObserver(this.revenueView);
	}  
	private void enterItem(String itemId) {
		enterItem(itemId, 1);
	}
	private void enterItem(String itemID, int quantity) {
		//check if ID is 11 or 13 EAN number or 4 digit PLU
		// create EAN and PLU objects to encapsulate id truthiness
		// EAN and PLU could inherit or implement an abstract or interface?!  
		// return error if wrong format
		// else call ctrl with it - ctrl decides what to do next

		printAction(String.format("ENTERING %d OF ITEM %s", quantity, itemID));
		
		try {
			EAN ean = new EAN(itemID); 
			System.out.println( ctrl.enterItem(ean, quantity) );
		}
		// NEW SALE NOT INITIATED
		catch(IllegalStateException exc) {
			System.out.println(exc.getMessage());
		}
		catch (OperationFailedException exc) {
			System.out.println(exc.getMessage() + "\n Please try again.");
			logHandler.logError(exc);
		} 
		// catch exception from EAN constructor
		// if ean cannot be created the format of the identifier is wrong
		// and results in a prompt to user
		catch (InvalidEanException exc) { //NoSuchItemException
			// catch exception from itemRegistry if product not found
			System.out.println(exc.getMessage());
		}
		finally {
			System.out.println("----------------------");
		}
	}

	private void endSale() {
		int total = (int) Math.round(ctrl.ringUpTotal());
		System.out.println(String.format("Att betala:	%d kr",total ));
	}

	private void enterCashPayment(int cashAmount) {
		try { 
			int cashback = ctrl.pay(cashAmount);
			System.out.println(String.format("Tillbaka:	%d kr", cashback));
		} catch (InsufficientPaymentException ex) {
			System.out.println(String.format("Otillräcklig betalning: %d", ex.getamountPaid()));
		}
	}

	private void printAction(String string) {
		System.out.printf("\n[%s]\n\n", string);
	}
}