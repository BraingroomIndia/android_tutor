package com.braingroom.tutor.services


import android.util.Log
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import com.braingroom.tutor.model.resp.MyProfileResp
import com.braingroom.tutor.model.req.CommonIdReq
import com.braingroom.tutor.utils.braingroomId


/*
 * Created by godara on 05/10/17.
 */

@Suppress("unused", "USELESS_ELVIS")
class DataFlowService(private val api: ApiService) {

    var userId = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId


    fun login(data: LoginReq.Snippet): Observable<LoginResp> {
        return api.login(LoginReq(data)).onErrorReturn { LoginResp() }.map { resp -> resp ?: LoginResp() }.
                doOnError { error: Throwable ->
                    Log.d("login Api Error\t", error.localizedMessage);
                    Log.d("login Api Error\t", error.message)
                    error.printStackTrace()
                }.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
    }

    fun login(data: SocialLoginReq.Snippet): Observable<LoginResp> {
        return api.socialLogin(SocialLoginReq(data)).onErrorReturn { LoginResp() }.map { resp -> resp ?: LoginResp() }.
                doOnError { error: Throwable ->
                    Log.d("Social Api Error\t", error.localizedMessage);
                    Log.d("Social Api Error", error.message)
                    error.printStackTrace()
                }.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
    }

    fun getMyProfile(id: String): Observable<MyProfileResp> {
        return api.getProfile(CommonIdReq(CommonIdReq.Snippet(id))).onErrorReturn({ MyProfileResp() }).map { resp -> resp ?: MyProfileResp() }.
                doOnError { error: Throwable ->
                    Log.d("getMyProfile ", error.localizedMessage)
                    Log.d("getMyProfile", error.message)
                    error.printStackTrace()
                }.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
    }

    fun getAllClasses(snippet: ClassListReq.Snippet, pageNumber: Int): Observable<ClassListResp?> {
        return api.getAllClasses(if (pageNumber > 0) pageNumber.toString() else "", ClassListReq(snippet)).onErrorReturn { ClassListResp() }.map { resp -> resp ?: ClassListResp() }.
                doOnError { error: Throwable ->
                    Log.d("getAllClasses", error.localizedMessage)
                    Log.d("getAllClasses", error.message)
                    error.printStackTrace()
                }.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
    }
}