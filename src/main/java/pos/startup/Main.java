package pos.startup;

import pos.controller.Controller;
import pos.integration.IntegrationHandler;
import pos.view.View;

public class Main {
    
    public static void main(String[] args) {
        var integrationHandler = new IntegrationHandler();
        var ctrl = new Controller(integrationHandler);
        new View(ctrl).hardCodedInteraction();
    }
}