package com.ivianuu.contributer.sample.di.view;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
public class MyViewModule {
    @Provides
    AudioManager provideAudioManager(Application application) {
        return (AudioManager) application.getSystemService(Context.AUDIO_SERVICE);
    }
}
