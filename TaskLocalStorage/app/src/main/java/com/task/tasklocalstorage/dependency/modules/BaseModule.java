package com.task.tasklocalstorage.dependency.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.task.tasklocalstorage.application.TaskApp;
import com.task.tasklocalstorage.dependency.component.ActivityComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        UIModule.class,
        DatabaseModule.class
}, subcomponents = {
        ActivityComponent.class
})
public class BaseModule {

    @Provides
    @Singleton
    public Application provideApplication(@NonNull TaskApp app){
        return app;
    }

    @Singleton
    @Provides
    public Context provideContext(@NonNull TaskApp app){
        return app.getApplicationContext();
    }
}
