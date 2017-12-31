package com.ivianuu.contributer.sample.ui.conductor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import javax.inject.Inject;

import com.ivianuu.contributer.conductor.ConductorInjection;
import com.ivianuu.contributer.sample.R;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class MyController extends Controller {

    @Inject PackageManager packageManager;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_my, container, false);
    }

    @Override
    protected void onContextAvailable(@NonNull Context context) {
        super.onContextAvailable(context);
        ConductorInjection.inject(this);
    }
}
