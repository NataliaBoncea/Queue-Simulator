package ro.tuc.BusinessLogic;

import ro.tuc.GUI.SimulationFrame;
import ro.tuc.GUI.SimulationSetup;

import java.awt.event.ActionEvent;

public class Controller {
    protected SimulationManager simulation;
    protected SimulationSetup setup;

    public Controller(SimulationSetup setup, SimulationManager simulation){
        this.setup = setup;
        this.simulation = simulation;
        this.setup.startListener(new StartListener(this, simulation, setup));
    }
}
