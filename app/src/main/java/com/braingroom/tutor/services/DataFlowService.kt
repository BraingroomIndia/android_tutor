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

@Suppress("unused")
class DataFlowService(private val api: ApiService) {

    var userId = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId


    fun login(data: LoginReq.Snippet): Observable<LoginResp> {
        return api.login(LoginReq(data)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { LoginResp() }.map { resp -> resp ?: LoginResp() }
    }

    fun login(data: SocialLoginReq.Snippet): Observable<LoginResp> {
        return api.socialLogin(SocialLoginReq(data)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { LoginResp() }.map { resp -> resp ?: LoginResp() }
    }

    fun getMyProfile(id: String): Observable<MyProfileResp> {
        return api.getProfile(CommonIdReq(CommonIdReq.Snippet(id))).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn({ MyProfileResp() }).map { resp -> resp ?: MyProfileResp() }
    }

    fun getAllClasses(snippet: ClassListReq.Snippet, pageNumber: Int): Observable<ClassListResp?> {
        return api.getAllClasses(if (pageNumber > 0) pageNumber.toString() else "", ClassListReq(snippet)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { ClassListResp() }.map { resp -> resp ?: ClassListResp() }
    }

    fun getInstitute(keyword: String): Observable<CommonIdResp> {

        return api.getInstitute(InstituteReq(InstituteReq.Snippet(keyword))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getLearner(keyword: String): Observable<CommonIdResp> {

        return api.getUser(UserListReq(UserListReq.Snippet(keyword, 2, "1"))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun geTutor(keyword: String): Observable<CommonIdResp> {

        return api.getUser(UserListReq(UserListReq.Snippet(keyword, 1, "1"))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    fun getSchools(keyword: String): Observable<CommonIdResp> {

        return api.getSchools(InstituteReq(InstituteReq.Snippet(keyword))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGallery(snippet: GalleryReq.Snippet): Observable<GalleryResp> {
        return api.getGallery(GalleryReq(snippet)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { GalleryResp() }.map { resp -> resp ?: GalleryResp() }.map { resp ->
            for(res in resp.data){
                Log.d("logger",res.mediaTitle);
                res.isVideo=snippet.isVideo
            }
            resp
        }
    }

    fun getCategories():Observable<CategoryResp>{
        return api.getCategories().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CategoryResp() }.map { resp-> resp?:CategoryResp() }
    }
}