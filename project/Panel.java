package project;

import waterflowsim.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

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
    private double endX;

    /**
     * Y-coordinate of the right corner of the "real-world"
     */
    private double endY;

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
     * Matrix representing cells of the simulation
     */
    private final Cell[][] cells;

    /**
     * Array of points so there's no need for recalculating them again
     */
    private final Point2D[][] POINTS;

    /**
     * Constant to create offset for arrows on the edge
     */
    private final int ARROW_OFFSET = 10;

    /**
     * Constant representing left edge for both X axises
     */
    private final int LEFT_EDGE_OFFSET = 10;

    /**
     * Constant representing right edge for both X axises
     */
    private final double RIGHT_EDGE_OFFSET = 0.9;      // 7/8

    /**
     * Data stored about simulation
     */
    private ArrayList<Data> data = new ArrayList<>();

    /**
     * Counter for seconds of the simulation
     */
    private int counterOfSeconds = 0;

    /**
     * Constant to zoom
     */
    private final double ZOOM_IN_CONSTANT = 1.1;

    /**
     * Constant to zoom out
     */
    private final double ZOOM_OUT_CONSTANT = 0.9;

    /**
     * Variable to represent zoom in/out
     */
    private double zoom = 1;

    /**
     * Array of cell areas
     */
    private Rectangle2D[][] areas;

    /**
     * Start position of mouse drag x-axis
     */
    private double xRect;

    /**
     * Start position of mouse drag on y-axis
     */
    private double yRect;

    /**
     * Zoom offset for x axis
     */
    private double zoomOffsetX;

    /**
     * Zoom offset for y axis
     */
    private double zoomOffsetY;

    /**
     * Pan offset for x axis
     */
    private double panOffsetX;

    /**
     * Pan offset for y axis
     */
    private double panOffsetY;


    /**
     * Constructor to create a canvas for modeling water flow
     *
     * @param scenario Number of scenario
     */
    public Panel(int scenario){
        this.setPreferredSize(new Dimension(600,480));
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
            this.areas = new Rectangle2D[AMMOUNT_OF_CELLS_WIDTH][AMMOUNT_OF_CELLS_HEIGHT];

            computeModelDimensions();

            setPoints();

        }


    /**
     * Method for drawing objects on the canvas
     *
     * @param g graphical context
     */
    @Override
    public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            computeModel2WindowTransformation(this.getWidth(), this.getHeight());

            g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, this.FONT_HEIGHT ));

            drawWaterFlowState(g2);

    }

    /**
     * Initializes XY-coordinates for "real-world" and saves them into appropriate variables
     */
    private void computeModelDimensions(){

        Vector2D<Double> start = Simulator.getStart();

            this.startXSim = start.x;
            this.startYSim = start.y;

            this.endX = SIM_WIDTH + this.startXSim;
            this.endY = SIM_HEIGHT + this.startYSim;

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

        scale = Math.min(scaleX, scaleY);

        OFFSET_X = (width - this.SIM_WIDTH*scale) /2;
        OFFSET_Y = (height - this.SIM_HEIGHT*scale) / 2;

        this.FONT_HEIGHT = (int)(0.03 * height);

        this.startX = width/2;
        this.startY = height/2;

        setAreas();

    }




    /**
     * Takes point created in "real-world" and models it into canvas
     *
     * @param m Point2D in "real-life"
     * @return remodeled Point2D into canvas
     */
    private Point2D model2window(Point2D m){
        return new Point2D.Double(((m.getX()-startXSim) * this.scale * this.zoom) + OFFSET_X /*+ zoomOffsetX */+ panX,
                (((m.getY() - startYSim) * this.scale * this.zoom))+ OFFSET_Y /*+ zoomOffsetY*/ + panY) ;
    }


    /**
     * Method to call others methods to draw the state of the flow of the water
     * @param g graphical context
     */
    private void drawWaterFlowState(Graphics2D g){

        drawWaterLayer(g);


    }

    /**
     * Now empty method
     *
     * @param g Graphic context
     */
    private void drawTerrain(Graphics2D g){
    //Empty

    }


    /**
     * Goes over the array of the points and draws them on the canvas
     *
     * @param g Graphical context
     */
    private void drawWaterLayer(Graphics2D g){

            for (int i = 0; i < AMMOUNT_OF_CELLS_HEIGHT; i++) {

                for (int j = 0; j < AMMOUNT_OF_CELLS_WIDTH; j++) {
//                    Point2D tmpPoint = model2window(POINTS[j][i]);

                    if (!cells[j][i].isDry()) {

                        g.setColor(new Color(0f,0f,1f));

                    }else{
                        g.setColor(new Color(0, (int)cells[j][i].getTerrainLevel(), 0));
                    }
//                    g.fill(new Rectangle2D.Double(tmpPoint.getX(), tmpPoint.getY(),
//                            deltaX * scale, deltaY * scale));
                    g.fill(areas[j][i]);
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
                  int index = wsu[i].getIndex();
                  int x = index % AMMOUNT_OF_CELLS_WIDTH;
                  int y = index / AMMOUNT_OF_CELLS_WIDTH;

                  //to create an offset for arrow that is on the edge
                  if(x < AMMOUNT_OF_CELLS_WIDTH/LEFT_EDGE_OFFSET){
                      x += AMMOUNT_OF_CELLS_WIDTH/ARROW_OFFSET;
                  }else if(x >= (AMMOUNT_OF_CELLS_WIDTH * RIGHT_EDGE_OFFSET)){
                      x -= AMMOUNT_OF_CELLS_WIDTH/ARROW_OFFSET;
                  }
                    if(y < AMMOUNT_OF_CELLS_HEIGHT/LEFT_EDGE_OFFSET){
                      y+= AMMOUNT_OF_CELLS_HEIGHT/ARROW_OFFSET;
                  }else if(y >= (AMMOUNT_OF_CELLS_HEIGHT * RIGHT_EDGE_OFFSET)){
                      y-= AMMOUNT_OF_CELLS_HEIGHT/ARROW_OFFSET;
                  }

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
            private void drawWaterFlowLabel(Point2D position, Vector2D<Double> dirFlow, String name, Graphics2D g){

                double x =dirFlow.x;
                double y =dirFlow.y;

                if((!Double.isNaN(x) || !Double.isNaN(y) )) {

                  position = model2window(position);

                //Vector created from points (0, heigthOfCanvas) and (widthOfCanvas, heightOfCanvas)
                //Represents the vector aligned with axis x
                double xVector = this.widthOfCanvas;
                double yVector = 0;


                double theta = Math.acos(
                        (xVector* x + yVector * y) /
                        ((Math.hypot(xVector, yVector) * Math.hypot(x,y))
                        )) ;


                if(dirFlow.y < 0 ){
                    theta *= -1;
                }

                    double xP = position.getX();
                    double yP = position.getY();

                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    int textWidth = metrics.stringWidth(name);

                    g.translate(xP,yP);
                    g.rotate(theta);

                    drawArrow(new Point2D.Double(0, 0), new Point2D.Double(-textWidth, 0), g);

                    g.setColor(Color.BLACK);


                    double degrees = Math.abs(Math.toDegrees(theta));

                    if( ( degrees > 90 && degrees < 180 ) || ( degrees > 270  && degrees < 360)){
                        g.scale(-1, -1);
                        g.translate(0, -metrics.getDescent());  //To create a little offset between arrow and text
                        g.drawString(name, 0, 0);
                        g.translate(0, metrics.getDescent());

                        g.scale(-1, -1);
                    }else{
                        g.translate(-textWidth, -metrics.getDescent());  //To create a little offset between arrow and text

                        g.drawString(name, 0, 0);

                        g.translate(textWidth, metrics.getDescent());
                    }

                    g.rotate(-theta);
                    g.translate(-xP ,-yP);

                }

            }


    /**
     * Draws arrow from start to end
     *
     * @param start Starting point
     * @param end ending points
     * @param g graphics context
     */
    private void drawArrow(Point2D start, Point2D end, Graphics2D g){

        //Taken from exercise No.3 and edited with few tweaks

        g.setStroke(new BasicStroke(3));

                g.setColor(Color.RED);

                double x1 = start.getX();
                double y1 = start.getY();

                double x2 = end.getX();
                double y2 = end.getY();


                // Spocitame slozky vektoru od (x1, y1) k (x2, y2)
                double vx = x2 - x1;
                double vy = y2 - y1;

                double length = Math.hypot(vx, vy);

                double unitVx = vx / length;
                double unitVy = vy / length;

                //Normal to the vector v
                double normUnitX = -vy;
                double normUnitY = vx;

                g.draw(new Line2D.Double(x1,y1,x2,y2));

                final double DISTANCE_FROM_END = 0.1 * length;
                final double LENGTH_OF_ARROWHEAD = 0.1;

                double pointFromEndX = x2 - unitVx * DISTANCE_FROM_END;
                double pointFromEndY = y2 - unitVy * DISTANCE_FROM_END;

                g.draw(new Line2D.Double(x2,y2,
                        pointFromEndX + normUnitX * LENGTH_OF_ARROWHEAD,
                        pointFromEndY + normUnitY * LENGTH_OF_ARROWHEAD));
                g.draw(new Line2D.Double(x2,y2,
                        pointFromEndX - normUnitX * LENGTH_OF_ARROWHEAD,
                        pointFromEndY - normUnitY * LENGTH_OF_ARROWHEAD));

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

                POINTS[j][i] =  new Point2D.Double(x, y);

                this.cells[j][i] = INFO[counter++];

                x += deltaX;

            }
            x = startXSim;

            y += deltaY;
        }

    }

    /**
     * Method to refresh data about simulation each second of the simulation
     */
    public void refresh() {
        for(int i = 0 ; i < INFO.length ; i++){
            data.add(new Data(INFO[i].getWaterLevel(), counterOfSeconds));
        }
        counterOfSeconds++;
    }

    private double startX = 0.0;

    private double startY = 0.0;

    /**
     * Method is to increase the scale for zoom
     */
    public void zoom(double x, double y){
        zoom *= ZOOM_IN_CONSTANT;

        zoomOffsetX = ( startX - (x - widthOfCanvas*ZOOM_IN_CONSTANT/2.0) );
        zoomOffsetY = ( startY - (y - heightOfCanvas*ZOOM_IN_CONSTANT/2.0) );

    }

    /**
     * Method to decrease the scale for zoom
     */
    public void zoomOut(){
        zoom *= ZOOM_OUT_CONSTANT;
    }

    /**
     * Method to reset the zoom
     */
    public void resetZoom(){
        zoom = 1;
        zoomOffsetX = 0;
        zoomOffsetY = 0;
        startX = 0;
        startY = 0;
    }

    /**
     * Sets the areas to know where the cells are located
     */
    private void setAreas() {
        for (int i = 0; i < AMMOUNT_OF_CELLS_HEIGHT; i++) {

            for (int j = 0; j < AMMOUNT_OF_CELLS_WIDTH; j++) {
                Point2D tmpPoint = model2window(POINTS[j][i]);

                areas[j][i] = new Rectangle2D.Double(tmpPoint.getX(), tmpPoint.getY(),
                        deltaX * scale * zoom, deltaY * scale * zoom);

            }
        }
    }


    /**
     * After selecting an area, this method goes through each point and checks if it is in the area
     *
     * @param r selected area
     * @return array of indexes in INFO array or null in case no points were found
     */
    public int[] findCells(Rectangle2D r){
        ArrayList<Integer> indexes = new ArrayList<>();

        int counter = 0;
        for (int i = 0; i < AMMOUNT_OF_CELLS_HEIGHT; i++) {

            for (int j = 0; j < AMMOUNT_OF_CELLS_WIDTH; j++) {

                if(r.contains(model2window(this.POINTS[j][i]))){
                    indexes.add(counter);
                }

                counter++;
            }
        }
        if(indexes.isEmpty()) return null;

        int[] temp = new int[indexes.size()];

        for(int i = 0  ; i < indexes.size(); i++){
            temp[i] = indexes.get(i);
        }


        return temp;
    }

    /**
     * Upon clicking checks all the areas to know which cell was clicked
     *
     * @param p mouseclick
     * @return index in the INFO array or null if nothing was found
     */
    public int[] findCells(Point2D p){

        int counter = 0 ;
        for (int i = 0; i < AMMOUNT_OF_CELLS_HEIGHT; i++) {

            for (int j = 0; j < AMMOUNT_OF_CELLS_WIDTH; j++) {
                Rectangle2D tempRect = areas[j][i];
                if(tempRect.contains(p)){
                    return new int[]{counter};
                }

                counter++;
            }
        }

        return null;
    }

    /**
     * Returns data for graph
     *
     * @return graph data
     */
    public ArrayList<Data> getData(){
        return this.data;
    }

    /**
     * When user clicks remember position where they clicked
     *
     * @param x position of mouse on x axis
     * @param y position of mouse on y axis
     */
    public void startPoint(double x, double y) {
        xRect = x;
        yRect = y;
    }

    /**
     * Draws rectangle on the panel so user knows what cells is he choosing
     *
     * @param x position of mouse on x axis
     * @param y position of mouse on y axis
     */
    public void drawChoosingRectangle(double x, double y){
        Graphics2D g = (Graphics2D)(this.getGraphics());
        g.draw(new Rectangle2D.Double(xRect, yRect, Math.abs(x-xRect), (y-yRect)));
        repaint();
    }

    /**
     * Creates a rectangle that was drawn and returns all cells in the drawn rectangle
     *
     * @param x position of mouse on x axis
     * @param y position of mouse on y axis
     * @return array of indexes
     */
    public int[] getSelectedPoints(double x, double y) {
        Rectangle2D r = new Rectangle2D.Double(xRect, yRect, Math.abs(x-xRect), (y-yRect));
        return findCells(r);
    }


    private double startPanX;

    private double startPanY;

    private double panX;

    private double panY;

    public void startPan(double x, double y){
        this.startPanX = x;
        this.startPanY = y;
    }

    public void pan(double x, double y){
        this.panX = x - startPanX ;
        this.panY = y - startPanY ;

        repaint();
    }


}
