package project;

import waterflowsim.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    /**
     * Instance of panel containing simulation
     */
    private Panel p;

    /**
     * Instance of frame that keeps all the GUI
     */
    private JFrame frame;

    /**
     * Panel with GUI components
     */
    private JPanel GUIPanel;

    /**
     * Counter of simulation seconds
     */
    public static double counter = 0.0;

    /**
     * Number of scenario
     */
    private int sim;

    /**
     * Instance of button to start/stop simulation
     */
    private StartStopButton stBut;

    /**
     * Instance of slider to change speed of simulation
     */
    private MySlider ms;

    /**
     * Instance of button to reset zoom and pan
     */
    private ResetButton rsBut;

    /**
     * Constructor takes which simulation to start and prepares frame and panel,
     * then calls method makeGUI()
     *
     * @param sim which simulation to start
     */
    public GUI(int sim){
        this.sim = sim;

        frame = new JFrame("A18B0307P");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeGUI();
    }


    /**
     * Method creates GUI panel
     */
    private void makeGUI(){

        GUIPanel = new JPanel();
        p = new Panel(sim);


        //SpeedSlider
        createSpeedSlider();

        //Start/Stop button
        createStartStopButton();

        //Reset button
        createResetButton();

        //Mouse Listener
        createMyMouseListener();

        //Legend button
        createLegendButton();

        //Print button
        createPrintButton();

        //Polygon button
        createPolygonButton();

        //Bitmap button
//        createBitmapButton();

        frame.add(GUIPanel, BorderLayout.SOUTH);

        frame.add(p, BorderLayout.CENTER);


        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    /**
     * Creates a button for export to bitmap
     * and adds it into panel
     */
    private void createBitmapButton() {
        BitmapButton bmpB = new BitmapButton(p);
        GUIPanel.add(bmpB);
    }


    /**
     * Starts the simulation
     */
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

                    if(p.hasFreeMemory() &&counter >= 1) {
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
     * Creates a polygon button and adds it into GUI panel
     */
    private void createPolygonButton() {
        PolygonButton polyB = new PolygonButton(p);
        GUIPanel.add(polyB);
    }

    /**
     * Creates a print button and adds it into GUI panel
     */
    private void createPrintButton() {
        PrintButton pb = new PrintButton(p);
        GUIPanel.add(pb);
    }

    /**
     * Creates legend button and adds it to the GUI panel
     */
    private void createLegendButton() {
        LegendButton lb = new LegendButton(p);
        GUIPanel.add(lb);
    }

    /**
     * Creates a mouse listener for panel
     */
    private void createMyMouseListener(){
        MyMouseListener mml = new MyMouseListener(p);
    }

    /**
     * Method creates an instance of MySlider to allow configure speed of simulation
     * and adds it into panel
     */
    private void createSpeedSlider() {
        ms = new MySlider();
        GUIPanel.add(ms);
    }


    /**
     * Method creates button to reset zoom
     * and adds it into panel
     */
    private void createResetButton(){
        rsBut = new ResetButton(p);
        GUIPanel.add(rsBut);
    }

    /**
     * Method creates button to stop or start simulation
     * and adds it into panel
     */
    private void createStartStopButton(){
        stBut = new StartStopButton();
        GUIPanel.add(stBut);
    }



}
