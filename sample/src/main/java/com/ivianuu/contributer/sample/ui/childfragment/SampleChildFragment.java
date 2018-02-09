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

package com.ivianuu.contributer.sample.ui.childfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivianuu.contributer.sample.R;
import com.ivianuu.contributer.sample.model.ActivityDependency;
import com.ivianuu.contributer.sample.model.AppDependency;
import com.ivianuu.contributer.sample.model.ChildFragmentDependency;
import com.ivianuu.contributer.sample.model.FragmentDependency;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class SampleChildFragment extends Fragment {

    @Inject AppDependency appDependency;
    @Inject ActivityDependency activityDependency;
    @Inject FragmentDependency fragmentDependency;
    @Inject ChildFragmentDependency childFragmentDependency;

    @SuppressLint("RestrictedApi")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);

        checkNotNull(appDependency);

        checkNotNull(activityDependency);
        checkNotNull(fragmentDependency);
        checkNotNull(childFragmentDependency);

        Log.d(getClass().getSimpleName(), "successfully injected");
    }
}
