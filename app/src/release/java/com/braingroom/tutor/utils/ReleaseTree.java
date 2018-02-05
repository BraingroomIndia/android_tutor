package com.braingroom.tutor.utils;

import android.util.Log;

import com.braingroom.tutor.common.CustomApplication;
import com.crashlytics.android.Crashlytics;

import java.util.Map;

import timber.log.Timber;

import static android.util.Log.ERROR;
import static android.util.Log.WARN;

/**
 * Created by godara on 30/01/18.
 */

public class ReleaseTree extends Timber.Tree {

    public ReleaseTree() {
        Crashlytics.setUserEmail(CustomApplication.getInstance().userEmail);
        Crashlytics.setUserName(CustomApplication.getInstance().userName);
    }

    @Override
    protected void log(final int priority, final String tag, final String message, final Throwable throwable) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        Crashlytics.log(priority, tag, message);

        if (throwable != null) {
            Crashlytics.logException(throwable);
        }
    }


}
