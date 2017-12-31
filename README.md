# Contributer


## Introduction
Dagger android injection is awesome right? But i only works for limited number of types..
With contributer you can use the @ContributesAndroidInjector pattern for each type such as views or conductor controllers.

## Download
```groovy
// in your root gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```groovy
dependencies {
         // for view
         implementation 'com.github.IVIanuu.Contributer:contributer-view:LATEST-VERSION'
         
         // for conductor
         implementation 'com.github.IVIanuu.Contributer:contributer-conductor:LATEST-VERSION'
         
         // for custom types
         implementation 'com.github.IVIanuu.Contributer:contributer-annotations:LATEST-VERSION'
         
         // Required
         annotationProcessor 'com.github.IVIanuu.Contributer:contributer-processor:LATEST-VERSION'
}
```

## Android Injection

If you have used the dagger android injection before make sure to remove the default android injection processor

```groovy
dependencies {
         // remove this line!!
         annotationProcessor 'com.google.dagger:dagger-android-processor:2.13'
}
```

## Views

Assume that your project contains a basic dagger setup.

First add the @AndroidInjectorKeyRegistry annotation to a class in your project for example your app component.
Here you have to add the @ViewKey.

```kotlin
@AndroidInjectorKeyRegistry(keys = arrayOf(ViewKey::class))
@Singleton
@Component(modules = {
      // modules
})
public interface AppComponent {
}
```

Next create your binding modules like you would with dagger android.
If you're not familiar with dagger android read this. https://google.github.io/dagger/android.html

```kotlin
@Module abstract class ViewBindingModule {

    @SomeScope
    @ContributesAndroidInjector(modules = [LoginModule::class, UiModule::class])
    abstract fun bindLoginView(): LoginView
    
    @ContributesAndroidInjector
    abstract fun bindPlayPauseView(): PlayPauseView

}
```

Add your binding module and the ViewInjectionModule to your app component.

```kotlin
@Singleton
@Component(modules = [
        AppModule::class,
        ViewBindingModule::class,
        ViewInjectionModule::class
        ]
))
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
```

Add the HasViewInjector interface to your app or base activity.

```kotlin
class App : Application(), HasViewInjector {

    @Inject lateinit var viewInjector: DispatchingAndroidInjector<View>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                    .application(this)
                    .build()
                    .inject(this)
    }
    
    override fun viewInjector() = viewInjector
}
```

Now you're done you can now inject your views with ViewInjection.inject(view)

```kotlin
class MyView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    @Inject lateinit var audioManager: AudioManager
    @Inject lateinit var packageManager: PackageManager

    override fun onFinishInflate() {
        super.onFinishInflate()
        ViewInjection.inject(this)

        // todo use injected stuff
    }
}
```

## Conductor

Absolutely the same as with views but replace the ViewKey with ControllerKey
the HasViewInjector with HasControllerInjector and so on.

## Custom types

1. Create a annotation class annotated with @MapKey and a value with the supertype of the classes
2. Replicate the HasSomeClassInjector, SomeClassInjection and SomeClassInjectionModule part
3. Done:D

You can check out the view or conductor module to look how they are implemented.

## Fun fact

Found out to late that contributer is a misspelling:DD

## License

```
Copyright 2017 Manuel Wrage

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
