package com.braingroom.tutor.utils;

import timber.log.Timber;

/**
 * Created by godara on 30/01/18.
 */

public class TimberLogImplementation implements TimberLog {
    public static void init() {
        Timber.plant(new ReleaseTree());
    }
}
