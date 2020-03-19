package project;

import waterflowsim.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;


public class MainClass {

    public static void main(String[] args){
        int sim;
        if(args.length == 0){
            sim = 0;
        }else{
            sim = Integer.parseInt(args[0]);
        }

        JFrame frame = new JFrame("A18B0307P");
        Panel p = new Panel(sim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 //       p.setPreferredSize(new Dimension(800,600));
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
        int timerPeriod = 1000 / 60; // Prekreslit okno 25krat za 1000 milisekund
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
