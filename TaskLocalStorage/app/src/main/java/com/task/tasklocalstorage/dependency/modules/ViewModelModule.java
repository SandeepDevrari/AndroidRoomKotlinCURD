package com.task.tasklocalstorage.dependency.modules;

import androidx.lifecycle.ViewModel;

import com.task.tasklocalstorage.dependency.factory.BaseViewModelKey;
import com.task.tasklocalstorage.ui.dashboard.AllUserViewModel;
import com.task.tasklocalstorage.ui.dashboard.userdetail.UserDetailViewModel;
import com.task.tasklocalstorage.ui.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @BaseViewModelKey(SplashViewModel.class)
    abstract ViewModel provideSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @BaseViewModelKey(AllUserViewModel.class)
    abstract ViewModel provideViewModelAllUser(AllUserViewModel viewModel);

    @Binds
    @IntoMap
    @BaseViewModelKey(UserDetailViewModel.class)
    abstract ViewModel provideViewModelUserDetail(UserDetailViewModel viewModel);

}
