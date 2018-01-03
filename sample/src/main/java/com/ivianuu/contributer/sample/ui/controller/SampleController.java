package com.ivianuu.contributer.sample.ui.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.ivianuu.contributer.conductor.ConductorInjection;
import com.ivianuu.contributer.conductor.HasControllerInjector;
import com.ivianuu.contributer.sample.R;
import com.ivianuu.contributer.sample.model.ActivityDependency;
import com.ivianuu.contributer.sample.model.AppDependency;
import com.ivianuu.contributer.sample.model.ControllerDependency;
import com.ivianuu.contributer.sample.ui.childcontroller.SampleChildController;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Sample controller to showcase injection into child {@link Controller}'s
 */
public class SampleController extends Controller implements HasControllerInjector {

    @Inject DispatchingAndroidInjector<Controller> childControllerInjector;

    @Inject AppDependency appDependency;
    @Inject ActivityDependency activityDependency;
    @Inject ControllerDependency controllerDependency;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onContextAvailable(@NonNull Context context) {
        super.onContextAvailable(context);
        ConductorInjection.inject(this);

        checkNotNull(appDependency);
        checkNotNull(activityDependency);
        checkNotNull(controllerDependency);

        Log.d(getClass().getSimpleName(), "successfully injected");
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_sample, container, false);
        onViewCreated(view);
        return view;
    }

    private void onViewCreated(View view) {
        ViewGroup childContainer = view.findViewById(R.id.child_controller_container);

        Router childRouter = getChildRouter(childContainer);
        if (!childRouter.hasRootController()) {
            childRouter.setRoot(RouterTransaction.with(new SampleChildController()));
        }
    }

    @Override
    public DispatchingAndroidInjector<Controller> controllerInjector() {
        return childControllerInjector;
    }
}
