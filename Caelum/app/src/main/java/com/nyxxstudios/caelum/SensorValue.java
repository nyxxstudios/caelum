package com.nyxxstudios.caelum;

import java.util.Date;

/**
 * Created by Jonas on 25.09.2016.
 */
public class SensorValue {
    private Date time; //time of creation
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }


    private double[] accelerationValues = new double[3]; //X, Y and Z
    public void setAccelerationValues(double[] accelerationValues) {
        this.accelerationValues = accelerationValues;
    }
    public double[] getAccelerationValues() {
        return accelerationValues;
    }


    private double pressure;
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }


}
