package com.ivianuu.contributer.sample;

import android.app.Activity;
import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bluelinelabs.conductor.Controller;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import com.ivianuu.contributer.conductor.HasControllerInjector;
import com.ivianuu.contributer.recyclerview.HasViewHolderInjector;
import com.ivianuu.contributer.sample.di.DaggerAppComponent;
import com.ivianuu.contributer.view.HasViewInjector;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class App extends Application implements HasActivityInjector, HasControllerInjector, HasViewInjector, HasViewHolderInjector {

    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<Controller> controllerInjector;
    @Inject DispatchingAndroidInjector<View> viewInjector;
    @Inject DispatchingAndroidInjector<RecyclerView.ViewHolder> viewHolderInjector;

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

    @Override
    public DispatchingAndroidInjector<RecyclerView.ViewHolder> viewHolderInjector() {
        return viewHolderInjector;
    }
}
