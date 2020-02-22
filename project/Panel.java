package project;

import waterflowsim.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Class for drawing of the simulation on the canvas
 *
 * @author Josef Yassin Saleh
 */
public class Panel extends JPanel {

    /**
     * Instance of the simulator
     */
    private Simulator sim;

    /**
     * X-coordinate of the left corner of the "real-world"
     */
    private double startXSim;

    /**
     * Y-coordinate of the left corner of the "real-world"
     */
    private double startYSim;

    /**
     * X-coordinate of the right corner of the "real-world"
     */
    private double endXSim;

    /**
     * Y-coordinate of the right corner of the "real-world"
     */
    private double endYSim;

    /**
     * Constant that defines width of the "real-world"
     */
    private final int SIM_WIDTH;

    /**
     * Constant that defines height of the "real-world"
     */
    private final int SIM_HEIGHT;

    /**
     * Defines width of canvas
     */
    private double widthOfCanvas;

    /**
     * Defines width of canvas
     */
    private double heightOfCanvas;

    /**
     * Defines scale of the simulation
     */
    private double scale;





    /**
     * Information about regions of the simulation
     */
    private final Cell[] INFO;


    /**
     * Constructor to create a canvas for modeling water flow
     *
     * @param sim Instance of simulation
     */
    public Panel(Simulator sim){

            this.sim = sim;
            this.INFO = this.sim.getData();

            this.SIM_HEIGHT = Simulator.getDimension().x;
            this.SIM_WIDTH = Simulator.getDimension().y;

            computeModelDimensions();



        }


    @Override
    public void paint(Graphics g){

            Graphics2D g2 = (Graphics2D) g;

            computeModel2WindowTransformation(this.getWidth(),this.getHeight());

            drawWaterFlowState(g2);


    }

    /**
     * Initializes XY-coordinates for "real-world" and saves them into appropriate variables
     */
    private void computeModelDimensions(){

        Vector2D<Double> start = Simulator.getStart();
        Vector2D<Double> delta = Simulator.getDelta();

            this.startXSim = start.x;
            this.startYSim = start.y;

            this.endXSim = delta.x * SIM_WIDTH + this.startXSim;
            this.endYSim = delta.y * SIM_HEIGHT + this.startYSim;

    }


    /**
     * Initializes variables for width and height of the canvas and creates a scale for modeling
     * on the canvas from the "real-world"
     *
     * @param width variable to initialize width of the canvas
     * @param height variable to initialize height of the canvas
     */
    private void computeModel2WindowTransformation(int width, int height){

        this.widthOfCanvas = width;
        this.heightOfCanvas = height;

        this.scale = Math.max(this.endXSim / this.widthOfCanvas,
                        this.endYSim/ this.heightOfCanvas);

    }


    /**
     * Takes point created in "real-world" and models it into canvas
     *
     * @param m Point2D in "real-life"
     * @return remodeled Point2D into canvas
     */
    private Point2D model2window(Point2D m){
        return new Point2D.Double(m.getX() * this.scale,
                m.getY() * this.scale - this.heightOfCanvas);
    }


    private void drawWaterFlowState(Graphics2D g){




    }

    /**
     * Now empty method
     *
     * @param g Graphic context
     */
    private void drawTerrain(Graphics2D g){
        //Is supposed to be empty

    }



    private void drawWaterLayer(Graphics2D g){
        //TODO finish

    }








}
