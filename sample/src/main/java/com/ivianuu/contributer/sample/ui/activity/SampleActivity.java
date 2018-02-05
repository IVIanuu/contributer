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

package com.ivianuu.contributer.sample.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.ivianuu.contributer.conductor.HasControllerInjector;
import com.ivianuu.contributer.sample.R;
import com.ivianuu.contributer.sample.model.ActivityDependency;
import com.ivianuu.contributer.sample.model.AppDependency;
import com.ivianuu.contributer.sample.ui.controller.SampleController;
import com.ivianuu.contributer.sample.ui.fragment.SampleFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Sample activity
 */
public class SampleActivity extends AppCompatActivity implements HasControllerInjector, HasSupportFragmentInjector {

    @Inject DispatchingAndroidInjector<Controller> controllerInjector;
    @Inject DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Inject AppDependency appDependency;
    @Inject ActivityDependency activityDependency;

    private Router router;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        checkNotNull(appDependency);
        checkNotNull(activityDependency);

        Log.d(getClass().getSimpleName(), "successfully injected");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        // attach controller
        ViewGroup controllerContainer = findViewById(R.id.controller_container);
        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new SampleController()));
        }

        // attach fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SampleFragment())
                    .commitNowAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public DispatchingAndroidInjector<Controller> controllerInjector() {
        return controllerInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }
}
