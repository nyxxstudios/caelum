package com.nyxxstudios.caelum;

import java.util.Date;

/**
 * Created by Jonas on 25.09.2016.
 */
public class SensorValue {
    //all 'sensor variables' don't need a setter, because they are just once defined
    // (in the constructor of this class) and their value is never changed from outside of this class


    private Date time; //time of creation
    public Date getTime() {
        return time;
    }


    private double[] accelerationValues = new double[3]; //X, Y and Z
    public double[] getAccelerationValues() {
        return accelerationValues;
    }


    private double pressure; //current air pressure
    public double getPressure() {
        return pressure;
    }

    private double heigth;//is calculated based on the current pressure
    public double getHeigth() {
        return heigth;
    }


    //constructor. Defines variables with sensor values
    public SensorValue(){

    }

}
