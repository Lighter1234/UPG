package project;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener, WindowListener {

    /**
     * Instance of active panel
     */
    private Panel p;

    /**
     * Instace of timer to repaint the graph
     */
    private Timer t;

    /**
     * Instance of chartpanel to put the graph in
     */
    private ChartPanel chp;

    /**
     * Instance of graph
     */
    private GraphHandler graph;

    /**
     * New window to show graph in
     */
    private JFrame frame;

    /**
     * Variable to represent if graph is shown
     */
    private boolean graphActive = false;

    /**
     * Variable to represent if the user is choosing rectangle
     */
    private boolean drawingRect = false;

    /**
     * Variable to represent if the user is panning
     */
    private boolean panning = false;

    /**
     * Takes Panel instance and adds this instance as it's mouse listener
     *
     * @param p panel to work with
     */
    public MyMouseListener(Panel p){
        this.p = p;
        this.p.addMouseMotionListener(this);
        this.p.addMouseListener(this);
        this.p.addMouseWheelListener(this);
    }

    /**
     * Reaction on mouseclick from user,
     * Creates a graph
     *
     * @param e click from user
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {
            if (!this.p.isChoosingPolygon()) {
                int[] point = p.findCells(e.getPoint());

                if (point != null) {
                    if (!graphActive) {
                       createGraph(point);
                    }
                }
            }else{
                int[] graphData =  p.addPointToPolygon(e.getPoint());
                if(graphData != null) {
                    createGraph(graphData);
                }
            }
        }
    }

    /**
     * Reaction when user presses the mouse button
     * Prepares first point
     *
     * @param e mouse click
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(!this.p.isChoosingPolygon()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                p.startPoint(e.getX(), e.getY());
                this.drawingRect = true;
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                p.startPan(e.getX(), e.getY());
                this.panning = true;
            }
        }
    }

    /**
     * Reaction when user lets go of the mouse button
     * Creates a graph
     *
     * @param e mouse release
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if(drawingRect) {
            int[] selectedPoints = p.getSelectedPoints(e.getX(), e.getY());
            if (selectedPoints != null) {
                if (!graphActive) {
                    createGraph(selectedPoints);
                }
            }
        }
        drawingRect = false;
        panning = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Reaction when user is holding the button and goes around the panel
     * Creates a rectangle on the panel
     *
     * @param e movement with mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if(drawingRect) {
            p.drawChoosingRectangle(e.getX(), e.getY());
        }else if(panning){
            p.pan(e.getX(),e.getY());
        }
    }


    /**
     * If user is choosing polygon,
     * draws a line towards the point it user has moved to
     *
     * @param e mouse movement
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if(p.isChoosingPolygon()){
            p.choosePointForPolygon(e.getPoint());
        }

    }

    /**
     * Reaction when user scrolls with mouse wheel
     * Zooms in or out depending on the scroll
     *
     * @param e mouse wheel scroll
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rot = e.getWheelRotation();

        if(rot > 0){
            p.zoomOut();
        } else{
            p.zoom(e.getX(), e.getY());
        }
        p.repaint();
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Reaction when user closes the panel with graph
     *
     * @param e panel closing
     */
    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
        this.graphActive = false;
        t = null;
        frame = null;
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    /**
     * Creates a graph
     *
     * @param selectedPoints indexes of the cells
     */
    private void createGraph(int[] selectedPoints){
        frame = new JFrame("Graph");
        frame.addWindowListener(this);

        graph = new GraphHandler(p.getData(), selectedPoints);

        chp = new ChartPanel(graph.createXYChart());
        frame.add(chp);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        int timerPeriod = 1000 / 25; // Prekreslit okno 25krat za 1000 milisekund
        t = new Timer(timerPeriod, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                graph.refreshGraph();
                chp.repaint();


            }
        });
        t.start();
        graphActive = true;
    }
}
