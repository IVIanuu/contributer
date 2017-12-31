package com.ivianuu.contributer.sample.di.conductor;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.ivianuu.contributer.sample.ui.conductor.MyController;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
public abstract class ControllerBindingModule {

    @ContributesAndroidInjector
    abstract MyController bindMyController();
}
