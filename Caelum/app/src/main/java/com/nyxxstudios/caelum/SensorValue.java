package com.nyxxstudios.caelum;

import android.hardware.SensorManager;
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


    private float[] accelerationValues = new float[3]; //X, Y and Z
    public float[] getAccelerationValues() {
        return accelerationValues;
    }


    private float pressure; //current air pressure
    public float getPressure() {
        return pressure;
    }

    private double height;//is calculated based on the current pressure
    public double getHeight() {
        return height;
    }

    private float pressureAtGround; //TODO has to be set at start in main routine

    //constructor. Defines variables with sensor values
    public SensorValue(){
        time = currentDateAndTime(); //UTC
        accelerationValues = new float[]{0,0,0};
        accelerationValues = MainActivity.getCurrentAcceleration();
        System.out.println("0");
        //pressure = sensors.getPressure();
        pressure = MainActivity.getCurrentPressure();
        System.out.println("6");
        pressureAtGround = (float) 1013.25;
        height = pressureToHeight(pressure, pressureAtGround);
        }

    public float pressureToHeight(float pressure, float pressureAtGround) {
        float height = -100000;
        height = SensorManager.getAltitude(pressure, pressureAtGround);
        return height;
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

        result = "Time: " + timeAsString + "\n\n" +
                "Acceleration: \n" +
                "X = " + accelerationValues[0] + "\n" +
                "Y = " + accelerationValues[1] + "\n" +
                "Z = " + accelerationValues[2] + "\n\n" +

                "Pressure: " + pressure + "\n" +
                "Height: " + height;

        return result;
    }
}
