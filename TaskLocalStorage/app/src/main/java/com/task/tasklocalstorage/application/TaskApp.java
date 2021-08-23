package com.task.tasklocalstorage.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.task.tasklocalstorage.dependency.component.AppComponent;
import com.task.tasklocalstorage.dependency.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class TaskApp extends Application implements HasAndroidInjector, Application.ActivityLifecycleCallbacks {

    static TaskApp instance;

    public static AppComponent appComponent;

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;

        appComponent= DaggerAppComponent.builder().application(this).build();

        appComponent.inject(this);
    }


    public static TaskApp getInstance(){
        return instance;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
