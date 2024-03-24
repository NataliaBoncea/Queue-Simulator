package ro.tuc.Model;

public class Task implements Comparable{
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String toString(){
        return "(" + ID + ", " + arrivalTime + ", " + serviceTime + ") ";
    }

    @Override
    public int compareTo(Object o) {
        return this.arrivalTime - ((Task)o).arrivalTime;
    }
}
