package com.braingroom.tutor.services


import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.req.CommonIdReq
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/*
 * Created by godara on 05/10/17.
 */

@Suppress("unused")
class DataFlowService(private val api: ApiService, private val realmCacheService: RealmCacheService) {

    var userId: String = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId


    fun login(data: LoginReq.Snippet): Observable<LoginResp> {
        return api.login(LoginReq(data)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { LoginResp() }.map { resp -> resp }
    }

    fun login(data: SocialLoginReq.Snippet): Observable<LoginResp> {
        return api.socialLogin(SocialLoginReq(data)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { LoginResp() }.map { resp -> resp }
    }

    fun getMyProfile(id: String): Observable<MyProfileResp> {
        return api.getProfile(CommonIdReq(CommonIdReq.Snippet(id))).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn({ MyProfileResp() }).map { resp -> resp }
    }

    fun getAllClasses(snippet: ClassListReq.Snippet, pageNumber: Int): Observable<ClassListResp?> {
        return api.getAllClasses(if (pageNumber > 1) pageNumber.toString() else "", ClassListReq(snippet)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { ClassListResp() }.map { resp -> resp }
    }

    fun getPaymentDetails(pageNumber: Int): Observable<PaymentDetailsResp> {
        return api.getPaymentDetails(if (pageNumber > 1) pageNumber.toString() else "", CommonIdReq(userId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { PaymentDetailsResp() }
    }

    fun getInstitute(keyword: String): Observable<CommonIdResp> {//Cache

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

    fun signUp(signUpReq: SignUpReq): Observable<SignUpResp> {

        return api.signUp(signUpReq).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribeOn(Schedulers.io())
    }


    fun getSchools(keyword: String): Observable<CommonIdResp> {//Cache

        return api.getSchools(InstituteReq(InstituteReq.Snippet(keyword))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGallery(snippet: GalleryReq.Snippet): Observable<GalleryResp> {
        return api.getGallery(GalleryReq(snippet)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { GalleryResp() }.map { resp -> resp }.map { resp ->
            for (res in resp.data) {
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
                        return@flatMap realmCacheService.getCachedCommon("category")
                    if (data.data.size == 1)
                        return@flatMap api.getCategories().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
                            realmCacheService.putCachedCommon(resp.data, "category")
                            resp
                        }.map { resp -> resp }
                    return@flatMap realmCacheService.getCachedCommon("category")
                }
    }

    fun getCommunity(): Observable<CommonIdResp> {
        return realmCacheService.getCachedCommon("community").
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        return@flatMap realmCacheService.getCachedCommon("community")
                    if (data.data.size == 1)
                        api.getCommunity().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
                            realmCacheService.putCachedCommon(resp.data, "community")
                            resp
                        }.map { resp -> resp }
                    return@flatMap realmCacheService.getCachedCommon("community")
                }
    }

    fun getCountry(): Observable<CommonIdResp> {

        return realmCacheService.getCachedCommon("country").
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        return@flatMap realmCacheService.getCachedCommon("country")
                    if (data.data.size == 1)
                        return@flatMap api.getCountry().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
                            realmCacheService.putCachedCommon(resp.data, "country")
                            resp
                        }.map { resp -> resp }
                    return@flatMap realmCacheService.getCachedCommon("country")
                }
    }

    fun getState(countryId: Int): Observable<CommonIdResp> {
        return realmCacheService.getCachedCommon("state" + countryId).
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        return@flatMap realmCacheService.getCachedCommon("state" + countryId)
                    if (data.data.size == 1)
                        return@flatMap api.getState(StateReq(countryId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
                            realmCacheService.putCachedCommon(resp.data, "state" + countryId)
                            resp
                        }.map { resp -> resp }
                    return@flatMap realmCacheService.getCachedCommon("state" + countryId)
                }
    }

    fun getCity(stateId: Int): Observable<CommonIdResp> {
        return realmCacheService.getCachedCommon("city" + stateId).
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        return@flatMap realmCacheService.getCachedCommon("city" + stateId)
                    if (data.data.size == 1)
                        return@flatMap api.getCity(CityReq(stateId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
                            realmCacheService.putCachedCommon(resp.data, "city" + stateId)
                            resp
                        }.map { resp -> resp }
                    return@flatMap realmCacheService.getCachedCommon("city" + stateId)
                }
    }

    fun getReview(pageNumber: Int): Observable<ReviewGetResp> {
        return api.reviewGet(pageNumber.toString(), ReviewGetReq(ReviewGetReq.Snippet(userId))).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation()).onErrorReturnItem(ReviewGetResp())

    }


    fun getLocality(cityId: Int): Observable<CommonIdResp> {
        return realmCacheService.getCachedCommon("locality" + cityId).
                defaultIfEmpty(CommonIdResp(null)).
                flatMap { data ->
                    if (data == null)//TODO Handle this case
                        return@flatMap realmCacheService.getCachedCommon("locality" + cityId)
                    if (data.data.size == 1)
                        return@flatMap api.getLocalities(LocalityReq(cityId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
                            realmCacheService.putCachedCommon(resp.data, "locality" + cityId)
                            resp
                        }.map { resp -> resp }
                    return@flatMap realmCacheService.getCachedCommon("locality" + cityId)
                }
    }

    fun getNotifications(pageIndex: Int): Observable<NotificationListResp> {
        return api.getUserNotifications("" + pageIndex, CommonIdReq(CommonIdReq.Snippet(userId))).subscribeOn(Schedulers.io())
                .onErrorReturn { NotificationListResp() }
                .observeOn(Schedulers.computation()).map { resp -> resp }
        //TODO Handle error return parts
    }

    fun changeNotificationStatus(notificationId: String): Observable<CommonIdResp> {

        return api.changeNotificationStatus(ChangeNotificationStatusReq(ChangeNotificationStatusReq.Snippet(userId, notificationId))).subscribeOn(Schedulers.io())
                .onErrorReturn { CommonIdResp() }
                .observeOn(Schedulers.computation())
    }

    fun getUnreadNotificationCount(): Observable<NotificationCountResp> {
        return api.getUnreadNotificationCount(CommonIdReq(CommonIdReq.Snippet(userId))).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation()).map { resp -> resp }
        //TODO Handle error return parts
    }
}