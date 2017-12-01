package com.braingroom.tutor.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.barcodereader.BarcodeCaptureActivity

class SplashActivity : AppCompatActivity() {

    var loggedIn: Boolean
        get() = CustomApplication.getInstance().loggedIn;
        set(value) {
            CustomApplication.getInstance().loggedIn = value;
        }

    val userPreferences by lazy {
        CustomApplication.getInstance().appModule.userPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loggedIn = userPreferences.getBoolean(lodgedIn, true)

        if (loggedIn) {
            CustomApplication.getInstance().userEmail = userPreferences.getString(email, "viki.godara@gmail.com")
            CustomApplication.getInstance().userId = userPreferences.getString(braingroomId, "1131")
            CustomApplication.getInstance().userName = userPreferences.getString(name, "Vikas Godara")
            CustomApplication.getInstance().userPic = userPreferences.getString(profilePic, "");
            startActivity(Intent(this, BarcodeCaptureActivity::class.java))
        } else startActivityForResult(Intent(this, LoginActivity::class.java), LOG_IN_REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, BarcodeCaptureActivity::class.java))
        }
    }
}