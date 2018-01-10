package com.ivianuu.contributer.sample;

import android.app.Activity;
import android.app.Application;

import com.ivianuu.contributer.sample.di.DaggerAppComponent;
import com.ivianuu.contributer.sample.di.module.ChildViewBindingModule_BindSampleChildView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * App..
 */
public class App extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
