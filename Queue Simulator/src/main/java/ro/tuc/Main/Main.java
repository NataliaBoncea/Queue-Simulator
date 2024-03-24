package ro.tuc.Main;

import ro.tuc.BusinessLogic.Controller;
import ro.tuc.BusinessLogic.SimulationManager;
import ro.tuc.GUI.SimulationSetup;

public class Main {
    public static void main(String[] args) {
        SimulationManager simulation = new SimulationManager();
        SimulationSetup setup = new SimulationSetup();
        Controller controller = new Controller(setup, simulation);
    }
}