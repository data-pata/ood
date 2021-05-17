package pos.startup;

import pos.controller.Controller;
import pos.integration.IntegrationHandler;
import pos.view.View;
/**
 * The main class of the POS system. It initiates the system by creating an
 * integrationHandler, the main Controller and the View. It starts a mock hard
 * coded interaction from the view. 
 *
 */
public class Main {
        
    /**
     * Initiates the system by creating an integrationHandler, the main
     * Controller and the View. It starts a mock hard coded interaction from the
     * view.
     *
     * @param args  command line arguments are ignored. 
     */
    public static void main(String[] args) {
        var integrationHandler = new IntegrationHandler();
        var ctrl = new Controller(integrationHandler);
        new View(ctrl).hardCodedInteraction();
    }
}
