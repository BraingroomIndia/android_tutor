package com.braingroom.tutor.services


import android.util.Log
import com.braingroom.tutor.BuildConfig.VERSION_CODE
import com.braingroom.tutor.common.CustomApplication
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/*
 * Created by godara on 26/09/17.
 */

class CustomInterceptor : Interceptor {
    private val TAG: String? = CustomInterceptor::class.java.simpleName

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
                .addHeader("X-App-Type", "BGTUT01")
                .addHeader("X-App-Version", VERSION_CODE.toString())
                .addHeader("X-App-Geo", CustomApplication.GEO_TAG)
                .addHeader("X-App-Platform", "Android")

        val request = requestBuilder.build()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: ConnectException) {
            Timber.tag(TAG).e(e, e.message)
            /* TutorApplication.getInstance().getInternetStatusBus().onNext(false);*/
            throw e
        } catch (e: SocketTimeoutException) {
            Timber.tag(TAG).e(e, e.message)
            /* TutorApplication.getInstance().getInternetStatusBus().onNext(false);*/
            throw e
        } catch (e: UnknownHostException) {
            Timber.tag(TAG).e(e, e.message)
            /*TutorApplication.getInstance().getInternetStatusBus().onNext(false);*/
            throw e
        }

        return response
    }

}

