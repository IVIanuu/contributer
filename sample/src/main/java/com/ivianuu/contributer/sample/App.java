package com.ivianuu.contributer.sample;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import com.bluelinelabs.conductor.Controller;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import com.ivianuu.contributer.conductor.HasControllerInjector;
import com.ivianuu.contributer.sample.di.DaggerAppComponent;
import com.ivianuu.contributer.view.HasViewInjector;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class App extends Application implements HasActivityInjector, HasControllerInjector, HasViewInjector {

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<Controller> controllerInjector;
    @Inject DispatchingAndroidInjector<View> viewInjector;

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

    @Override
    public DispatchingAndroidInjector<Controller> controllerInjector() {
        return controllerInjector;
    }

    @Override
    public DispatchingAndroidInjector<View> viewInjector() {
        return viewInjector;
    }
}
