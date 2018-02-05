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

package com.ivianuu.contributer.sample.ui.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;

import com.ivianuu.contributer.sample.model.ActivityDependency;
import com.ivianuu.contributer.sample.model.AppDependency;
import com.ivianuu.contributer.sample.model.ChildFragmentDependency;
import com.ivianuu.contributer.sample.model.FragmentDependency;
import com.ivianuu.contributer.sample.model.PreferenceDependency;
import com.ivianuu.contributer.supportpreference.HasSupportPreferenceInjector;
import com.ivianuu.contributer.supportpreference.SupportPreferenceInjection;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class SamplePreferenceGroup extends PreferenceGroup implements HasSupportPreferenceInjector {

    @Inject DispatchingAndroidInjector<Preference> preferenceInjector;

    @Inject AppDependency appDependency;
    @Inject ActivityDependency activityDependency;
    @Inject FragmentDependency fragmentDependency;
    @Inject ChildFragmentDependency childFragmentDependency;
    @Inject PreferenceDependency preferenceDependency;

    public SamplePreferenceGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SamplePreferenceGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SamplePreferenceGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        SupportPreferenceInjection.inject(this);

        checkNotNull(appDependency);
        checkNotNull(activityDependency);
        checkNotNull(fragmentDependency);
        checkNotNull(childFragmentDependency);
        checkNotNull(preferenceDependency);

        Log.d(getClass().getSimpleName(), "successfully injected");
    }

    @Override
    public AndroidInjector<Preference> supportPreferenceInjector() {
        return preferenceInjector;
    }
}
