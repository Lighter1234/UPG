package project;

import waterflowsim.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
    private final double SIM_WIDTH;

    /**
     * Constant that defines height of the "real-world"
     */
    private final double SIM_HEIGHT;

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
     * Defines width of one cell
     */
    private double deltaX;

    /**
     * Defines height of one cell
     */
    private double deltaY;

    /**
     * Ammount of cells in a row
     */
    private final int AMMOUNT_OF_CELLS_WIDTH;

    /**
     * Ammount of cells in a column
     */
    private  final int AMMOUNT_OF_CELLS_HEIGHT;




    /**
     * Information about regions of the simulation
     */
    private final Cell[] INFO;


    /**
     * Constructor to create a canvas for modeling water flow
     *
     * @param sim Instance of simulation
     */
    public Panel(int scenario){

        Simulator.runScenario(scenario);
            this.INFO = Simulator.getData();

            Vector2D<Double> delta = Simulator.getDelta();
            Vector2D<Integer> dimension = Simulator.getDimension();

            this.AMMOUNT_OF_CELLS_WIDTH = dimension.x;
            this.AMMOUNT_OF_CELLS_HEIGHT = dimension.y;

            this.deltaX = delta.x;
            this.deltaY = delta.y;

            this.SIM_WIDTH = deltaX * AMMOUNT_OF_CELLS_WIDTH;
            this.SIM_HEIGHT = deltaY * AMMOUNT_OF_CELLS_HEIGHT;

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

            this.startXSim = start.x;
            this.startYSim = start.y;

            this.endXSim = SIM_WIDTH + this.startXSim;
            this.endYSim = SIM_HEIGHT + this.startYSim;

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
    //TODO finish

        drawWaterLayer(g);



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

            for(int i = 0 ; i < INFO.length ; i++){

                Cell tmp = INFO[0];

                double tmpStartingX = this.startXSim;
                double tmpStartingY = this.startYSim;

                if(tmp.isDry()){
                    g.setColor(new Color(20,20,20));

                }else{
                    g.setColor(new Color(40,20,100));
                }
                g.draw(new Rectangle2D.Double(tmpStartingX + (i % AMMOUNT_OF_CELLS_WIDTH) * this.deltaX
                        , tmpStartingY + (i / AMMOUNT_OF_CELLS_HEIGHT) * this.deltaY, deltaX, deltaY));

            }

            //TODO finish


    }








}
