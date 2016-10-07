package com.nyxxstudios.caelum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

//Sensor classes
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mAcceleration;

    public static float getCurrentPressure() {
        return currentPressure;
    }
    private static float currentPressure;

    public static float[] getCurrentAcceleration() {
        return currentAcceleration;
    }
    private static float[] currentAcceleration = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPressure != 0) {
                    SensorValue.pressureAtGround = currentPressure;
                }else{
                    System.err.println("pressureAtGround not set correctly!");
                }
                startLogging();
            }
        });


        //get our Sensor Mananger
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Assign Sensors
        mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);;

        if(mAcceleration == null){
            //not available on this device
            currentAcceleration = new float[]{Float.NEGATIVE_INFINITY,
                    Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY};
        }
        if(mPressure == null){
            //not available on this device
            currentPressure = Float.NEGATIVE_INFINITY;
        }

        //Register Sensor Listener
        mSensorManager.registerListener(this, mAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mPressure,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in Use
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            currentAcceleration = event.values;
        }
        else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            currentPressure = event.values[0];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Function is logging sensor values to the screen every X seconds
    It uses a second thread to do this task
     */
    private void startLogging(){
        final TextView tvOutput = (TextView) findViewById(R.id.textView);
        new Thread() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        final SensorValue currentSensorValues = new SensorValue();
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                tvOutput.append(currentSensorValues.toString());
                                tvOutput.append("\n-------------------------------------\n");
                            }
                        });
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


}