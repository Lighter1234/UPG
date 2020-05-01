package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetButton extends JButton implements ActionListener {

    /**
     * Panel to reset the zoom for
     */
    private Panel p;

    /**
     * Creates a button that has text "Reset" on it
     *
     * @param p Panel to work with
     */
    public ResetButton(Panel p){
        super("Reset");
        this.p = p;
        this.addActionListener(this);
    }

    /**
     * Resets the zoom of simulation
     *
     * @param e click of the user
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        p.resetZoom();
    }
}
