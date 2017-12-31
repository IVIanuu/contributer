package com.ivianuu.contributer.conductor;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;

import java.util.Map;

import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.Multibinds;

/**
 * Conductor injection module
 */
@Module
public abstract class ConductorInjectionModule {

    @Multibinds
    abstract Map<Class<? extends Controller>, AndroidInjector.Factory<? extends Controller>>
    controllerInjectorFactories();
}
