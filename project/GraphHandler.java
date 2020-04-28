package project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import waterflowsim.Simulator;

import java.util.ArrayList;

public class GraphHandler{

    private XYSeriesCollection dataset;

    private XYSeries data;

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
        dataset = new XYSeriesCollection();
        data = new XYSeries("A");
        dataset.addSeries(data);

        refreshGraph();

        return ChartFactory.createXYLineChart("Graf zavislosti vysky vody na case t", "cas t (s)",
                "Vyska hladiny vody (m)" ,dataset);
    }


    /**
     * Adds all new elements to the graph
     */
    public void refreshGraph(){

        if(index.length == 1) {
          //  System.out.println(index[0]);
            for (int i = index[0]; i < graphData.size(); i += NEXT) {
                Data tempData = graphData.get(i);
            //    double[][] temp = new double[][]{{tempData.getSec()}, {tempData.getWaterHeight()}};
                data.addOrUpdate(tempData.getSec(), tempData.getWaterHeight());
                index[0] = i + NEXT;

            }
        }
        else{
//            System.out.println(index.length);
//            int counter = 0;
            for(int i = index[0] ; i < graphData.size() ;  i += NEXT ){
                Data tmpData = graphData.get(i);

                int sec = tmpData.getSec();

                double tempHeight = 0.0; // tmpData.getWaterHeight();

                for(int j = 0 ; j <  index.length ; j++ ){
                    tempHeight += graphData.get(index[j]).getWaterHeight();
                    index[j] = index[j] + NEXT; // Creates a structure that helps with adding new element
                    // without having to create the graph from the scratch
                }

//                double[][] temp = new double[][]{{sec},{tempHeight}};
//                System.out.println(sec);

//                System.out.println(tempHeight);

                tempHeight /= (double)index.length;

                data.addOrUpdate(sec, tempHeight);

            }
        }

    }

}
