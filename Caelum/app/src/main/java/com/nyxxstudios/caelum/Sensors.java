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

    public static MainActivity mainActivity;

    public float getPressure() {
        Sensor presSensor;
        SensorManager presSM;

        System.out.println("0.5");
        SensorEvent event = null;
        System.out.println("0.6");
        Sensor sensor = event.sensor;
        System.out.println("1");
        float pressure = 0;
        //get our Sensor Mananger
        presSM = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        System.out.println("2");
        //Assign Sensors
        presSensor = presSM.getDefaultSensor(Sensor.TYPE_PRESSURE);
        System.out.println("3");
        //register Listener
        presSM.registerListener(this, presSensor, SensorManager.SENSOR_DELAY_NORMAL);
        System.out.println("4");
        pressure = event.values[0];

        System.out.println("5");
        return 1014.0f;//pressure;
    }

    public double[] getAcceleration() {
        Sensor accSensor;
        SensorManager accSM;

        SensorEvent event = null;
        Sensor sensor = event.sensor;

        double[] acceleration = new double[3];
        //get our Sensor Mananger
        accSM = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        //Assign Sensors
        accSensor = accSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //register Listener
        accSM.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);

        for (int i = 0; i <= 2; i++) {
            acceleration[i] = event.values[i];
        }

        return acceleration;
    }


    public void onSensorChanged(SensorEvent event) {

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in Use
    }
}