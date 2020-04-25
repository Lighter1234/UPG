package project;

import waterflowsim.Simulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private Panel panel;

    private static boolean simulationRunning = true;

    private int sim;
    public GUI(int sim){
        this.sim = sim;
    }



    private void makeGUI(){
     //   this.panel = panel;

        JFrame frame = new JFrame("A18B0307P");
        Panel p = new Panel(sim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //       p.setPreferredSize(new Dimension(800,600));
        frame.add(p);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Timer timer;
        int timerPeriod = 1000 / 25; // Prekreslit okno 25krat za 1000 milisekund
        long startTime = System.currentTimeMillis();
        timer = new Timer(timerPeriod, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Simulator.nextStep(0.1);
                p.repaint();
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
