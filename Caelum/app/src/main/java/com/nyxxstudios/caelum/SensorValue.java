package com.nyxxstudios.caelum;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

/**
 * Created by Jonas on 25.09.2016.
 */
public class SensorValue {
    //all 'sensor variables' don't need a setter, because they are just once defined
    // (in the constructor of this class) and their value is never changed from outside of this class


    private SimpleDateFormat time; //time of creation
    public SimpleDateFormat getTime() {
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

    private double height;//is calculated based on the current pressure
    public double getHeight() {
        return height;
    }


    //constructor. Defines variables with sensor values
    public SensorValue(){
        time = currentDateAndTime(); //UTC
        accelerationValues = new double[]{0,0,0}; //for testing, replace with function from Sensors.java
        pressure = 1013.0;                        //for testing, replace with function from Sensors.java
        height = 0.0;                             //for testing, replace with function from Sensors.java

    }

    private SimpleDateFormat currentDateAndTime() {
        Calendar c = Calendar.getInstance();

//        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
//        Toast.makeText(this, formattedDate, Toast.LENGTH_LONG).show();

        return df;
    }

    @Override
    public String toString() {
        String result;

        String timeAsString;
        Calendar c = Calendar.getInstance();
        timeAsString = time.format(c.getTime());

        result = "Time: " + timeAsString + "\n" +
                "Acceleration: \n" +
                "X = " + accelerationValues[0] + "\n" +
                "Y = " + accelerationValues[1] + "\n" +
                "Z = " + accelerationValues[2] + "\n\n" +

                "Pressure: " + pressure;

        return result;
    }
}
