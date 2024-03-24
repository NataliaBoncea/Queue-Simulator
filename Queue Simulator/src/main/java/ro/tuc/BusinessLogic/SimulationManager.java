package ro.tuc.BusinessLogic;

import ro.tuc.GUI.SimulationFrame;
import ro.tuc.Model.Server;
import ro.tuc.Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager {
    public int timeLimit = 0;
    public int numberOfClients = 0;
    public int numberOfServers = 0;
    public int maxArrivalTime = 0;
    public int minArrivalTime = 0;
    public int maxServingTime = 0;
    public int minServingTime = 0;
    public Scheduler.SelectionPolicy selectionPolicy = Scheduler.SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private LinkedList<Task> generatedTasks;
    private LinkedList<Thread> servers;
    private FileWriter logFile;

    public SimulationManager(){
        generatedTasks = new LinkedList<>();
        servers = new LinkedList<>();
    }

    public SimulationFrame getFrame() {
        return frame;
    }

    public LinkedList<Thread> getServers() {
        return servers;
    }

    public FileWriter getLogFile() {
        return logFile;
    }

    public void setSimulation(){
        scheduler = new Scheduler(numberOfServers, numberOfClients, this);
        frame = new SimulationFrame(this);
    }

    public void startSimulation(){
        try {
            logFile = new FileWriter("SimulationLog.txt");
            logFile.write("Simulation Log of events\n");
            logFile.write("--------------------------------------------------------------------------------------------------------------------\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for(int i=0;i<numberOfServers;i++){
            Server r = new Server(this, scheduler);
            Thread t = new Thread(r);
            t.setName(Integer.toString(i+1));
            servers.push(t);
            scheduler.getServers().add(r);
        }
        for(int i=0;i<numberOfServers;i++) {
            servers.get(i).start();
        }
    }

    public void generateNRandomTasks() throws InterruptedException {
        int randServiceTime;
        int randArrivalTime;
        Random random = new Random();
        for(int i=1;i<=numberOfClients;i++){
            randServiceTime = random.nextInt(maxServingTime - minServingTime + 1) + minServingTime;
            randArrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            generatedTasks.add(new Task(i, randArrivalTime, randServiceTime));
        }

        Collections.sort(generatedTasks);
        try {
            scheduler.setTasksList(generatedTasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
