package project;

import waterflowsim.*;

import javax.swing.*;

public class MainClass {

    public static void main(String[] args){

        JFrame frame = new JFrame("Hello");
        Panel p = new Panel(0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(p);
        frame.pack();
        frame.setVisible(true);

    }
}
