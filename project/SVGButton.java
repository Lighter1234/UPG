package project;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SVGButton extends JButton implements ActionListener {

    private Panel p;

    public SVGButton(Panel p){
        super("SVG");
        this.p = p;
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        SVGGraphics2D g = new SVGGraphics2D(p.getWidth(),p.getHeight());
        p.paint(g);
        try {
        PrintWriter pw = new PrintWriter(new File("SVGFile.svg"));
        pw.println(g.getSVGElement());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}
