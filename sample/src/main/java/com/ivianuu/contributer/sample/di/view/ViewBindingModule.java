package com.ivianuu.contributer.sample.di.view;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

import com.ivianuu.contributer.sample.ui.view.MyOtherView;
import com.ivianuu.contributer.sample.ui.view.MyView;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
public abstract class ViewBindingModule {

    @ContributesAndroidInjector(modules = {
            MyViewModule.class,
            MyViewModule2.class
    })
    abstract MyView bindMyView();

    @ContributesAndroidInjector
    abstract MyOtherView bindMyOtherView();
}
