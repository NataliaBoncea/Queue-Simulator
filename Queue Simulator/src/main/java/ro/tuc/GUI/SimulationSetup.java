package ro.tuc.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationSetup extends JFrame{
    private JLabel title = new JLabel("=========== Simulator magazin ===========", SwingConstants.CENTER);
    private JLabel serverL = new JLabel("Numarul de cozi:    ");
    private JLabel taskL = new JLabel("Numarul de clienti: ");
    private JLabel simulationTimeL = new JLabel("Durata simularii (secunde): ");
    private JLabel serviceL1 = new JLabel("Intervalul de servire este intre: ");
    private JLabel serviceL2 = new JLabel("si");
    private JLabel serviceL3 = new JLabel("secunde.");
    private JLabel arrivalL1 = new JLabel("Intervalul de sosire este intre: ");
    private JLabel arrivalL2 = new JLabel("si");
    private JLabel arrivalL3 = new JLabel("secunde.");
    private JLabel strategyL = new JLabel("Strategia de selectie: ");

    private JTextField serverT = new JTextField(5);
    private JTextField taskT = new JTextField(5);
    private JTextField simulationTimeT = new JTextField(5);
    private JTextField serviceMinT = new JTextField(3);
    private JTextField serviceMaxT = new JTextField(3);
    private JTextField arrivalMinT = new JTextField(3);
    private JTextField arrivalMaxT = new JTextField(3);
    private String selectionPolicy[] = {"SHORTEST_QUEUE", "SHORTEST_TIME"};
    private JComboBox<String> strategy = new JComboBox<>(selectionPolicy);
    private JButton start = new JButton("Start");

    private Color light_green = new Color(185, 237, 221);
    private Color bg_color = new Color(183, 183, 183);
    private Color dark_blue = new Color(10, 38, 71);

    public SimulationSetup(){

        JFrame frame = new JFrame ("Simulator magazin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 250);

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        JPanel p6 = new JPanel();
        JPanel p_col1 = new JPanel();
        JPanel p_col2 = new JPanel();
        JPanel p_title = new JPanel();
        JPanel p_button = new JPanel();
        JPanel p = new JPanel();
        JPanel pt = new JPanel();

        Font  f1  = new Font(Font.SERIF, Font.BOLD,  30);
        Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  20);

        title.setFont(f1);
        title.setForeground(light_green);
        p_title.add(title);
        p_title.setBackground(dark_blue);
        p_title.setAlignmentY(Component.CENTER_ALIGNMENT);
        p_title.setPreferredSize(new Dimension(700, 50));


        p1.add(serverL);
        p1.add(serverT);
        p1.setLayout(new FlowLayout());

        p2.add(taskL);
        p2.add(taskT);
        p2.setLayout(new FlowLayout());

        p3.add(simulationTimeL);
        p3.add(simulationTimeT);
        p3.setLayout(new FlowLayout());

        p4.add(arrivalL1);
        p4.add(arrivalMinT);
        p4.add(arrivalL2);
        p4.add(arrivalMaxT);
        p4.add(arrivalL3);
        p4.setLayout(new FlowLayout());

        p5.add(serviceL1);
        p5.add(serviceMinT);
        p5.add(serviceL2);
        p5.add(serviceMaxT);
        p5.add(serviceL3);
        p5.setLayout(new FlowLayout());

        p6.add(strategyL);
        p6.add(strategy);
        p6.setLayout(new FlowLayout());

        p_col1.add(p1);
        p_col1.add(p2);
        p_col1.add(p3);
        p_col1.setLayout(new BoxLayout(p_col1, BoxLayout.Y_AXIS));
        p_col1.setPreferredSize(new Dimension(350, 100));

        p_col2.add(p4);
        p_col2.add(p5);
        p_col2.add(p6);
        p_col2.setLayout(new BoxLayout(p_col2, BoxLayout.Y_AXIS));
        p_col2.setPreferredSize(new Dimension(350, 100));

        p.add(p_col1);
        p.add(p_col2);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        p_button.add(start);
        start.setBackground(light_green);
        start.setForeground(dark_blue);
        start.setFont(f2);

        pt.add(p_title);
        pt.add(p);
        pt.add(p_button);
        pt.setLayout(new BoxLayout(pt, BoxLayout.Y_AXIS));
        pt.setBackground(bg_color);

        frame.setContentPane(pt);
        frame.setVisible(true);
    }

    public JTextField getServerT() {
        return serverT;
    }

    public JTextField getTaskT() {
        return taskT;
    }

    public JTextField getSimulationTimeT() {
        return simulationTimeT;
    }

    public JTextField getServiceMinT() {
        return serviceMinT;
    }

    public JTextField getServiceMaxT() {
        return serviceMaxT;
    }

    public JTextField getArrivalMinT() {
        return arrivalMinT;
    }

    public JTextField getArrivalMaxT() {
        return arrivalMaxT;
    }

    public String getNumberOfServers(){
        return serverT.getText();
    }
    public String getNumberOfTasks(){
        return taskT.getText();
    }
    public String getSimulationTime(){
        return simulationTimeT.getText();
    }
    public String getArrivalMin(){
        return arrivalMinT.getText();
    }
    public String getArrivalMax(){
        return arrivalMaxT.getText();
    }
    public String getServiceMin(){
        return serviceMinT.getText();
    }
    public String getServiceMax(){
        return serviceMaxT.getText();
    }
    public int getStrategy() {
        return strategy.getSelectedIndex();
    }

    public void incorrectValueError(JTextField tf){
        tf.setBackground(Color.RED);
        UIManager.put("OptionPane.background", dark_blue);
        UIManager.put("Panel.background", dark_blue);
        UIManager.put("Button.background", light_green);
        String instructions = """
                Oups! Se pare ca nu ati introdus corect o valoare.
                Asigurati-va ca ati introdus toate valorile numerice corect.""";
        JTextArea area = new JTextArea(instructions);
        area.setFont(new Font("Tahoma", Font.PLAIN, 14));
        area.setBackground(dark_blue);
        area.setForeground(light_green);
        JOptionPane.showMessageDialog(this, area, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }
    public void goodValue(JTextField tf){
        tf.setBackground(Color.white);
    }

    public void incorrectOrderError(JTextField tf1, JTextField tf2){
        tf1.setBackground(Color.RED);
        tf2.setBackground(Color.RED);
        UIManager.put("OptionPane.background", dark_blue);
        UIManager.put("Panel.background", dark_blue);
        UIManager.put("Button.background", light_green);
        String instructions = """
                Oups! Se pare ca nu ati introdus valorile in ordinea corecta.
                Asigurati-va ca ati introdus toate valorile numerice corect.""";
        JTextArea area = new JTextArea(instructions);
        area.setFont(new Font("Tahoma", Font.PLAIN, 14));
        area.setBackground(dark_blue);
        area.setForeground(light_green);
        JOptionPane.showMessageDialog(this, area, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }
    public void startListener(ActionListener listenStartButton){
        start.addActionListener(listenStartButton);
    }
}
