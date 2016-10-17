package com.nyxxstudios.caelum;

import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

//GPS classes
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Jonas on 25.09.2016.
 */
public class SensorValue {
    //all 'sensor variables' don't need a setter, because they are just once defined
    // (in the constructor of this class) and their value is never changed from outside of this class

    public static float pressureAtGround;

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

    public double[] location = new double[2]; //longitude, latitude
    public double[] getLocation() {
        location = MainActivity.getCurrentLocation();
        //location = new double[]{(10), (10)};
        return location;
    }

    //constructor. Defines variables with sensor values
    public SensorValue(){
        time = currentDateAndTime(); //UTC
        accelerationValues = new float[]{0,0,0};
        accelerationValues = MainActivity.getCurrentAcceleration();
        System.out.println("0");
        //pressure = sensors.getPressure();
        pressure = MainActivity.getCurrentPressure();
        System.out.println("6");
        //pressureAtGround = (float) 1013.25;
        height = pressureToHeight(pressure);
        //location = MainActivity.currentLocation;
        location =getLocation();
        }

    public float pressureToHeight(float pressure) {
        float height = -100000;
        height = SensorManager.getAltitude(pressureAtGround, pressure);
        return height;
    }

    public double[] getLocation(double[] location) {
        //location = MainActivity.getCurrentLocation();
        location = new double[]{(10), (10)};
        return location;
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
                "Height: " + height + "\n\n" +
                "Location: " + location[1] + ", "+ location[0];

        return result;
    }
}
