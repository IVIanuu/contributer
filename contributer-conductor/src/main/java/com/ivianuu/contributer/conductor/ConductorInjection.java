package com.ivianuu.contributer.conductor;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;

import dagger.android.DispatchingAndroidInjector;
import dagger.internal.Preconditions;

/**
 * Conductor injection
 */
public final class ConductorInjection {

    private ConductorInjection() {
    }

    public static void inject(Controller controller) {
        Preconditions.checkNotNull(controller, "controller");
        HasControllerInjector hasDispatchingControllerInjector = findHasControllerInjector(controller);
        DispatchingAndroidInjector<Controller> controllerInjector = hasDispatchingControllerInjector.controllerInjector();
        Preconditions.checkNotNull(controllerInjector, "%s.controllerInjector() returned null",
                hasDispatchingControllerInjector.getClass().getCanonicalName());
        controllerInjector.inject(controller);
    }

    private static HasControllerInjector findHasControllerInjector(Controller controller) {
        Controller parentController = controller;

        do {
            if ((parentController = parentController.getParentController()) == null) {
                Activity activity = controller.getActivity();
                if (activity instanceof HasControllerInjector) {
                    return (HasControllerInjector) activity;
                }

                if (activity.getApplication() instanceof HasControllerInjector) {
                    return (HasControllerInjector) activity.getApplication();
                }

                throw new IllegalArgumentException(
                        String.format("No injector was found for %s", controller.getClass().getCanonicalName()));
            }
        } while (!(parentController instanceof HasControllerInjector));

        return (HasControllerInjector) parentController;
    }
}
