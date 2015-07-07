package limes_qgram;

/**
 *
 * @author Bene
 */
public class Data {
    private double theta;
    private double time;
    private int overTheta;
    private int sizeSample;
    
    public Data(double theta, double time, int overTheta, int sizeSample){
        this.theta = theta;
        this.time = time;
        this.overTheta = overTheta;
        this.sizeSample = sizeSample;
    }

    /**
     * @return the theta
     */
    public double getTheta() {
        return theta;
    }

    /**
     * @param theta the theta to set
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * @return the overTheta
     */
    public int getOverTheta() {
        return overTheta;
    }

    /**
     * @param overTheta the overTheta to set
     */
    public void setOverTheta(int overTheta) {
        this.overTheta = overTheta;
    }

    /**
     * @return the sizeSample
     */
    public int getSizeSample() {
        return sizeSample;
    }

    /**
     * @param sizeSample the sizeSample to set
     */
    public void setSizeSample(int sizeSample) {
        this.sizeSample = sizeSample;
    }
}
