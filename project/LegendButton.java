package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LegendButton extends JButton implements ActionListener {

    /**
     * Panel to work with
     */
    private Panel p;

    /**
     * Creates a button with text "Legend"
     *
     * @param p panel to work with
     */
    public LegendButton(Panel p){
        super("Legend");
        this.p = p;
        this.addActionListener(this);
    }


    /**
     * After click shows up frame with legend of the colors
     *
     * @param e user click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame("Legend");
        double[] heights = p.getIntervals();

        LegendPanel panel = new LegendPanel(heights);


        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
