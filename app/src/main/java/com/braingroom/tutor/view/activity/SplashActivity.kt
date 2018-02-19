package com.braingroom.tutor.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.utils.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.HashMap
import com.google.android.youtube.player.internal.e
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import com.braingroom.tutor.common.modules.HelperFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class SplashActivity : AppCompatActivity() {
    lateinit var bundleReceived: Bundle
    val gson: Gson by lazy {
        CustomApplication.getInstance().appModule.gson
    }

    val apiServices by lazy {
        CustomApplication.getInstance().appModule.dataFlowService;
    }

    var loggedIn: Boolean
        get() = CustomApplication.getInstance().loggedIn;
        set(value) {
            CustomApplication.getInstance().loggedIn = value;
        }

    val userPreferences by lazy {
        CustomApplication.getInstance().appModule.userPreferences
    }
    val cls: Class<out Activity> = HomeActivity::class.java

    val helperFactory by lazy {
        HelperFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        bundleReceived = intent.getBundleExtra(pushNotification) ?: Bundle()
        loggedIn = userPreferences.getBoolean(lodgedIn, false)

        if (loggedIn) {
            CustomApplication.getInstance().userEmail = userPreferences.getString(email, "")
            CustomApplication.getInstance().userId = userPreferences.getString(braingroomId, "")
            CustomApplication.getInstance().userName = userPreferences.getString(name, "")
            CustomApplication.getInstance().userPic = userPreferences.getString(profilePic, "")

            pushNotification()
        } else startActivityForResult(Intent(this, LoginActivity::class.java), LOG_IN_REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            pushNotification()
        } else finish()
    }

    fun navigateActivity(destination: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, destination)
        intent.putExtra(bundleData, bundle)
        startActivity(intent)
        finish()
    }

    private fun navigateToIndex() {
        navigateActivity(HomeActivity::class.java, null)
    }

    private fun pushNotification() {
        apiServices.registerFCMToken(userPreferences.getString(FCM_TOKEN, ""))
        val postId: String?
        val classId: String?
        val messageSenderId: String?
        val messageSenderName: String?
        val notificationType: Int?
        val userId: String?

        val data: HashMap<String, String>
        //if onMessageReceived called
        if (bundleReceived.containsKey(pushNotification)) {
            data = gson.fromJson(bundleReceived.getString(pushNotification), object : TypeToken<HashMap<String, String>>() {

            }.type)
            Timber.tag(TAG).d("hashMap data" + data.toString())

            postId = data["post_id"]
            classId = data["class_id"]
            messageSenderId = data["sender_id"]
            messageSenderName = data["sender_name"]
            notificationType = data["notification_type"]?.toIntOrNull()
            userId = data["user_id"]

            if (postId != null) {
                val bundle = Bundle()
                bundle.putString("postId", postId)
                bundle.putBoolean(pushNotification, true)
                navigateActivity(HomeActivity::class.java, null)
            } else if (classId != null) {

                apiServices.getClassDetail(classId).doOnSubscribe { helperFactory.messageHelper.showProgressDialog("Wait", "Loading") }.doOnComplete { helperFactory.messageHelper.dismissActiveProgress() }.subscribe({
                    if (it.resCode) {
                        if (notificationType == 10) {
                            val bundle = Bundle()
                            bundle.putBoolean(pushNotification, true)
                            bundle.putString(classIdBundleData, classId);
                            bundle.putString(className, it.data.classTopic)
                            bundle.putString(classImage, it.data.classPic)
                            navigateActivity(PaymentClassDetailActivity::class.java, bundle)
                        } else {
                            val bundle = Bundle()
                            bundle.putBoolean(pushNotification, true)
                            bundle.putSerializable(classBudleData, it.data)
                            navigateActivity(ClassDetailActivity::class.java, bundle)
                        }
                    } else navigateToIndex()
                })
            } else if (messageSenderId != null && messageSenderName != null) {
                val bundle = Bundle()
                bundle.putBoolean(pushNotification, true)
                bundle.putString("sender_id", messageSenderId)
                bundle.putString("sender_name", messageSenderName)
                if ("0".equals(messageSenderId, ignoreCase = true))
                //If message is from admin open Inbox
                    navigateActivity(MessageActivity::class.java, bundle)
                else
                // For every one else open Chat thread
                    navigateActivity(MessageThreadActivity::class.java, bundle)

            } else {
                navigateToIndex()
            }

        } else navigateToIndex()


    }
}