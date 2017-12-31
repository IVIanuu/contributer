package com.ivianuu.contributer.sample.di.activity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.ivianuu.contributer.sample.ui.MainActivity;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
