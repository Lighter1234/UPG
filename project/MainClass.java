package project;

import waterflowsim.*;

import javax.swing.*;
import java.awt.*;

public class MainClass {

    public static void main(String[] args){

        JFrame frame = new JFrame("Hello");
        Panel p = new Panel(2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setPreferredSize(new Dimension(800,600));
        frame.add(p);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
