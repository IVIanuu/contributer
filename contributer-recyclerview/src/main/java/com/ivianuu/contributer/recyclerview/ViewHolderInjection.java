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

package com.ivianuu.contributer.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import dagger.android.AndroidInjector;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * View injection
 */
public final class ViewHolderInjection {

    private ViewHolderInjection() {
    }

    /**
     * Injects the view holder
     */
    public static void inject(RecyclerView.ViewHolder viewHolder) {
        checkNotNull(viewHolder, "viewHolder");
        HasViewHolderInjector hasViewHolderInjector = findHasViewHolderInjector(viewHolder);
        AndroidInjector<RecyclerView.ViewHolder> viewHolderInjector = hasViewHolderInjector.viewHolderInjector();
        checkNotNull(
                viewHolderInjector,
                "%s.viewHolderInjector() returned null",
                viewHolderInjector.getClass().getCanonicalName());

        viewHolderInjector.inject(viewHolder);
    }

    private static HasViewHolderInjector findHasViewHolderInjector(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.itemView.getContext() instanceof HasViewHolderInjector) {
            return (HasViewHolderInjector) viewHolder.itemView.getContext();
        } else if (viewHolder.itemView.getContext().getApplicationContext() instanceof HasViewHolderInjector) {
            return (HasViewHolderInjector) viewHolder.itemView.getContext().getApplicationContext();
        } else {
            throw new IllegalArgumentException(String.format(
                    "No injector was found for %s", viewHolder.getClass().getCanonicalName()));
        }
    }

}