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
     * Variable to create a little space from the corners of the canvas
     */
    private double OFFSET_X;

    /**
     * Variable to create a little space from the corners of the canvas
     */
    private double OFFSET_Y;




    /**
     * Information about regions of the simulation
     */
    private final Cell[] INFO;


    /**
     * Array of points so there's no need for recalculating them again
     */
    private final Point2D[] POINTS;

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
            this.AMMOUNT_OF_CELLS_WIDTH = Math.abs(dimension.x);
            this.AMMOUNT_OF_CELLS_HEIGHT = Math.abs(dimension.y);

            this.deltaX = Math.abs(delta.x);
            this.deltaY = Math.abs(delta.y);

            this.SIM_WIDTH = deltaX * AMMOUNT_OF_CELLS_WIDTH;
            this.SIM_HEIGHT = deltaY * AMMOUNT_OF_CELLS_HEIGHT;

            this.POINTS = new Point2D[INFO.length];


         computeModelDimensions();

        setPoints();

        }


    @Override
    public void paintComponent(Graphics g){
            super.paintComponent(g);
//        System.out.println("Hello");

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

        System.out.println("StartX: " + startXSim  + " startY: " + startYSim
         + " endX: " + endXSim + " endY: " + endYSim
        + " DeltaX: " + deltaX + " deltaY: " + deltaY);


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

        double scaleX = width/this.SIM_WIDTH;
        double scaleY = height/this.SIM_HEIGHT;

//        this.scale = Math.min(scaleX,
//                scaleY);

        if (scaleX < scaleY) {
            scale = scaleX;
            OFFSET_X = 0;
            OFFSET_Y = (this.getHeight() - this.SIM_HEIGHT*scale) / 2;
        } else {
            scale = scaleY;
            OFFSET_X = (this.getWidth() - this.SIM_WIDTH*scale) / 2;
            OFFSET_Y = 0;
        }


    }


    /**
     * Takes point created in "real-world" and models it into canvas
     *
     * @param m Point2D in "real-life"
     * @return remodeled Point2D into canvas
     */
    private Point2D model2window(Point2D m){
        return new Point2D.Double(((m.getX()-startXSim) * this.scale) + OFFSET_X,
                (((m.getY() - startYSim) * this.scale))+ OFFSET_Y) ;
    }


    private void drawWaterFlowState(Graphics2D g){
    //TODO finish

        drawWaterLayer(g);
        drawWaterSources(g);


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


        double tmpStartingX = this.startXSim;
        double tmpStartingY = this.startYSim;

            for(int i = 0 ; i < INFO.length ; i++){

                Cell tmp = INFO[i];

                if(tmp.isDry()){
                    g.setColor(new Color(100,255,100));

                }else{
                    g.setColor(new Color(40,20,255));
                }

//                Point2D tmpPoint = new Point2D.Double(tmpStartingX + (i % AMMOUNT_OF_CELLS_WIDTH) * (this.deltaX)
//                        , tmpStartingY + (i / AMMOUNT_OF_CELLS_HEIGHT) * (this.deltaY) );

                Point2D tmpPoint = POINTS[i];

                tmpPoint = this.model2window(tmpPoint);

//              System.out.println("X: " + tmpPoint.getX() + " Y:" + tmpPoint.getY()
//                        + " deltaX:" + deltaX + " deltaY: "+ deltaY + " scale: " + scale + " startX: "
//                        + startXSim + " startY: "+ startYSim);
                g.draw(new Rectangle2D.Double(tmpPoint.getX(),tmpPoint.getY(), deltaX * scale, deltaY * scale ));

            }

           this.drawWaterSources(g);
            //TODO finish


    }

            private void drawWaterSources(Graphics2D g){
                WaterSourceUpdater[] wsu = Simulator.getWaterSources();

                double tmpStartingX = this.startXSim;
                double tmpStartingY = this.startYSim;


                g.setColor(Color.BLACK);
                for(int i = 0 ; i < wsu.length ; i++){
                  int index = wsu[i].getIndex();

            //      Point2D pt = INFO[index].;

              //    g.drawString(wsu[i].getName(), );

                }




                //TODO finish
    }


            private void drawWaterFlowLabel(Point2D position, Vector2D dirFlow, String name, Graphics2D g){


                //TODO finish

            }


            private void setPoints() {

                double tmpStartingX = this.startXSim;
                double tmpStartingY = this.startYSim;

                for (int i = 0; i < INFO.length; i++) {

                    Point2D tmpPoint = new Point2D.Double(tmpStartingX + (i % AMMOUNT_OF_CELLS_WIDTH) * (this.deltaX)
                            , tmpStartingY + (i / AMMOUNT_OF_CELLS_HEIGHT) * (this.deltaY));

//                    tmpPoint = this.model2window(tmpPoint);

                    POINTS[i] = tmpPoint;

//              System.out.println("X: " + tmpPoint.getX() + " Y:" + tmpPoint.getY()
//                        + " deltaX:" + deltaX + " deltaY: "+ deltaY + " scale: " + scale + " startX: "
//                        + startXSim + " startY: "+ startYSim);

                }


            }




}
