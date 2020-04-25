package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartStopButton extends JButton implements ActionListener {

    /**
     * Array containing names for the button depending on the state of simulation
     */
    private static final String[] BUTTON_NAMES = {"Stop","Start"};

    /**
     * Boolean telling whether the simulation is paused or not
     */
    private boolean simulationRunning = true;

    /**
     * Initialization of the button
     */
    public StartStopButton(){
        super(BUTTON_NAMES[0]);   //Simulation is running
    }


    /**
     * When user clicks the button simulation stops and button changes text
     *
     * @param e click from user
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.simulationRunning = !this.simulationRunning;
        this.setText(BUTTON_NAMES[this.simulationRunning ? 0 : 1]);
    }

    /**
     * Is simulation running?
     *
     * @return true if it is, false if not
     */
    public boolean isSimulationRunning(){
        return this.simulationRunning;
    }
}
