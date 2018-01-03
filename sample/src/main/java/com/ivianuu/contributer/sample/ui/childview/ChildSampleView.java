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

package com.ivianuu.contributer.sample.ui.childview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ivianuu.contributer.sample.model.ActivityDependency;
import com.ivianuu.contributer.sample.model.AppDependency;
import com.ivianuu.contributer.sample.model.ChildControllerDependency;
import com.ivianuu.contributer.sample.model.ChildViewDependency;
import com.ivianuu.contributer.sample.model.ControllerDependency;
import com.ivianuu.contributer.sample.model.ViewDependency;
import com.ivianuu.contributer.view.ViewInjection;

import javax.inject.Inject;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Sample controller view
 */
public class ChildSampleView extends View {

    @Inject AppDependency appDependency;
    @Inject ActivityDependency activityDependency;
    @Inject ControllerDependency controllerDependency;
    @Inject ChildControllerDependency childControllerDependency;
    @Inject ViewDependency viewDependency;
    @Inject ChildViewDependency childViewDependency;

    private boolean injected = false;

    public ChildSampleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!injected) {
            injected = true;
            // child views should be inject there dependencies in on attached to window
            ViewInjection.inject(this);

            checkNotNull(appDependency);
            checkNotNull(activityDependency);
            checkNotNull(controllerDependency);
            checkNotNull(childControllerDependency);
            checkNotNull(viewDependency);
            checkNotNull(childViewDependency);

            Log.d(getClass().getSimpleName(), "successfully injected");
        }
    }
}
