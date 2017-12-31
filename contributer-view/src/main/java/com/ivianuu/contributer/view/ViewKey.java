package com.ivianuu.contributer.view;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * View key
 */
@MapKey
@Target(ElementType.METHOD)
public @interface ViewKey {
    Class<? extends View> value();
}
