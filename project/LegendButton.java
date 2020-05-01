package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LegendButton extends JButton implements ActionListener {

    private Panel p;

    public LegendButton(Panel p){
        super("Legend");
        this.p = p;
        this.addActionListener(this);
    }



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
