package com.ivianuu.contributer.conductor;


import com.bluelinelabs.conductor.Controller;

import dagger.android.DispatchingAndroidInjector;

/**
 * Has controller injector
 */
public interface HasControllerInjector {
    /** Returns an android injector of controllers. */
    DispatchingAndroidInjector<Controller> controllerInjector();
}
