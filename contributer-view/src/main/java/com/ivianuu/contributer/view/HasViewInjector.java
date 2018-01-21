package com.ivianuu.contributer.view;

import android.view.View;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

/**
 * Has view injector
 */
public interface HasViewInjector {
    /** Returns an android injector of views. */
    AndroidInjector<View> viewInjector();
}
