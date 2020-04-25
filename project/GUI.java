package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private Panel panel;

    private static boolean simulationRunning = true;

    public GUI(Panel panel){
        this.panel = panel;
    }



    private void makeGUI(){
        makeStopButton();


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
