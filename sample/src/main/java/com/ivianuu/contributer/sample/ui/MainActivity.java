package com.ivianuu.contributer.sample.ui;

import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.ivianuu.contributer.sample.R;
import com.ivianuu.contributer.sample.ui.conductor.MyController;
import com.ivianuu.contributer.sample.ui.recycler.MyViewHolder;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject PackageManager packageManager;

    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);

        ViewGroup container = findViewById(R.id.controller_container);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new MyController()));
        }

        // do this if you're to lazy to create a adapter
        new MyViewHolder(new View(this));
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
