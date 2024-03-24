package ro.tuc.Model;

import ro.tuc.BusinessLogic.Scheduler;
import ro.tuc.BusinessLogic.SimulationManager;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private SimulationManager simulation;
    private Scheduler scheduler;

    public Server(SimulationManager simulation, Scheduler scheduler){
        this.tasks = new LinkedBlockingDeque<Task>();
        waitingPeriod = new AtomicInteger(0);
        this.simulation = simulation;
        this.scheduler = scheduler;
    }

    public AtomicInteger getWaitingT() {
        return waitingPeriod;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void addTask (Task newTask) throws InterruptedException {
        tasks.put(newTask);
        scheduler.getTotalTasksProcessed().incrementAndGet();
        waitingPeriod.addAndGet(newTask.getServiceTime());
        scheduler.getTasksList().remove(scheduler.getTasksList().peek());
        simulation.getFrame().newClientOnQueue(scheduler.getServers().indexOf(this), tasks.size() - 1);
        scheduler.getTasksOnQueuesPerTime().incrementAndGet();
    }

    public void makeTask () throws InterruptedException {
        if(tasks.size()!=0){
            waitingPeriod.decrementAndGet();
            scheduler.getTotalWaitingTime().incrementAndGet();
            tasks.peek().setServiceTime(tasks.peek().getServiceTime() - 1);
            simulation.getFrame().changeServingTime(scheduler.getServers().indexOf(this), tasks.peek());
            if(tasks.peek().getServiceTime()<=0) {
                tasks.take();
                simulation.getFrame().removeClient(scheduler.getServers().indexOf(this));
            }
            scheduler.getTasksOnQueuesPerTime().addAndGet(tasks.size());
        }

    }

    public void run(){
        while(scheduler.getFinish().get() == false){
            try {
                timeRun();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(scheduler.getFinish().get() && Thread.currentThread().getName().equals(simulation.getServers().get(0).getName())){
            try {
                scheduler.endSimulation();
                simulation.getLogFile().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void timeRun() throws InterruptedException {
        makeTask();
        Thread.currentThread().sleep(1000);
        scheduler.stillBusy();

        if(Thread.currentThread().getName().equals(simulation.getServers().get(0).getName())){
            scheduler.getSimulationTime().incrementAndGet();
            scheduler.dispatchTask();
            scheduler.getPeakTime();
            System.out.println("Time "+ scheduler.getSimulationTime());
            try {
                simulation.getLogFile().write("Time "+ scheduler.getSimulationTime() + "\n");
                scheduler.printServerStatus();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            scheduler.getTasksOnQueuesPerTime().set(0);
            Thread.currentThread().sleep(70);
        }
        if(!Thread.currentThread().getName().equals(simulation.getServers().get(0).getName())){
            Thread.currentThread().sleep(100);
        }

        if(scheduler.getSimulationTime().get()>=simulation.timeLimit){
            scheduler.getFinish().set(true);
        }
    }
}
