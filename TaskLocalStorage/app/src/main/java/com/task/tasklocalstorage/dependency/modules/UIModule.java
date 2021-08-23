package com.task.tasklocalstorage.dependency.modules;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.task.tasklocalstorage.dependency.factory.BaseViewModelFactory;
import com.task.tasklocalstorage.ui.dashboard.ActivityAllUser;
import com.task.tasklocalstorage.ui.dashboard.userdetail.ActivityUserDetail;
import com.task.tasklocalstorage.ui.splash.SplashActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        ViewModelModule.class
})
public abstract class UIModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(@NonNull BaseViewModelFactory viewModelFactory);

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract ActivityAllUser bindActivityAllUser();

    @ContributesAndroidInjector
    abstract ActivityUserDetail bindActivityUserDetail();

}
