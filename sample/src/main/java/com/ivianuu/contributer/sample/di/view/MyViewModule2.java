package com.ivianuu.contributer.sample.di.view;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
public class MyViewModule2 {
    @Provides
    AlarmManager provideAlarmManager(Application application) {
        return (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
    }
}

