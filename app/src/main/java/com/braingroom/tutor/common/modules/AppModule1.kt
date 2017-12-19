package com.braingroom.tutor.common.modules

import android.content.Context
import com.braingroom.tutor.BuildConfig
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.services.CustomInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by godara on 30/11/17.
 */
@Module
class AppModule1(val app: CustomApplication) {

    val cacheSize: Long = 10 * 1024 * 1024;

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideAppContext(): Context = app.applicationContext


    @Provides
    @Singleton
    fun provideOkHttpCache(application: CustomApplication) = Cache(application.cacheDir, cacheSize)

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()!!

    fun provideOkHttpClient(application: CustomApplication, cache: Cache): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return if (BuildConfig.DEBUG)
            OkHttpClient.Builder()
                    .addInterceptor(CustomInterceptor()).addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(StethoInterceptor())
                    .cache(cache)
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()
        else
            OkHttpClient.Builder()
                    .addInterceptor(CustomInterceptor())
                    .cache(cache)
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS).build()

    }
}