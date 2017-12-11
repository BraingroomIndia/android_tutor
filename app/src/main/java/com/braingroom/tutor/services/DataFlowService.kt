package com.braingroom.tutor.services


import android.util.Log
import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.braingroom.tutor.model.resp.MyProfileResp
import com.braingroom.tutor.model.req.CommonIdReq


/*
 * Created by godara on 05/10/17.
 */

@Suppress("unused")
class DataFlowService(private val api: ApiService,private val realmCacheService: RealmCacheService) {

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
            for (res in resp.data) {
                Log.d("logger", res.mediaTitle);
                res.isVideo = snippet.isVideo
            }
            resp
        }
    }

    fun getCategories(): Observable<CommonIdResp> {
        return realmCacheService.getCachedCommon("category").
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        Observable.just(CommonIdResp())
                    if (data.data.size == 1)
                        api.getCategories().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map{resp ->
                            realmCacheService.putCachedCommon(resp.data,"category")
                            resp
                        }.map { resp -> resp ?: CommonIdResp() }
                    return@flatMap Observable.just(data)
                }
        return api.getCategories().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp -> resp ?: CommonIdResp() }
    }

    fun getCommunity(): Observable<CommonIdResp> {
        return realmCacheService.getCachedCommon("community").
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        api.getCommunity().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map{resp ->
                            realmCacheService.putCachedCommon(resp.data,"community")
                            resp
                        }.map { resp -> resp ?: CommonIdResp() }
                    if (data.data.size == 1)
                        api.getCommunity().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map{resp ->
                            realmCacheService.putCachedCommon(resp.data,"community")
                            resp
                        }.map { resp -> resp ?: CommonIdResp() }
                    return@flatMap Observable.just(data)
                }
    }

    fun getCountry(): Observable<CommonIdResp> {

        return realmCacheService.getCachedCommon("country").
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        return@flatMap Observable.just(CommonIdResp())
                    if (data.data.size == 1)
                        return@flatMap api.getCountry().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map{resp ->
                            realmCacheService.putCachedCommon(resp.data,"country")
                            resp
                        }.map { resp -> resp ?: CommonIdResp() }
                    return@flatMap realmCacheService.getCachedCommon("country")
                }

        }
    fun getState(countryId: Int): Observable<CommonIdResp> {
        return api.getState(StateReq(countryId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp -> resp ?: CommonIdResp() }
    }

    fun getCity(stateId: Int): Observable<CommonIdResp> {
        return api.getCity(CityReq(stateId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp -> resp ?: CommonIdResp() }
    }

    fun getLocality(cityId: Int): Observable<CommonIdResp> {
        return api.getLocalities(LocalityReq(cityId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp -> resp ?: CommonIdResp() }
    }
}