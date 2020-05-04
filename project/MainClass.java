package project;

import waterflowsim.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

/**
 * Class to start the program
 *
 * @author Josef Yassin Saleh
 */
public class MainClass {

    public static void main(String[] args){
        int sim = 0;
        if(args.length == 0){
            sim = 4;
        }else {
            try {
                sim = Integer.parseInt(args[0]);
            }catch(Exception e){
                System.out.println("Je nutne zadat kladny celociselny parametr!");
                System.exit(0);
            }
        }

        int length = Simulator.getScenarios().length;

        if(sim < 0 || sim >= length){
            System.out.println("Je nutne zadat parametr v rozsahu <0, " + length + ")" );
            System.exit(0);
        }

        GUI gui = new GUI(sim);
        gui.startSimulation();


    }
}
