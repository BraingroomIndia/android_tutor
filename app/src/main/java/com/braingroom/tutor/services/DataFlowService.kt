package com.braingroom.tutor.services


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

@Suppress("unused")
class DataFlowService(private val api: ApiService) {

    var userId = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId


    fun login(data: LoginReq.Snippet): Observable<LoginResp> {
        return api.login(LoginReq(data)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).
                onErrorReturn { LoginResp() }.map { resp -> resp ?: LoginResp() }
    }

    fun login(data: SocialLoginReq.Snippet): Observable<LoginResp> {
        return api.socialLogin(SocialLoginReq(data)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).
                onErrorReturn { LoginResp() }.map { resp -> resp ?: LoginResp() }
    }

    fun getMyProfile(id: String): Observable<MyProfileResp> {
        return api.getProfile(CommonIdReq(CommonIdReq.Snippet(id))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                onErrorReturn({ MyProfileResp() }).map { resp -> resp ?: MyProfileResp() }
    }

    fun getAllClasses(snippet: ClassListReq.Snippet, pageNumber: Int): Observable<ClassListResp?> {
        return api.getAllClasses(if (pageNumber > 0) pageNumber.toString() else "", ClassListReq(snippet)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                onErrorReturn { ClassListResp() }.map { resp -> resp ?: ClassListResp() }
    }
}