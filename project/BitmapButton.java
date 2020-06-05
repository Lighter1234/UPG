package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BitmapButton extends JButton implements ActionListener {

    /**
     * Panel to work with
     */
    private Panel p;

    /**
     * Amount of columns, to show space in text field
     */
    private final int COLUMNS = 4;

    /**
     * Creates a button with text "Bitmap"
     *
     * @param p panel to work with
     */
    public BitmapButton(Panel p){
        super("Bitmap");
        this.p = p;
        this.addActionListener(this);
    }

    /**
     * On user click, shows a frame that has text fields to input height and width and
     * after confirming, checks if the input values are integer
     *
     * @param e user click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame f = new JFrame();
        JPanel panel = new JPanel();


        JTextField width = new JTextField(COLUMNS);
        JTextField height = new JTextField(COLUMNS);

        JLabel widthL = new JLabel("Width:");
        JLabel heightL = new JLabel("Heigth:");

        widthL.setLabelFor(width);
        heightL.setLabelFor(height);

        JButton confirm = new JButton("Confirm");

        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    int inputWidth = Integer.parseInt(width.getText());
                    int inputHeight =  Integer.parseInt(height.getText());

                    if(inputHeight != 0 && inputWidth != 0) {
                        p.exportToBitmap(inputWidth, inputHeight);
                        JOptionPane.showMessageDialog(new JFrame(), "Ok");
                        f.dispose();
                    }else{
                        JOptionPane.showMessageDialog( new JFrame(),"Need to input non-zero integer");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog( new JFrame(),"Need to input integer");

                }
            }
        });

        panel.add(widthL);
        panel.add(width);

        panel.add(heightL);
        panel.add(height);

        panel.add(confirm);

        f.add(panel);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }
}
