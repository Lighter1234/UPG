package project;

import waterflowsim.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.Timer;
//import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;


public class MainClass {

    public static void main(String[] args){

        JFrame frame = new JFrame("Hello");
        Panel p = new Panel(3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setPreferredSize(new Dimension(800,600));
        frame.add(p);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        java.util.Timer myTime = new Timer();
//        myTime.scheduleAtFixedRate(new TimerTask() {
//
//            @Override
//            public void run() {
//                Simulator.nextStep(.1);
//                p.repaint();
//
//            }
//        }, 0, 100);
        Timer timer;
        int timerPeriod = 1000 / 25; // Prekreslit okno 25krat za 1000 milisekund
        long startTime = System.currentTimeMillis();
        timer = new Timer(timerPeriod, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                drawingPanel.setTime((System.currentTimeMillis() - startTime) / 1000.0);
                Simulator.nextStep(.1);
                p.repaint();
            }
        });
        timer.start();


    }
}
