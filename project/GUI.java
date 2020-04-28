package project;

import waterflowsim.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private Panel p;

    private JFrame frame;

    private JPanel GUIPanel;

    public static double counter = 0.0;


    private int sim;

    private StartStopButton stBut;

    private MySlider ms;
    private JButton rsBut;

    public GUI(int sim){
        this.sim = sim;

        frame = new JFrame("A18B0307P");
        frame.setLayout(new BorderLayout());
        p = new Panel(sim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(p, BorderLayout.CENTER);

        makeGUI();
    }




    private void makeGUI(){

        GUIPanel = new JPanel();

        //SpeedSlider
        createSpeedSlider();

        //Start/Stop button
        createStartStopButton();

        //Reset button
        createResetButton();

        MyMouseListener mml = new MyMouseListener(p);
//        p.addMouseWheelListener(mml);
//        p.addMouseListener(mml);
//        p.addMouseMotionListener(mml);

        frame.add(GUIPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }

    public void startSimulation(){
        /////////////////////////Simulation/////////////////////////
        Timer timer;
        int timerPeriod = 1000 / 25  ; // Prekreslit okno 25krat za 1000 milisekund
        timer = new Timer(timerPeriod, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(stBut.isSimulationRunning()){

                    double step = Math.round(0.1 * ms.getSimulationSpeed()*1000)/1000.0; // No need for more
                                                                                // than 3 decimal numbers
                    Simulator.nextStep(step);
                    counter += step;

                    if(counter >= 1) {
                        p.refresh();
                        counter = 0.0;
                    }
                    p.repaint();

                }
            }
        });
        timer.start();

    }

    /**
     * Method creates an instance of MySlider to allow configure speed of simulation
     * and adds it into panel
     */
    private void createSpeedSlider() {
        ms = new MySlider();
        ms.addChangeListener(ms);
        GUIPanel.add(ms);
    }


    /**
     * Method creates button to reset zoom
     * and adds it into panel
     */
    private void createResetButton(){
        rsBut = new JButton("Reset");
        rsBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.resetZoom();
            }
        });
        GUIPanel.add(rsBut);
    }

    /**
     * Method creates button to stop or start simulation
     * and adds it into panel
     */
    private void createStartStopButton(){
        stBut = new StartStopButton();
        stBut.addActionListener(stBut);
        GUIPanel.add(stBut);
    }



}
