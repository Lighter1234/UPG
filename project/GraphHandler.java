package project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import waterflowsim.Simulator;

import java.util.ArrayList;

public class GraphHandler{

    /**
     * Formatted data for graph
     */
    private XYSeriesCollection dataset;

    /**
     * Data in XY format
     */
    private XYSeries data;

    /**
     * Arraylist of cell data
     */
    private ArrayList<Data> graphData;

    /**
     * Array of cell indexes
     */
    private int[] index;

    /**
     * Constant to jump through the list
     */
    private final int NEXT = Simulator.getData().length;

    /**
     * Accepts data and array of indexes
     *
     * @param graphData Arraylist of cell data
     * @param index indexes of cells
     */
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
        dataset = new XYSeriesCollection();
        data = new XYSeries("Water level");
        dataset.addSeries(data);

        refreshGraph();

        return ChartFactory.createXYLineChart("Graph of water level dependence on time", "time t (s)",
                "Water level (m)" ,dataset);
    }


    /**
     * Adds all new elements to the graph
     */
    public void refreshGraph(){

        if(index.length == 1) {
            for (int i = index[0]; i < graphData.size(); i += NEXT) {
                Data tempData = graphData.get(i);
                data.addOrUpdate(tempData.getSec(), tempData.getWaterHeight());
                index[0] = i + NEXT;

            }
        }
        else{
            for(int i = index[0] ; i < graphData.size() ;  i += NEXT ){
                Data tmpData = graphData.get(i);

                int sec = tmpData.getSec();

                double tempHeight = 0.0; // tmpData.getWaterHeight();

                int counter = 0 ;

                for(int j = 0 ; j <  index.length ; j++ ){
                    double tempH = graphData.get(index[j]).getWaterHeight();
                    if(tempH > 0.0) {
                        tempHeight += tempH;
                        counter++;
                    }

                    index[j] = index[j] + NEXT; // Creates a structure that helps with adding new element
                                                // without having to create the graph from the scratch
                }

                tempHeight /= counter;

                data.addOrUpdate(sec, tempHeight);

            }
        }

    }

}
