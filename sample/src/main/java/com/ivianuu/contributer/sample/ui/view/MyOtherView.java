package com.ivianuu.contributer.sample.ui.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ivianuu.contributer.view.ViewInjection;

import javax.inject.Inject;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class MyOtherView extends FrameLayout {

    @Inject PackageManager packageManager;

    public MyOtherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        ViewInjection.inject(this);
        super.onFinishInflate();
    }
}
