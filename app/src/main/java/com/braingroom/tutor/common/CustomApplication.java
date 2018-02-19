package com.braingroom.tutor.common;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.TextView;

import com.braingroom.tutor.BuildConfig;
import com.braingroom.tutor.common.modules.AppModule;
import com.braingroom.tutor.utils.TimberLogImplementation;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.facebook.stetho.Stetho;

import android.app.Application.ActivityLifecycleCallbacks;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashMap;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


import static com.braingroom.tutor.utils.ConstantsKt.FONT_REGULAR;
import static com.braingroom.tutor.utils.ConstantsKt.FONT_BOLD;

/*
 * Created by godara on 06/10/17.
 */

public class CustomApplication extends MultiDexApplication {
    private static CustomApplication sInstance;
    private String TAG = CustomApplication.class.getSimpleName();
    public boolean loggedIn = false;
    private AppModule appModule;
    private HashMap<String, android.graphics.Typeface> fontCache = new HashMap<>();
    private RefWatcher refWatcher;
    public String userName = "";
    public String userEmail = "";
    public String userId = "";
    public String userPic = "";
    public static String GEO_TAG = "";


    @Override
    public void onCreate() {
        super.onCreate();
        appModule = new AppModule(this);
        sInstance = this;
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
        if (!BuildConfig.DEBUG || !LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
            Fabric.with(this, new Crashlytics());

        }
        TimberLogImplementation.init();
        appModule.getDataFlowService().getGeoDetail();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appModule = null;
        sInstance = null;
    }

    public Typeface getFont(String key) {
        return fontCache.get(key);
    }

    synchronized public static CustomApplication getInstance() {
        return sInstance;
    }

    synchronized public AppModule getAppModule() {
        return appModule != null ? appModule : (getInstance() != null ? new AppModule(getInstance()) : null);
    }

    synchronized public RefWatcher getRefWatcher() {
        return refWatcher;
    }


}
