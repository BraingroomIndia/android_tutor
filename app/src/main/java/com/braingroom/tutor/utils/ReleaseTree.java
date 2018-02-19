package com.braingroom.tutor.utils;

import android.util.Log;

import com.braingroom.tutor.common.CustomApplication;
import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

/*
 * Created by godara on 30/01/18.
 */

public class ReleaseTree extends Timber.Tree {

    ReleaseTree() {
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
