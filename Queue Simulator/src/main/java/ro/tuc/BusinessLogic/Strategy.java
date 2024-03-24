package ro.tuc.BusinessLogic;

import ro.tuc.GUI.SimulationFrame;
import ro.tuc.Model.Server;
import ro.tuc.Model.Task;

import java.util.LinkedList;

public interface Strategy {
    public void addTaskByStrategy(LinkedList<Server> servers, Task t, Integer maxTasksPerServer, SimulationFrame frame) throws InterruptedException;
}
