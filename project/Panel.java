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
     * @param scenario Number of scenario
     */
    public Panel(int scenario){

            Simulator.runScenario(scenario);

            this.INFO = Simulator.getData();

            //Getting info about Simulation
            Vector2D<Double> delta = Simulator.getDelta();
            Vector2D<Integer> dimension = Simulator.getDimension();

            //Setting up parameters for drawing on canvas from simulation
            this.AMMOUNT_OF_CELLS_WIDTH = dimension.x;
            this.AMMOUNT_OF_CELLS_HEIGHT = dimension.y;

            this.deltaX = Math.abs(delta.x);
            this.deltaY = Math.abs(delta.y);

            this.SIM_WIDTH = deltaX * AMMOUNT_OF_CELLS_WIDTH;
            this.SIM_HEIGHT = deltaY * AMMOUNT_OF_CELLS_HEIGHT;

            computeModelDimensions();



        }


    @Override
    public void paintComponent(Graphics g){

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
            this.startYSim = -start.y;

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

        this.scale = Math.min(width/this.SIM_WIDTH,
                height/this.SIM_HEIGHT);
//TODO control


    }


    /**
     * Takes point created in "real-world" and models it into canvas
     *
     * @param m Point2D in "real-life"
     * @return remodeled Point2D into canvas
     */
    private Point2D model2window(Point2D m){
        return new Point2D.Double(((m.getX()-startXSim) * this.scale)-(startXSim*this.scale),
                (this.heightOfCanvas - (m.getY() - startYSim) * this.scale) + (startYSim*this.scale) );
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

                Cell tmp = INFO[i];

                double tmpStartingX = this.startXSim;
                double tmpStartingY = this.startYSim;

                if(tmp.isDry()){
                    g.setColor(new Color(20,255,20));

                }else{
                  //  System.out.println("Here");
                    g.setColor(new Color(40,20,255));
                }
                Point2D tmpPoint = new Point2D.Double(tmpStartingX + (i % AMMOUNT_OF_CELLS_WIDTH) * this.deltaX
                        , tmpStartingY + (i / AMMOUNT_OF_CELLS_HEIGHT) * this.deltaY);

                tmpPoint = this.model2window(tmpPoint);

                System.out.println("X: " + tmpPoint.getX() + " Y:" + tmpPoint.getY()
                        + " deltaX:" + deltaX + " deltaY: "+ deltaY + " scale: " + scale );
                g.draw(new Rectangle2D.Double(tmpPoint.getX(),tmpPoint.getY(), deltaX, deltaY ));

            }

           this.drawWaterSources(g);
            //TODO finish


    }

            private void drawWaterSources(Graphics2D g){



                //TODO finish
    }


            private void drawWaterFlowLabel(Point2D position, Vector2D dirFlow, String name, Graphics2D g){


                //TODO finish

            }








}
