package com.nyxxstudios.caelum;

import android.os.Environment;
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

public class StorageManager {

    private static final String LOG_TAG = "StorageManager";

    /*Provides methods for saving .txt files to the (external) storage.
    The data that is being collected (a list of SensorValue objects and the photos) can be saved
    on an external storage (sd card) for later access on a computer.

    Class uses only static functions, no objects of this class will be created.
     */

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getAlbumStorageDir(String fileName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), fileName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

//    public static void savetext () {
//        try {
//
//            OutputStreamWriter out = new OutputStreamWriter(getAlbumStorageDir("testFileJonas"));
//            String text = "this is a sample text";
//            out.write(text);
//            out.write('\n');
//            out.close();
//            System.out.println("saved successfully");
//
//        } catch (Throwable t) {
//
//            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
//
//        }
//        //Toast.makeText(this, "Save not implemented yet.", Toast.LENGTH_SHORT).show();
//    }}

    public static void writeToFile(String data)
    {
//        // Get the directory for the user's public pictures directory.
//        final File path =
//                Environment.getExternalStoragePublicDirectory
//                        (
//                                //Environment.DIRECTORY_PICTURES
//                                Environment.DIRECTORY_DCIM + "/YourFolder/"
//                        );
//
//        // Make sure the path directory exists.
//        if(!path.exists())
//        {
//            // Make it, if it doesn't exit
//            path.mkdirs();
//        }

        File root = Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/CaelumFolder";

        System.out.println("Path: " + path);

        // Create the folder.
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Create the file.
        final File file = new File(folder, "config.txt");

//        final File file = new File(path, "config.txt");

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            fOut.write(data.getBytes());

            //OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            //myOutWriter.append(data);
            //myOutWriter.close();

            fOut.flush();
            fOut.close();

            System.out.println("Message saved");
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
