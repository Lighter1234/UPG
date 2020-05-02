package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PolygonButton extends JButton implements ActionListener {

    private Panel p;

    public PolygonButton(Panel p){
        super("Polygon");
        this.p = p;
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.p.changeStatePolygon();

    }
}
