package project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;
import waterflowsim.Simulator;

import java.util.ArrayList;

public class GraphHandler{

    private DefaultXYDataset dataset;

    private ArrayList<Data> graphData;

    private int[] index;

    private final int NEXT = Simulator.getData().length;

    public GraphHandler(ArrayList<Data> graphData, int[] index){
      this.graphData = graphData;
      this.index = index;
    }


    /**
     * Creates a XY graph that contains time in seconds on X axis and height of the water in meters on Y axis
     *
     * @return graph of data
     */
    public JFreeChart createXYChart(){
        dataset = new DefaultXYDataset();

        refreshGraph();

        return ChartFactory.createXYLineChart("Graf zavislosti vysky vody na case t", "cas t (s)",
                "Vyska hladiny vody (m)" ,dataset);
    }


    /**
     * Adds all new elements to the graph
     */
    public void refreshGraph(){

        if(index.length == 1) {
            for (int i = index[0]; i < graphData.size(); i += NEXT) {
                Data tempData = graphData.get(i);
                double[][] temp = new double[][]{{tempData.getSec()}, {tempData.getWaterHeight()}};
                dataset.addSeries(0, temp);
                index[0] = i + NEXT;
            }
        }
        else{
            int counter = 0;
            for(int i = index[counter] ; i < graphData.size() ; i += NEXT){
                int sec = graphData.get(i).getSec();
                double tempHeight = graphData.get(index[i]).getWaterHeight();

                for(int j = 1 ; j < index.length ; j++){
                    tempHeight += graphData.get(index[j]).getWaterHeight();
                }
                index[counter++] = i + NEXT; // Creates a structure that helps with adding new element
                // without having to create the graph from the scratch

                double[][] temp = new double[][]{{sec},{tempHeight}};
                dataset.addSeries(0,temp);

            }
        }

    }

}
