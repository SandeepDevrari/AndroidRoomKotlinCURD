package com.task.tasklocalstorage.utils.timber;

import android.os.Build;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;


public class DebugLogTree extends Timber.DebugTree {
    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        if (Build.MANUFACTURER.equals("HUAWEI") || Build.MANUFACTURER.equals("samsung")) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
                priority = Log.ERROR;
        }
        super.log(priority, tag, message, t);
    }

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + " - " + element.getLineNumber();
    }
}
