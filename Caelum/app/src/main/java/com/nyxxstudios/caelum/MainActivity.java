package com.nyxxstudios.caelum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

//Sensor classes
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

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

//UI classes
import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mAcceleration;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public static float getCurrentPressure() {
        return currentPressure;
    }
    private static float currentPressure;

    public static float[] getCurrentAcceleration() {
        return currentAcceleration;
    }
    private static float[] currentAcceleration = new float[3];

    public static double[] currentLocation = new double[2];
    public static double[] getCurrentLocation(){return currentLocation; }


    Button btnStart;


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

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPressure != 0) {
                    SensorValue.pressureAtGround = currentPressure;
                }else{
                    System.err.println("pressureAtGround not set correctly!");
                }
                configure_button();
                startLogging();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StorageManager.isExternalStorageWritable()){
                    Log.d("TAG", "writable");
                    System.out.println("writable");

                }else{
                    Log.e("TAG", "not writable");
                    System.out.println("not writable");
                }
                saveButtonClicked();
            }
        });


        //get our Sensor Mananger
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //get Location Mananger
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation[1] = location.getLongitude();
                currentLocation[2] = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

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

    public void saveButtonClicked(){
        StorageManager.writeToFile("Test data \nhello world \nauthor:Jonas");
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

    //GPS Permission not granted

  //**  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }


    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        //noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

    }

}