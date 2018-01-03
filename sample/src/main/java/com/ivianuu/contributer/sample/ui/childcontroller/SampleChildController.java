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

package com.ivianuu.contributer.sample.ui.childcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.ivianuu.contributer.conductor.ConductorInjection;
import com.ivianuu.contributer.sample.R;
import com.ivianuu.contributer.sample.model.ActivityDependency;
import com.ivianuu.contributer.sample.model.AppDependency;
import com.ivianuu.contributer.sample.model.ChildControllerDependency;
import com.ivianuu.contributer.sample.model.ControllerDependency;
import com.ivianuu.contributer.sample.util.ViewInjectionContextWrapper;
import com.ivianuu.contributer.view.HasViewInjector;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Sample child controller to showcase injection into {@link View}'s
 */
public class SampleChildController extends Controller implements HasViewInjector {

    @Inject DispatchingAndroidInjector<View> viewInjector;

    @Inject AppDependency appDependency;
    @Inject ActivityDependency activityDependency;
    @Inject ControllerDependency controllerDependency;
    @Inject ChildControllerDependency childControllerDependency;

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        // we have to inject here because the parent controller get set after on context available
        ConductorInjection.inject(this);

        checkNotNull(appDependency);
        checkNotNull(activityDependency);
        checkNotNull(controllerDependency);
        checkNotNull(childControllerDependency);

        Log.d(getClass().getSimpleName(), "successfully injected");

        Context wrappedContext = new ViewInjectionContextWrapper(inflater.getContext(), this);
        LayoutInflater wrappedInflated = inflater.cloneInContext(wrappedContext);
        return wrappedInflated.inflate(R.layout.child_controller_sample, container, false);
    }

    @Override
    public DispatchingAndroidInjector<View> viewInjector() {
        return viewInjector;
    }
}
