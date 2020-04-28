package project;

import org.jfree.chart.ChartPanel;
import sun.awt.WindowClosingListener;
import waterflowsim.Simulator;

import javax.swing.*;
import java.awt.event.*;

public class MyMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener, WindowListener {

    private Panel p;

    private Timer t;

    private ChartPanel chp;

    private GraphHandler graph;

    private JFrame frame;

    private boolean graphActive = false;

    public MyMouseListener(Panel p){
        this.p = p;
        this.p.addMouseMotionListener(this);
        this.p.addMouseListener(this);
        this.p.addMouseWheelListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int[] point = p.findCells(e.getPoint());
        if(point !=null){
            if(!graphActive){

                frame = new JFrame("Graph");
                frame.addWindowListener(this);

                graph = new GraphHandler(p.getData(), point);

                chp = new ChartPanel(graph.createXYChart());
                frame.add(chp);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

                int timerPeriod = 1000 / 25  ; // Prekreslit okno 25krat za 1000 milisekund
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        p.startPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int[] selectedPoints = p.getSelectedPoints(e.getX(), e.getY());

        if(selectedPoints !=null){
            if(!graphActive){

                frame = new JFrame("Graph");
                frame.addWindowListener(this);

                graph = new GraphHandler(p.getData(), selectedPoints);

                chp = new ChartPanel(graph.createXYChart());
                frame.add(chp);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

                int timerPeriod = 1000 / 25  ; // Prekreslit okno 25krat za 1000 milisekund
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


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        p.drawChoosingRectangle(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rot = e.getWheelRotation();

        if(rot > 0){
            p.zoomOut();
        } else{
            p.zoom();
        }
        p.repaint();
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

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
}
