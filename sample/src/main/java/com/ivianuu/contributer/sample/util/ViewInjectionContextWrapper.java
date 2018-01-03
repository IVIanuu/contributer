/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.contributer.sample.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import com.ivianuu.contributer.view.HasViewInjector;

import dagger.android.DispatchingAndroidInjector;

/**
 * Wraps a {@link Context} and is a {@link HasViewInjector}
 * to make it possible to inject stuff from a {@link com.bluelinelabs.conductor.Controller}
 * into {@link View}'s
 * This could also be used for {@link android.support.v4.app.Fragment}'s
 */
public class ViewInjectionContextWrapper extends ContextWrapper implements HasViewInjector {

    private final HasViewInjector viewInjector;

    public ViewInjectionContextWrapper(Context context,
                                       HasViewInjector viewInjector) {
        super(context);
        this.viewInjector = viewInjector;
    }

    @Override
    public DispatchingAndroidInjector<View> viewInjector() {
        // delegate to the "real" injector
        return viewInjector.viewInjector();
    }
}
