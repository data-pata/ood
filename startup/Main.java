package startup;

import controller.Controller;
import integration.IntegrationHandler;
import view.View;

public class Main {
    
    public static void main(String[] args) {
        
        var extSysHandler = new IntegrationHandler();
        var ctrl = new Controller(extSysHandler);
        new View(ctrl);
    }
}