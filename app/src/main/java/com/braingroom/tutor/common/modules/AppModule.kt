package com.braingroom.tutor.common.modules


import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.braingroom.tutor.BuildConfig.DEBUG
import com.braingroom.tutor.services.ApiService
import com.braingroom.tutor.services.CustomInterceptor
import com.braingroom.tutor.services.DataFlowService

import com.braingroom.tutor.utils.*
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS


/*
 * Created by godara on 06/10/17.
*/

@SuppressLint("CommitPrefEdits")
@Suppress("unused", "MemberVisibilityCanPrivate")
class AppModule(private val application: Application) {


    private val cacheSize = 10 * 1024 * 1024 // 10 MiB

    val gson: Gson by lazy {
        GsonBuilder().create()
    }
    private val okHttpClient: OkHttpClient by lazy {
        val bodyLog = HttpLoggingInterceptor()
        bodyLog.level = HttpLoggingInterceptor.Level.BODY
        val headerLog = HttpLoggingInterceptor()
        headerLog.level = HttpLoggingInterceptor.Level.HEADERS
        if (DEBUG)
            OkHttpClient.Builder()
                    .addInterceptor(CustomInterceptor()).addInterceptor(bodyLog)
                    .addInterceptor(headerLog)
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
    val dataFlowService: DataFlowService by lazy {
        DataFlowService(apiService)
    }
    val userPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }
    val preferencesEditor: SharedPreferences.Editor by lazy {
        userPreferences.edit();
    }


}