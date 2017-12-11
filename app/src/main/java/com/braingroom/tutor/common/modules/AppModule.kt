package com.braingroom.tutor.common.modules


import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.braingroom.tutor.BuildConfig.DEBUG
import com.braingroom.tutor.services.ApiService
import com.braingroom.tutor.services.CustomInterceptor
import com.braingroom.tutor.services.DataFlowService
import com.braingroom.tutor.services.RealmCacheService
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.activity.Activity
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import okhttp3.logging.HttpLoggingInterceptor


/*
 * Created by godara on 06/10/17.
*/

@SuppressLint("CommitPrefEdits")
@Suppress("unused", "MemberVisibilityCanPrivate")
class AppModule(private val application: Application) {

    var activity: Activity? = null
        set(value) {
            field = value
            if (value != null)
                Log.d(value.TAG, "activity set")
            else
                Log.d("activity null", "activity value set")
        }
    private val cacheSize = 10 * 1024 * 1024 // 10 MiB

    val gson: Gson by lazy {
        GsonBuilder().create()
    }
    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        if (DEBUG)
            OkHttpClient.Builder()
                    .addInterceptor(CustomInterceptor()).addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(StethoInterceptor())
                    .cache(Cache(application.cacheDir, cacheSize.toLong()))
                    .connectTimeout(1000, SECONDS)
                    .readTimeout(1000, SECONDS)
                    .writeTimeout(1000, SECONDS).build()
        else
            OkHttpClient.Builder()
                    .addInterceptor(CustomInterceptor())
                    .cache(Cache(application.cacheDir, cacheSize.toLong()))
                    .connectTimeout(1000, SECONDS)
                    .readTimeout(1000, SECONDS)
                    .writeTimeout(1000, SECONDS).build()
    }

    val picasso: Picasso by lazy {
        providePicasso()
    }
    val apiService: ApiService by lazy {
        if (DEBUG)
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(DEV_BASE_URL)
                    .client(okHttpClient)
                    .build().create(ApiService::class.java)
        else
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build().create(ApiService::class.java)
    }
    val realmCacheService by lazy {
        RealmCacheService()
    }
    val dataFlowService: DataFlowService by lazy {
        DataFlowService(apiService,realmCacheService)
    }
    val userPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }
    val preferencesEditor: SharedPreferences.Editor by lazy {
        userPreferences.edit();
    }

    var navigator: Navigator? = null
        get() {
            Log.d(activity?.TAG, "fetched navigator")
            return activity?.navigator
        }
    var messageHelper: MessageHelper? = null
        get() {
            Log.d(activity?.TAG, "fetched messageHelper")
            return activity?.messageHelper
        }
    var dialogHelper: DialogHelper? = null
        get() {
            Log.d(activity?.TAG, "fetched dialogHelper")
            return activity?.dialogHelper
        }
    fun providePicasso(): Picasso {
        val picasso = Picasso.with(application)
        picasso.setIndicatorsEnabled(false)
        picasso.isLoggingEnabled = false
        return picasso
    }
}