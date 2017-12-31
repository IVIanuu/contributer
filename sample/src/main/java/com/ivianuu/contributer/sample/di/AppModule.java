package com.ivianuu.contributer.sample.di;

import android.app.Application;
import android.content.pm.PackageManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@Module
public class AppModule {

    @Provides
    public PackageManager providePackageManager(Application application) {
        return application.getPackageManager();
    }
}
