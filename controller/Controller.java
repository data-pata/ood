package controller;

import integration.EAN;
import integration.ExtSysHandler;
import integration.NoSuchItemException;
import model.CashRegister;
import model.Model;

public class Controller {
	private ExtSysHandler extSysHandler;
	private Model model;

	public Controller(ExtSysHandler extSysHandler, CashRegister cashRegister) {
		this.extSysHandler = extSysHandler;
		model = new Model(extSysHandler, cashRegister);
	}

	public void startNewSale() {
		System.out.println("ctrl: starting new sale");
		model.startNewSale(); 
	}

	public String enterItem(EAN ean) throws NoSuchItemException {
		// search for item in catalog first or check if ean already scanned first? 
		// check if scanned first: u
		// retrieve product from external system, returns
		// try {
		return model.enterItem(ean);
			
		// } catch (NoSuchItemException e) {
			// throw e;
		}
	
	public int ringUpTotal() {
		return model.ringUpTotal();
	}

	public int enterPayment(int amount) {
		// makepayment
		// logSale
		// sendlogsaleto extsystems
		// create receipt send to printer
		return model.pay(amount);
	}

}
