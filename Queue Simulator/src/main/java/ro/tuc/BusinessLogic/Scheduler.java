package ro.tuc.BusinessLogic;

import ro.tuc.Model.Server;
import ro.tuc.Model.Task;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    private LinkedList<Server> servers;
    private BlockingQueue<Task> tasksList;
    private int maxNoServers;
    private int maxTasksPerServer;
    private int totalServiceTime = 0;
    private int totalTasks = 0;
    private Strategy strategy;

    private AtomicInteger simulationTime;
    private AtomicInteger totalWaitingTime;
    private AtomicInteger totalTasksProcessed;
    private AtomicBoolean finish;
    private AtomicInteger tasksOnQueuesPerTime;
    private AtomicInteger peakTime;
    private AtomicInteger peakTimeTasks;
    private SimulationManager simulation;

    public enum SelectionPolicy{
        SHORTEST_QUEUE, SHORTEST_TIME;
    }

    public Scheduler(int maxNoServers, int maxTasksPerServer, SimulationManager simulation){
        servers = new LinkedList<>();
        tasksList = new LinkedBlockingDeque<>();
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.simulation = simulation;

        this.simulationTime = new AtomicInteger(-1);
        finish = new AtomicBoolean(false);
        tasksOnQueuesPerTime = new AtomicInteger(0);
        totalWaitingTime = new AtomicInteger(0);
        totalTasksProcessed = new AtomicInteger(0);
        peakTime = new AtomicInteger(0);
        peakTimeTasks = new AtomicInteger(0);
    }

    public BlockingQueue<Task> getTasksList() {
        return tasksList;
    }

    public AtomicInteger getSimulationTime() {
        return simulationTime;
    }

    public AtomicInteger getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public AtomicInteger getTotalTasksProcessed() {
        return totalTasksProcessed;
    }

    public AtomicBoolean getFinish() {
        return finish;
    }

    public AtomicInteger getTasksOnQueuesPerTime() {
        return tasksOnQueuesPerTime;
    }

    public void setTasksList(LinkedList<Task> tasksList) throws InterruptedException {
        for(Task t: tasksList){
            this.tasksList.put(t);
            totalServiceTime += t.getServiceTime();
        }
        totalTasks = tasksList.size();
        simulation.getFrame().clientText(tasksList);
    }

    public LinkedList<Server> getServers() {
        return servers;
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new TimeStrategy();
        }
    }

    public void dispatchTask() throws InterruptedException {
        changeStrategy(simulation.selectionPolicy);
        while(tasksList.size()>0 && tasksList.peek().getArrivalTime()<=simulationTime.get()) {
            strategy.addTaskByStrategy(servers, tasksList.peek(), maxTasksPerServer, simulation.getFrame());
        }
    }
    public void getPeakTime(){
        if(peakTimeTasks.get() < tasksOnQueuesPerTime.get()){
            peakTimeTasks.set(tasksOnQueuesPerTime.get());
            peakTime.set(simulationTime.get());
        }
    }

    public void endSimulation() throws IOException {
        simulation.getFrame().showFinalLog(simulationTime.get(), (float)totalWaitingTime.get()/totalTasksProcessed.get(), (float)totalServiceTime/totalTasks, peakTime.get());
        System.out.println("Peak time: " + peakTime.get());
        System.out.println("Average waiting time: " + (float)totalWaitingTime.get()/totalTasksProcessed.get());
        System.out.println("Average service time: " + (float)totalServiceTime/totalTasks);
        simulation.getLogFile().write("--------------------------------------------------------------------------------------------------------------------\n");
        simulation.getLogFile().write("Peak time: " + peakTime.get() + "\n");
        simulation.getLogFile().write("Average waiting time: " + (float)totalWaitingTime.get()/totalTasksProcessed.get() + "\n");
        simulation.getLogFile().write("Average service time: " + (float)totalServiceTime/totalTasks + "\n");
    }

    public void printServerStatus() throws IOException {
        System.out.println("Tasks on waiting:");
        simulation.getLogFile().write("Tasks on waiting:\n");
        for(Task g: tasksList){
            System.out.println(g);
            simulation.getLogFile().write(g.toString()+", ");
        }
        simulation.getLogFile().write("\n");
        int i = 1;
        for(Server s: servers) {
            if(s.getTasks().size()>0){
                System.out.println("Queue "+ i + ": ");
                simulation.getLogFile().write("Queue "+ i+": ");
                for (Task t : s.getTasks()) {
                    System.out.println(t + ", ");
                    simulation.getLogFile().write(t.toString() + ", ");
                }
                simulation.getLogFile().write("\n");
            }
            else{
                System.out.println("Queue "+ i+": closed");
                simulation.getLogFile().write("Queue "+ i+": closed\n");
            }
            i++;
        }
        simulation.getLogFile().write("\n\n");
        simulation.getFrame().changeTime(simulationTime.get());
    }

    public void stillBusy(){
        boolean busy = false;
        for(Server s: servers){
            if(s.getWaitingT().get()!=0){
                busy = true;
            }
        }
        if(tasksList.size()==0 && busy == false){
            finish.set(true);
        }
    }
}
