package com.ivianuu.contributer.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

import dagger.android.AndroidInjector;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * View injection
 */
public final class ViewInjection {

    private ViewInjection() {
    }

    /**
     * Injects the view
     */
    public static void inject(View view) {
        checkNotNull(view, "view");
        HasViewInjector hasViewInjector = findHasViewInjector(view);
        AndroidInjector<View> viewInjector = hasViewInjector.viewInjector();
        checkNotNull(
                viewInjector,
                "%s.viewInjector() returned null",
                viewInjector.getClass().getCanonicalName());

        viewInjector.inject(view);
    }

    private static HasViewInjector findHasViewInjector(View view) {
        if (view.getParent() != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof HasViewInjector) {
                return (HasViewInjector) parent;
            }

            while ((parent = parent.getParent()) != null) {
                if (parent instanceof HasViewInjector) {
                    return (HasViewInjector) parent;
                }
            }
        } else if (view.getContext() instanceof HasViewInjector) {
            return (HasViewInjector) view.getContext();
        } else if (view.getContext().getApplicationContext() instanceof HasViewInjector) {
            return (HasViewInjector) view.getContext().getApplicationContext();
        }

        throw new IllegalArgumentException(String.format(
                "No injector was found for %s", view.getClass().getCanonicalName()));
    }
}
