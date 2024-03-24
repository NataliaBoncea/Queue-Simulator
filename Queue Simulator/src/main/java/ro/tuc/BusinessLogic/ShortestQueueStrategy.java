package ro.tuc.BusinessLogic;

import ro.tuc.GUI.SimulationFrame;
import ro.tuc.Model.Server;
import ro.tuc.Model.Task;

import java.util.LinkedList;

public class ShortestQueueStrategy implements Strategy{
    public void addTaskByStrategy(LinkedList<Server> servers, Task t, Integer maxTasksPerServer, SimulationFrame frame) throws InterruptedException {
        int minClients = servers.getFirst().getTasks().size();
        Server minServer = servers.getFirst();
        for(Server s: servers){
            if(s.getTasks().size()<=maxTasksPerServer && s.getTasks().size()<minClients){
                minClients = s.getTasks().size();
                minServer = s;
            }
        }
        minServer.addTask(t);
    }
}
