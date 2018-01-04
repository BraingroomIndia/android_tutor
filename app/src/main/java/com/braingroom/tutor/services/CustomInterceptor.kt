package com.braingroom.tutor.services


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
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
        val request = requestBuilder.build()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: ConnectException) {
            Log.e(TAG, e.message + e)
            /* TutorApplication.getInstance().getInternetStatusBus().onNext(false);*/
            throw e
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.message + e)
            /* TutorApplication.getInstance().getInternetStatusBus().onNext(false);*/
            throw e
        } catch (e: UnknownHostException) {
            Log.e(TAG, e.message + e)
            /*TutorApplication.getInstance().getInternetStatusBus().onNext(false);*/
            throw e
        }

        return response
    }

}

