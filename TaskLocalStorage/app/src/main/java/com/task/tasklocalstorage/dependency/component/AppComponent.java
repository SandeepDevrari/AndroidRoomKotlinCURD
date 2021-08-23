package com.task.tasklocalstorage.dependency.component;

import com.task.tasklocalstorage.application.TaskApp;
import com.task.tasklocalstorage.dependency.modules.BaseModule;
import com.task.tasklocalstorage.dependency.modules.UIModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {
        AndroidInjectionModule.class,
        BaseModule.class
})
@Singleton
public interface AppComponent {

    void inject(TaskApp app);

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(TaskApp app);

        AppComponent build();
    }
}
