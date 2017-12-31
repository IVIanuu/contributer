package com.ivianuu.contributer.sample.ui.view;

import android.app.AlarmManager;
import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import javax.inject.Inject;

import com.ivianuu.contributer.view.ViewInjection;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class MyView extends View {

    @Inject AlarmManager alarmManager;
    @Inject AudioManager audioManager;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewInjection.inject(this);
    }
}
