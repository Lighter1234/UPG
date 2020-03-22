package project;

import waterflowsim.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
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
     * Variable representing height of the text
     */
    private int FONT_HEIGHT;

    /**
     * Variable representing whether the points are set or not
     */
    private boolean arePointsSet = false;

    /**
     * Matrix representing cells of the simulation
     */
    private final Cell[][] cells;

    /**
     * Array of points so there's no need for recalculating them again
     */
    private final Point2D[][] POINTS;

    /**
     * Constructor to create a canvas for modeling water flow
     *
     * @param scenario Number of scenario
     */
    public Panel(int scenario){
        this.setPreferredSize(new Dimension(800,600));
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

            this.SIM_WIDTH = deltaX * (AMMOUNT_OF_CELLS_WIDTH);
            this.SIM_HEIGHT = deltaY * (AMMOUNT_OF_CELLS_HEIGHT);

            this.POINTS = new Point2D[AMMOUNT_OF_CELLS_WIDTH][AMMOUNT_OF_CELLS_HEIGHT];
            this.cells = new Cell[AMMOUNT_OF_CELLS_WIDTH][AMMOUNT_OF_CELLS_HEIGHT];

            computeModelDimensions();
        setPoints();


        }


    @Override
    public void paintComponent(Graphics g){
            super.paintComponent(g);
//        System.out.println("Hello");

            Graphics2D g2 = (Graphics2D) g;

            computeModel2WindowTransformation(this.getWidth(), this.getHeight());

            drawWaterFlowState(g2);


    }

    /**
     * Initializes XY-coordinates for "real-world" and saves them into appropriate variables
     */
    private void computeModelDimensions(){

        Vector2D<Double> start = Simulator.getStart();

            this.startXSim = start.x;
            this.startYSim = start.y;

            this.endXSim = SIM_WIDTH + this.startXSim - deltaX;
            this.endYSim = SIM_HEIGHT + this.startYSim - deltaY;

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


   //     System.out.println("Width: " +width + " Height: " + height );

        this.widthOfCanvas = width;
        this.heightOfCanvas = height;

        double scaleX = width/this.SIM_WIDTH;
        double scaleY = height/this.SIM_HEIGHT;

//        this.scale = Math.min(scaleX,
//                scaleY);
//
        if (scaleX < scaleY) {
            scale = scaleX;
            OFFSET_X = 0;
            OFFSET_Y = (height - this.SIM_HEIGHT*scale) / 2;

        } else {
            scale = scaleY;
            OFFSET_Y = 0;
            OFFSET_X = (width - this.SIM_WIDTH*scale) /2;
        }


        this.FONT_HEIGHT = (int)(0.03 * this.getHeight());



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


    /**
     * Method to call others methods to draw the state of the flow of the water
     * @param g
     */
    private void drawWaterFlowState(Graphics2D g){

        drawWaterLayer(g);
        drawWaterSources(g);


    }

    /**
     * Now empty method
     *
     * @param g Graphic context
     */
    private void drawTerrain(Graphics2D g){
        //Is empty now

    }


    /**
     * Goes over the array of the points and draws them on the canvas
     *
     * @param g Graphical context
     */
    private void drawWaterLayer(Graphics2D g){

        g.setColor(new Color(40,100,255));

            for (int i = 0; i < AMMOUNT_OF_CELLS_HEIGHT; i++) {

                for (int j = 0; j < AMMOUNT_OF_CELLS_WIDTH; j++) {

                    if (!cells[j][i].isDry()) {
                        Point2D tmpPoint = model2window(POINTS[j][i]);
                        g.fill(new Rectangle2D.Double(tmpPoint.getX(), tmpPoint.getY(), deltaX * scale, deltaY * scale));
                    }

                }
            }

           this.drawWaterSources(g);

    }


    /**
     * Prepares an array of water sources and the using the method drawWaterFlowLabel()
     * draws it's direction together with name
     *
     * @param g graphical context
     */
    private void drawWaterSources(Graphics2D g){

                WaterSourceUpdater[] wsu = Simulator.getWaterSources();

                g.setColor(Color.BLACK);
                for(int i = 0 ; i < wsu.length ; i++){
//                    System.out.println("i: " + i);
                  int index = wsu[i].getIndex();
                  int x = index % AMMOUNT_OF_CELLS_WIDTH;
                  int y = index / AMMOUNT_OF_CELLS_WIDTH;
                  Point2D tmp = POINTS[x][y];

                  this.drawWaterFlowLabel(tmp, INFO[index].getGradient(), wsu[i].getName(), g);

                }

    }


    /**
     * Calculates the angle depending on the cell gradient and writes the name of the water source and
     * draws the direction its heading
     *
     * @param position position of the cell
     * @param dirFlow gradient of the cell
     * @param name name of the water source
     * @param g graphical context
     */
            private void drawWaterFlowLabel(Point2D position, Vector2D dirFlow, String name, Graphics2D g){

                double x = Math.abs((double)dirFlow.x);
                double y = Math.abs((double)dirFlow.y);
//                System.out.println( " Name: " + name );

                if(!Double.isNaN(x) || !Double.isNaN(y)) {

                  position = model2window(position);


                //Vector created from points (0, heigthOfCanvas) and (widthOfCanvas, heightOfCanvas)
                //Represents the vector aligned with axis x
                double xVector = -this.widthOfCanvas;
                double yVector = 0;

                double theta = Math.acos((xVector* x + yVector * y) /
                        ((Math.hypot(xVector, yVector) * Math.hypot(x,y))
                        ));

                int xP =  (int)position.getX();
                int yP = (int)position.getY();


                    g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, this.FONT_HEIGHT ));

                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    int textWidth = metrics.stringWidth(name);
                    g.translate(xP,yP);
                    g.rotate(theta);

                    g.setColor(Color.BLACK);
//                    g.translate(-textWidth,0);

                    g.scale(-1,-1);

                    g.translate(0, -metrics.getDescent());
                    g.drawString(name, 0,0);
                    g.translate(0, metrics.getDescent());

                    g.scale(-1,-1);

//                    g.translate(textWidth, 0);

                    drawArrow(new Point2D.Double(0, 0), new Point2D.Double(-textWidth, 0), g);

                    g.rotate(-theta);
                    g.translate(-xP ,-yP);

                }else
                    return;

            }


    /**
     * Draws arrow from start to end
     *
     * @param start Starting point
     * @param end ending points
     * @param g graphics context
     */
    private void drawArrow(Point2D start, Point2D end, Graphics2D g){

        //Taken from exercise No.3

                g.setColor(Color.RED);

                double x1 = start.getX();
                    double y1 = start.getY();

                    double x2 = end.getX();
                    double y2 = end.getY();


                // Spocitame slozky vektoru od (x1, y1) k (x2, y2)
                double vx = x2 - x1;
                double vy = y2 - y1;

                // Spocitame vektoru v, tj usecky od (x1, y1) k (x2, y2).
                // K vypoctu druhe mocniny idealne pouzivame nasobeni, ne funkci pow
                // (je mnohem pomalejsi).
                double vLength = Math.sqrt(vx*vx + vy*vy);

                // Z vektoru v udelame vektor jednotkove delky
                double vNormX = vx / vLength;
                double vNormY = vy / vLength;

                // Vektor v protahneme na delku arrowLength
                double vArrowX = vNormX * 4;
                double vArrowY = vNormY * 4;

                // Spocitame vektor kolmy k (vx, vy)
                // Z nej pak odvodime koncove body carek tvoricich sipku.
                double kx = -vArrowY;
                double ky = vArrowX;

                // Upravime delku vektoru k, aby byla sipka hezci
                kx *= 0.5;
                ky *= 0.5;

                g.setStroke(new BasicStroke(3));
                // Cara od (x1, y1) k (x2, y2)
                g.draw(new Line2D.Double(x1, y1, x2, y2));

                // Sipka na konci
                g.draw(new Line2D.Double(x2, y2, x2 - vArrowX + kx, y2 - vArrowY + ky));
                g.draw(new Line2D.Double(x2, y2, x2 - vArrowX - kx, y2 - vArrowY - ky));

            }


    /**
     * Calculates the points and saves them into an array
     */
    private void setPoints() {
        int counter = 0;


        double x = startXSim;
        double y = startYSim;

        for (int i = 0; i < AMMOUNT_OF_CELLS_HEIGHT; i++) {

            for (int j = 0; j < AMMOUNT_OF_CELLS_WIDTH; j++) {

//                Point2D tmpPoint =
             //   tmpPoint = this.model2window(tmpPoint);
                POINTS[j][i] =  new Point2D.Double(x, y);

                this.cells[j][i] = INFO[counter++];

                x += deltaX;

            }
            x = startXSim;

            y += deltaY;
        }


    }




}
