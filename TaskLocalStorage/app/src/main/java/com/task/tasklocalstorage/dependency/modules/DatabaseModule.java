package com.task.tasklocalstorage.dependency.modules;

import android.content.Context;

import androidx.room.Room;

import com.task.tasklocalstorage.repo.DatabaseRepo;
import com.task.tasklocalstorage.room.UserDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public UserDatabase provideUserDatabase(Context context){
        return Room.databaseBuilder(context, UserDatabase.class, "user_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public DatabaseRepo provideDatabaseRepo(UserDatabase database){
        return new DatabaseRepo(database);
    }
}
