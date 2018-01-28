package com.braingroom.tutor.common;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.braingroom.tutor.BuildConfig;
import com.braingroom.tutor.common.modules.AppModule;
import com.facebook.stetho.Stetho;

import android.app.Application.ActivityLifecycleCallbacks;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashMap;

import io.realm.Realm;

import static com.braingroom.tutor.utils.ConstantsKt.FONT_REGULAR;
import static com.braingroom.tutor.utils.ConstantsKt.FONT_BOLD;

/*
 * Created by godara on 06/10/17.
 */

public class CustomApplication extends Application implements ActivityLifecycleCallbacks {
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

    @Override
    public void onCreate() {
        super.onCreate();
        appModule = new AppModule(this);
        sInstance = this;
        Realm.init(this);
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
        if (!BuildConfig.DEBUG || !LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }

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

    public void putFontCache(String key, String name) {
        if (fontCache.get(key) == null) {
            fontCache.put(key, Typeface.createFromAsset(getApplicationContext().getAssets(), "font/" + name));
        } else {
            Log.d(TAG, "putFontCache: Already in cache");
        }
    }


    @SuppressWarnings("unused")
    public void setRegularTypeface(TextView textView) {
        if (getFont(FONT_REGULAR) == null)
            putFontCache(FONT_REGULAR, FONT_REGULAR);
        textView.setTypeface(getFont(FONT_REGULAR));
    }

    @SuppressWarnings("unused")
    public void setBoldTypeface(TextView textView) {
        if (getFont(FONT_BOLD) == null)
            putFontCache(FONT_BOLD, FONT_BOLD);
        textView.setTypeface(getFont(FONT_BOLD));
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof com.braingroom.tutor.view.activity.Activity) {
            appModule.setActivity((com.braingroom.tutor.view.activity.Activity) activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof com.braingroom.tutor.view.activity.Activity) {
            appModule.setActivity((com.braingroom.tutor.view.activity.Activity) activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof com.braingroom.tutor.view.activity.Activity) {
            appModule.setActivity((com.braingroom.tutor.view.activity.Activity) activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        clearActivityReferences(activity);

    }

    @Override
    public void onActivityStopped(Activity activity) {
        clearActivityReferences(activity);

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        clearActivityReferences(activity);

    }

    private void clearActivityReferences(Activity activity) {
        if (activity == CustomApplication.getInstance().appModule.getActivity())
            CustomApplication.getInstance().appModule.setActivity(null);
    }
}
