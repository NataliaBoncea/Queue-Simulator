package ro.tuc.GUI;

import ro.tuc.BusinessLogic.Scheduler;
import ro.tuc.BusinessLogic.SimulationManager;
import ro.tuc.Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class SimulationFrame {
    private JLabel title = new JLabel("============== Simulator magazin =============", SwingConstants.CENTER);
    private JLabel time = new JLabel("Time: 0", SwingConstants.LEFT);
    private ArrayList<JLabel> queues = new ArrayList<>();
    private ArrayList<JLabel> clientsOnWaiting = new ArrayList<>();
    private ArrayList<ArrayList<JLabel>> clientsOnQueue = new ArrayList<>();
    private JPanel p_queue = new JPanel();

    private Color blue = new Color(0, 43, 91);
    private Color green = new Color(21, 152, 149);
    private Color light_green = new Color(185, 237, 221);
    private Color bg_color = new Color(183, 183, 183);
    private Color dark_blue = new Color(10, 38, 71);
    public JFrame frame = new JFrame ("Simulator magazin");

    public SimulationFrame(SimulationManager simulation){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        JPanel p = new JPanel();

        Font  f1  = new Font(Font.SERIF, Font.BOLD,  40);
        Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  10);
        Font  f3  = new Font(Font.SERIF, Font.BOLD,  20);

        title.setFont(f1);
        title.setForeground(light_green);
        title.setBounds(0, 0, 1000, 80);
        title.setBackground(dark_blue);
        title.setOpaque(true);

        time.setFont(f3);
        time.setForeground(blue);
        time.setBounds(10, 400, 1000, 80);

        p_queue.setLayout(null);
        p_queue.setBackground(bg_color);

        for(int i=0; i<simulation.numberOfServers; i++){
            JLabel l = new JLabel(Integer.toString(i+1), SwingConstants.CENTER);
            clientsOnQueue.add(new ArrayList<>());
            l.setBounds(50+i*120, 100, 30, 50);
            l.setBackground(blue);
            l.setOpaque(true);
            l.setForeground(Color.WHITE);
            p_queue.add(l);
            queues.add(l);
        }

        for(int i=0; i<simulation.numberOfClients; i++){
            JLabel l = new JLabel(Integer.toString(i), SwingConstants.CENTER);
            l.setBounds(800+i*70, 300, 50, 30);
            l.setBackground(green);
            l.setOpaque(true);
            l.setFont(f2);
            p_queue.add(l);
            clientsOnWaiting.add(l);
        }

        p_queue.add(title);
        p_queue.add(time);
        p.add(p_queue);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        frame.setContentPane(p);
    }

    public void newClientOnQueue(int queue, int posOnQueue){
        clientsOnWaiting.get(0).setBounds(90+queue*120, 100+posOnQueue*40, 50, 30);
        clientsOnQueue.get(queue).add(clientsOnWaiting.get(0));
        clientsOnWaiting.remove(0);
        for(int i=0; i<clientsOnWaiting.size(); i++){
            clientsOnWaiting.get(i).setBounds(800+i*70, 300, 50, 30);
        }
    }
    public void removeClient(int queue){
        clientsOnQueue.get(queue).get(0).setBounds(-100, -100, 50, 30);
        clientsOnQueue.get(queue).remove(0);
        for(int i=0; i<clientsOnQueue.get(queue).size(); i++){
            clientsOnQueue.get(queue).get(i).setBounds(90+queue*120, 100+i*40, 50, 30);
        }

    }

    public void changeServingTime(int queue, Task task){
        clientsOnQueue.get(queue).get(0).setText(task.toString());
    }
    public void changeTime(int time){
        this.time.setText("Time: " + time);
    }
    public void showFinalLog(int time, float averageWaitingTime, float averageServiceTime, int peakTime){
        this.time.setText("Time: " + time + "   ||   Average waiting time: " + averageWaitingTime +  "   ||   Average service time: " + averageServiceTime + "   ||   Peak Time: " + peakTime);
    }
    public void clientText(LinkedList<Task> tasks){
        for(int i=0; i<clientsOnWaiting.size(); i++){
            clientsOnWaiting.get(i).setText(tasks.get(i).toString());
        }
    }
}
