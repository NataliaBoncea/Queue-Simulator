package ro.tuc.BusinessLogic;

import ro.tuc.GUI.SimulationSetup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartListener implements ActionListener {
    protected SimulationManager simulation;
    protected Controller controller;
    protected SimulationSetup setup;

    public StartListener(Controller controller, SimulationManager simulation, SimulationSetup setup){
        this.simulation = simulation;
        this.controller = controller;
        this.setup = setup;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(setup.getStrategy() == 0){
            simulation.selectionPolicy = Scheduler.SelectionPolicy.SHORTEST_QUEUE;
        }
        else{
            simulation.selectionPolicy = Scheduler.SelectionPolicy.SHORTEST_TIME;
        }
        if(insertValues()){
            simulation.setSimulation();
            try {
                simulation.generateNRandomTasks();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            simulation.startSimulation();
            simulation.getFrame().frame.setVisible(true);
        }
    }

    public boolean verifInput(String s){
        if (s.isEmpty()){
            return false;
        }
        return s.matches("^\\d+$");
    }

    public boolean insertValues(){
        if(!verifInput(setup.getNumberOfServers())){
            setup.incorrectValueError(setup.getServerT());
            return false;
        }
        else{
            simulation.numberOfServers = Integer.parseInt(setup.getNumberOfServers());
            setup.goodValue(setup.getServerT());
        }
        if(!verifInput(setup.getNumberOfTasks())){
            setup.incorrectValueError(setup.getTaskT());
            return false;
        }
        else{
            simulation.numberOfClients = Integer.parseInt(setup.getNumberOfTasks());
            setup.goodValue(setup.getTaskT());
        }
        if(!verifInput(setup.getSimulationTime())){
            setup.incorrectValueError(setup.getSimulationTimeT());
            return false;
        }
        else{
            simulation.timeLimit = Integer.parseInt(setup.getSimulationTime());
            setup.goodValue(setup.getSimulationTimeT());
        }
        if(!verifInput(setup.getArrivalMin())){
            setup.incorrectValueError(setup.getArrivalMinT());
            return false;
        }
        else{
            simulation.minArrivalTime = Integer.parseInt(setup.getArrivalMin());
        }
        if(!verifInput(setup.getArrivalMax())){
            setup.incorrectValueError(setup.getArrivalMaxT());
            return false;
        }
        else{
            simulation.maxArrivalTime = Integer.parseInt(setup.getArrivalMax());
        }
        if(verifInput(setup.getArrivalMin()) && verifInput(setup.getArrivalMax())){
            if(simulation.maxArrivalTime < simulation.minArrivalTime){
                setup.incorrectOrderError(setup.getArrivalMinT(), setup.getArrivalMaxT());
                return false;
            }
            else{
                setup.goodValue(setup.getArrivalMinT());
                setup.goodValue(setup.getArrivalMaxT());
            }
        }
        if(!verifInput(setup.getServiceMin())){
            setup.incorrectValueError(setup.getServiceMinT());
            return false;
        }
        else{
            simulation.minServingTime = Integer.parseInt(setup.getServiceMin());
        }
        if(!verifInput(setup.getServiceMax())){
            setup.incorrectValueError(setup.getServiceMaxT());
            return false;
        }
        else{
            simulation.maxServingTime = Integer.parseInt(setup.getServiceMax());
        }
        if(verifInput(setup.getServiceMin()) && verifInput(setup.getServiceMax())){
            if(simulation.maxServingTime < simulation.minServingTime){
                setup.incorrectOrderError(setup.getServiceMinT(), setup.getServiceMaxT());
                return false;
            }
            else{
                setup.goodValue(setup.getServiceMinT());
                setup.goodValue(setup.getServiceMaxT());
            }
        }
        return true;
    }
}
