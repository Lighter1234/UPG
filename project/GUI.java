package project;

import waterflowsim.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private Panel p;

    private static boolean simulationRunning = true;

    private JFrame frame;


    private int sim;
    public GUI(int sim){
        this.sim = sim;

        frame = new JFrame("A18B0307P");
        frame.setLayout(new BorderLayout());
        p = new Panel(sim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(p, BorderLayout.CENTER);

        makeGUI();
    }




    private void makeGUI(){

        JPanel GUIPanel = new JPanel();



        //Start/Stop button
        StartStopButton stBut = new StartStopButton();
        stBut.addActionListener(stBut);
        GUIPanel.add(stBut);

        frame.add(GUIPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        /////////////////////////Simulation/////////////////////////
        Timer timer;
        int timerPeriod = 1000 / 25; // Prekreslit okno 25krat za 1000 milisekund
        timer = new Timer(timerPeriod, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(stBut.isSimulationRunning()){
                    Simulator.nextStep(0.1);
                    p.repaint();
                }
            }
        });
        timer.start();


    }


    private void makeStopButton() {
        JButton stopB = new JButton("Stop");
        stopB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


}
