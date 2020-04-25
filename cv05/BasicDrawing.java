package cv05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class BasicDrawing {


    public static void main(String[] args){

        JFrame frame = new JFrame("A18B0307P");


        
        makeGui(frame);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private static void makeGui(JFrame frame) {
        DrawingPanel panel = new DrawingPanel();

        frame.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(640, 480));
        frame.add(panel, BorderLayout.CENTER);

        JPanel guiP = new JPanel();
        JButton exitB = new JButton("Exit");
        exitB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
//                frame.dispose();
//                frame.dispatchEvent();

            }
        });

        JButton largB = new JButton("Larger");

                largB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            panel.makeStarLarger();
                    }
                });

                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if(e.getKeyChar()== '+'){
                            panel.makeStarLarger();
                        }

                        if(e.getKeyChar()=='r' || e.getKeyChar() == 'R'){
                            panel.resetStar();
                        }

                        return false;
                    }
                });

        guiP.add(largB);
        guiP.add(exitB);

        frame.add(guiP, BorderLayout.SOUTH);


    }

}
