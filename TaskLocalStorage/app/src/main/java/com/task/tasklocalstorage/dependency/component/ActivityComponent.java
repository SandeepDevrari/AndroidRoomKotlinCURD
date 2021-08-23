package com.task.tasklocalstorage.dependency.component;

import android.app.Activity;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent
public interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder{
        @BindsInstance
        Builder activity(Activity activity);

        ActivityComponent build();
    }
}
