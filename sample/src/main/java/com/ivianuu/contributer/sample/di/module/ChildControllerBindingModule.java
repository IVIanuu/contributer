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

package com.ivianuu.contributer.sample.di.module;

import com.ivianuu.contributer.sample.di.scope.PerChildController;
import com.ivianuu.contributer.sample.ui.childcontroller.SampleChildController;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binding module for child {@link com.bluelinelabs.conductor.Controller}'s
 */
@Module
abstract class ChildControllerBindingModule {

    @PerChildController
    @ContributesAndroidInjector(modules = {
            ViewBindingModule.class
    })
    abstract SampleChildController bindSampleChildController();
}
