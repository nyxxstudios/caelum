package com.nyxxstudios.caelum;

/**
 * Created by gordonkoehn on 9/25/16.
 */

import android.content.Context;

//Sensor classes
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;



public class Sensors implements SensorEventListener {

    public MainActivity mainActivity;

    public float getPressure() {
        Sensor presSensor;
        SensorManager presSM;

        float pressure = 0;
        //get our Sensor Mananger
        presSM = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        //Assign Sensors
        presSensor = presSM.getDefaultSensor(Sensor.TYPE_PRESSURE);

        return pressure;
    }

    public double[] getAcceleration() {
        Sensor accSensor;
        SensorManager accSM;

        double[] acceleration = new double[3];
        //get our Sensor Mananger
        accSM = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        //Assign Sensors
        accSensor = accSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return acceleration;
    }


    public void onSensorChanged(SensorEvent event) {

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in Use
    }
}