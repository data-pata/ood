package startup;

import controller.Controller;
import integration.ExtSysHandler;
import model.CashRegister;
import view.View;

public class Main {
    
    public static void main(String[] args) {
        
        var extSysHandler = new ExtSysHandler();
        var cashRegister = new CashRegister(3000);
        var ctrl = new Controller(extSysHandler, cashRegister);
        var view = new View(ctrl);
        
    }
}