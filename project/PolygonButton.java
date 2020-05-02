package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PolygonButton extends JButton implements ActionListener {

    /**
     * Panel to work with
     */
    private Panel p;

    /**
     * Creates a button with text "Polygon"
     *
     * @param p panel to work with
     */
    public PolygonButton(Panel p){
        super("Polygon");
        this.p = p;
        this.addActionListener(this);
    }

    /**
     * After clicking changes the state to "choosing polygon"
     *
     * @param e mouse click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.p.changeStatePolygon();

    }
}
