package ro.tuc.BusinessLogic;

import ro.tuc.GUI.SimulationFrame;
import ro.tuc.Model.Server;
import ro.tuc.Model.Task;

import java.util.LinkedList;

public class TimeStrategy implements Strategy{
    public void addTaskByStrategy(LinkedList<Server> servers, Task t, Integer maxTasksPerServer, SimulationFrame frame) throws InterruptedException {
        int minTime = 10000;
        Server minServer = servers.getFirst();
        for(Server s: servers){
            if(s.getTasks().size()<=maxTasksPerServer && s.getWaitingT().get()<minTime){
                minTime = s.getWaitingT().get();
                minServer = s;
            }
        }
        minServer.addTask(t);
    }
}
