package project;

public class Data {


    /**
     * Water height of cell
     */
    private double waterHeight;

    /**
     * Time of simulation in seconds
     */
    private int sec;

    /**
     * Constructor for Data used in graphs
     *
     * @param waterHeight
     * @param sec
     */
    public Data(double waterHeight, int sec ){
        this.waterHeight = waterHeight;
        this.sec = sec;
    }

    /**
     * Getter for water height of the simulation
     *
     * @return water height of the cell
     */
    public double getWaterHeight() {
        return waterHeight;
    }

    /**
     * Getter for time of the simulation
     *
     * @return time in seconds
     */
    public int getSec() {
        return sec;
    }
}
