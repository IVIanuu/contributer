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

package com.ivianuu.contributer.sample.di;

import android.app.Application;

import com.ivianuu.contributer.conductor.ConductorInjectionModule;
import com.ivianuu.contributer.sample.App;
import com.ivianuu.contributer.sample.di.activity.ActivityBindingModule;
import com.ivianuu.contributer.sample.di.conductor.ControllerBindingModule;
import com.ivianuu.contributer.sample.di.view.ViewBindingModule;
import com.ivianuu.contributer.annotations.AndroidInjectorKeyRegistry;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import com.ivianuu.contributer.conductor.ControllerKey;
import com.ivianuu.contributer.view.ViewInjectionModule;
import com.ivianuu.contributer.view.ViewKey;

@AndroidInjectorKeyRegistry(
        keys = {
                ControllerKey.class,
                ViewKey.class
        }
)
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        // conductor
        ConductorInjectionModule.class,
        ControllerBindingModule.class,
        // view
        ViewInjectionModule.class,
        ViewBindingModule.class
})
public interface AppComponent {
    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

