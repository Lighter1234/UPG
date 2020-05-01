package project;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LegendPanel extends JPanel {

    /**
     * Array keeping height intervals
     */
    double[] heightIntervals;

    /**
     * Creates an instance of legend panel
     *
     * @param heightIntervals height intervals
     */
    public LegendPanel(double[] heightIntervals){
        this.heightIntervals = heightIntervals;
        this.setPreferredSize(new Dimension(640, 100));
    }


    /**
     * Paints the legend
     *
     * @param g graphical context
     */
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        float division = 1f/(heightIntervals.length);   // + water

        float max = (float)this.heightIntervals[heightIntervals.length-1];

        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        for(int i = 0 ; i < heightIntervals.length - 1 ; i++){

            float curr = (float)this.heightIntervals[i];
            float difference = curr / max;

            g.setColor(Color.getHSBColor(difference*0.3f, difference * 0.5f , difference));

            g2.fill(new Rectangle2D.Double(this.getWidth() *i * division, 0,
                    this.getWidth() * division, this.getHeight()));


            g2.setColor(Color.getHSBColor(1/difference*0.3f, 1/difference * 0.5f , 1/difference));

            g2.drawString(Math.round(curr * 100f)/100f + "-"
                            + Math.round(heightIntervals[i+1]*100f)/100f + " m",
                    (this.getWidth() * i * division) + (this.getWidth() * division / 5f), this.getHeight() * 0.5f );
        }

        g.setColor(Color.blue);

        g2.fill(new Rectangle2D.Double(this.getWidth() * (heightIntervals.length - 1) * division, 0,
                this.getWidth() * division, this.getHeight()));


        g2.setColor(Color.white);

        g2.drawString("Water", (this.getWidth() * (heightIntervals.length - 1) * division) + (this.getWidth() * division / 5f),
                this.getHeight() * 0.5f );

    }

}
