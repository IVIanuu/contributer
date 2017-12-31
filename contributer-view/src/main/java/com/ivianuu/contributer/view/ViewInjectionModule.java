package com.ivianuu.contributer.view;


import android.view.View;

import java.util.Map;

import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.Multibinds;

/**
 * View injection module
 */
@Module
public abstract class ViewInjectionModule {

    @Multibinds
    abstract Map<Class<? extends View>, AndroidInjector.Factory<? extends View>>
    viewInjectorFactories();
}
