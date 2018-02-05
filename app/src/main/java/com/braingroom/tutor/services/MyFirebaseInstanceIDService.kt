package com.braingroom.tutor.services

import android.content.SharedPreferences
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.utils.FCM_TOKEN
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/*
 * Created by godara on 01/02/18.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {


    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        if (!refreshedToken.isNullOrBlank()) {
            Timber.tag(TAG).d("Refreshed token: " + refreshedToken)
            CustomApplication.getInstance().appModule.dataFlowService.registerFCMToken(refreshedToken!!);
            CustomApplication.getInstance().appModule.preferencesEditor.putString(FCM_TOKEN, refreshedToken).apply()
        }
    }

    companion object {

        val TAG = "MyFirebaseInstanceIDService"
    }

}