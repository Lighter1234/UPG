package project;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.HashMap;
import java.util.Hashtable;


public class MySlider extends JSlider implements ChangeListener {

    private double simulationSpeed;

    private final Hashtable<Integer, JLabel> labels = new Hashtable<>();


    public MySlider(){
        super(JSlider.HORIZONTAL, 1, 200, 50);  //Chosen values, cannot use final attributes
        this.simulationSpeed = 1.0;                               //because super constructor is being called

        labels.put(new Integer(1),new JLabel("Min"));
        labels.put(new Integer(200),new JLabel("Max"));
        labels.put(new Integer(50), new JLabel("Norm"));
        this.setLabelTable(labels);

        this.setToolTipText(this.simulationSpeed + "x faster");
        this.setMajorTickSpacing(this.getHeight()/2);
        this.setMinorTickSpacing(this.getHeight()/5);
        this.setPaintTicks(true);
        this.setPaintLabels(true);



    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        this.simulationSpeed = source.getValue()/50.0;
        if(this.simulationSpeed >= 1) {
            this.setToolTipText(this.simulationSpeed + "x faster");
        }else{
            this.setToolTipText((this.simulationSpeed) + "x slower");
        }

    }

    public double getSimulationSpeed(){
        return this.simulationSpeed;
    }
}
