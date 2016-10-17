package com.nyxxstudios.caelum;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_APPEND;

/**
 * Created by Jonas on 07.10.2016.
 */

public class StorageManager{

    private static final String LOG_TAG = "StorageManager";

    //The path for the folder that is used for all saved data
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Caelum Data";

    /*Provides methods for saving .txt files to the (external) storage.
    The data that is being collected (a list of SensorValue objects and the photos) can be saved
    on an external storage (sd card) for later access on a computer.

    Just one object will be created, the storageManager in the MainActivity.java
     */

    public boolean isAccessAllowed = false;

    public StorageManager(){
        System.out.println("path = " + path);
        File dir = new File(path);
        dir.mkdirs();//creates folder, if not already existing

        //check, that user allows permissions for read and write to external storage
        isAccessAllowed = isAccessToExternalStorageAllowed();
        System.out.println("access allowed: " + isAccessAllowed);
    }

    public boolean isAccessToExternalStorageAllowed(){
        // first check for permissions
        if ((ActivityCompat.checkSelfPermission(MainActivity.mainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
            (ActivityCompat.checkSelfPermission(MainActivity.mainActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                System.out.println("request permission");
                MainActivity.mainActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                        ,20);
            }
            return false;
        }
        return true;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String fileName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), fileName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    public void write(String[] data){

        if(!isAccessAllowed){
            System.err.println("access not allowed");
            return;
        }

        File file = new File(path + "/logdata.txt");
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

}
