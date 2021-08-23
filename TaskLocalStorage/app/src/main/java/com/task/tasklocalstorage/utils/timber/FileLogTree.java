package com.task.tasklocalstorage.utils.timber;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.task.tasklocalstorage.application.TaskApp;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;


public class FileLogTree extends Timber.DebugTree {

    private static final String LOG_TAG = FileLogTree.class.getSimpleName();

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        try {
            String path = "Log";
            String fileNameTimeStamp = new SimpleDateFormat("dd-MM-yyyy",
                    Locale.getDefault()).format(new Date());
            String logTimeStamp = new SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa",
                    Locale.getDefault()).format(new Date());
            String fileName = fileNameTimeStamp + ".html";

            // Create file
            File file  = generateFile(path, fileName);

            // If file created or exists save logs
            if (file != null) {
                FileWriter writer = new FileWriter(file, true);
                writer.append("<p style=\"background:lightgray;\"><strong "
                        + "style=\"background:lightblue;\">&nbsp&nbsp")
                        .append(logTimeStamp)
                        .append(" :&nbsp&nbsp</strong><strong>&nbsp&nbsp")
                        .append(tag)
                        .append("</strong> - ")
                        .append(message)
                        .append("</p>");
                writer.flush();
                writer.close();
            }


            if (Build.MANUFACTURER.equals("HUAWEI") || Build.MANUFACTURER.equals("samsung")) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
                    priority = Log.ERROR;
            }
            super.log(priority, tag, message, t);

        } catch (Exception e) {
            Log.e(LOG_TAG,"Error while logging into file : " + e);
        }
    }

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + " - " + element.getLineNumber();
    }

    /*  Helper method to create file*/
    @Nullable
    private static File generateFile(@NonNull String path, @NonNull String fileName) {
        File file = null;
        if (isExternalStorageAvailable()) {
            File root = new File(TaskApp.getInstance().getFilesDir(),
                    path);

            boolean dirExists = true;

            if (!root.exists()) {
                dirExists = root.mkdirs();
            }

            if (dirExists) {
                file = new File(root, fileName);
            }

            Log.d("FILE","created "+root.getAbsolutePath());
        }else{
            Log.d("FILE","no created");
        }
        return file;
    }

    /* Helper method to determine if external storage is available*/
    private static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
