package com.braingroom.tutor.services


import com.braingroom.tutor.common.CustomApplication
import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.req.CommonIdReq
import com.braingroom.tutor.model.resp.*
import com.braingroom.tutor.utils.GEO_TAG
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList


/*
 * Created by godara on 05/10/17.
 */

@Suppress("unused")
class DataFlowService(private val api: ApiService) {

    var userId: String = CustomApplication.getInstance().userId
        get() = CustomApplication.getInstance().userId

    fun getGeoDetail() {
        api.getGeoDetail().map({
            if (it.resCode)
                it.data[0].textValue
            else
                ""
        }).onErrorReturn { "" }.subscribeOn(Schedulers.io()).subscribe {
            CustomApplication.GEO_TAG = it
        }
    }

    fun login(data: LoginReq.Snippet): Observable<LoginResp> {
        return api.login(LoginReq(data)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { LoginResp() }
                .map { resp ->
                    if (resp.resCode) {
                        resp.data.emailId = data.email
                    }
                    resp
                }
    }

    fun login(data: SocialLoginReq.Snippet): Observable<LoginResp> {
        return api.socialLogin(SocialLoginReq(data)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { LoginResp() }.map {
            it.data.profilePic = data.profilePic
            it
        }
    }

    fun forgetPassword(email: String): Observable<String> {
        return api.forgotPassword(LoginReq(email)).subscribeOn(Schedulers.io()).map { it.resMsg }.onErrorReturn { it.localizedMessage }.observeOn(AndroidSchedulers.mainThread())
    }

    fun getMyProfile(id: String): Observable<MyProfileResp> {
        return api.getProfile(CommonIdReq(CommonIdReq.Snippet(id))).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn({ MyProfileResp() }).map { resp -> resp }
    }

    fun getAllClasses(snippet: ClassListReq.Snippet, pageNumber: Int): Observable<ClassListResp?> {
        return api.getAllClasses(if (pageNumber > 1) pageNumber.toString() else "", ClassListReq(snippet)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { ClassListResp() }.map { resp -> resp }
    }

    fun getClassDetail(classId: String): Observable<ClassDetailResp> {
        return api.getClassDetail(CommonIdReq(classId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
    }

    fun getPaymentDetails(pageNumber: Int, starDate: String, endDate: String, keyword: String): Observable<PaymentClassListResp> {
        return api.getPaymentSummaryByClasses(if (pageNumber > 1) pageNumber.toString() else "", PaymentSummaryReq(userId, starDate, endDate, keyword)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { PaymentClassListResp() }
    }

    fun getPaymentDetailByClass(pageNumber: Int, classId: String): Observable<PaymentClassDetailResp> {
        return api.getPaymentDetailByClass(if (pageNumber > 1) pageNumber.toString() else "", PaymentClassDetailReq(userId, classId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).
                onErrorReturn { PaymentClassDetailResp() }
    }

    /*  fun getInstitute(keyword: String): Observable<CommonIdResp> {//Cache

          return api.getInstitute(InstituteReq(InstituteReq.Snippet(keyword))).subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
      }*/

    fun getLearner(type: Int?, classId: String?): Observable<CommonIdResp> {

        return api.getUser(UserListReq(UserListReq.Snippet(userId, type, classId))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun signUp(signUpReq: SignUpReq): Observable<SignUpResp> {
        return api.signUp(signUpReq).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribeOn(Schedulers.io())
    }


    fun getSchools(keyword: String): Observable<CommonIdResp> {//Cache
        return api.getSchools(InstituteReq(InstituteReq.Snippet(keyword))).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGallery(req: GalleryReq): Observable<GalleryResp> {
        return api.getGallery(req).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { GalleryResp() }.map { resp -> resp }.map {
            if (it.resCode)
                it.data.forEach { it.isVideo = req.data.isVideo }
            it
        }
    }

    fun getMessages(): Observable<MessageGetResp> {
        return api.getMessages(MessagesGetReq(MessagesGetReq.Snippet(userId))).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { MessageGetResp() }
    }

    fun getMessageThread(senderId: String): Observable<ChatMessageResp> {
        return api.getMessageThread(ChatMessageReq(ChatMessageReq.Snippet(userId, senderId))).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { ChatMessageResp() }
    }

    fun changePassword(snippet: ChangePasswordReq.Snippet): Observable<ChangePasswordResp> {

        return api.changePassword(ChangePasswordReq(snippet)).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
    }

    fun postReply(senderId: String, message: String): Observable<CommonIdResp> {
        return api.reply(MessageReplyReq(MessageReplyReq.Snippet(userId, senderId, "", "", message, ""))).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }
    }

    fun changeMessageThreadStatus(senderId: String): Observable<CommonIdResp> {

        return api.changeMessageThreadStatus(ChatMessageReq(ChatMessageReq.Snippet(userId, senderId))).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
    }

    fun getCategories(): Observable<CommonIdResp> {
        return api.getCategories().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->
            resp
        }

    }


    fun getCommunity(): Observable<CommonIdResp> {
        return api.getCommunity().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->

            resp
        }.map { resp -> resp }
    }

    fun getCountry(): Observable<CommonIdResp> {

        return api.getCountry().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->

            resp
        }


    }

    fun getState(countryId: Int): Observable<CommonIdResp> {
        return api.getState(StateReq(countryId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->

            resp
        }.map { resp -> resp }

    }


    fun getCity(stateId: String): Observable<CommonIdResp> {
        return api.getCity(CityReq(stateId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->

            resp
        }


    }

    fun getReview(pageNumber: Int): Observable<ReviewGetResp> {
        return api.reviewGet(pageNumber.toString(), ReviewGetReq(userId)).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation()).map { resp ->
            if (resp.resCode) resp.data.forEach { if (it.classTopic.isNullOrBlank()) it.classTopic = "Vendor Review" }
            resp
        }.onErrorReturnItem(ReviewGetResp())

    }

    fun getClassList(): Observable<CommonIdResp> {
        return api.getClassList(CommonTutorIdReq(userId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }
                .map { resp -> resp }


    }

    fun getPaymentSummary(starDate: String, endDate: String): Observable<PaymentSummaryResp> {
        return api.getPaymentSummary(PaymentSummaryReq(userId, starDate, endDate)).onErrorReturnItem(PaymentSummaryResp()).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
    }


    fun getLocality(cityId: String): Observable<CommonIdResp> {
        return api.getLocalities(LocalityReq(cityId)).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).onErrorReturn { CommonIdResp() }.map { resp ->

            resp
        }.map { resp -> resp }

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

    fun getUnreadNotificationCount(): Observable<Int> {
        return api.getUnreadNotificationCount(CommonIdReq(CommonIdReq.Snippet(userId))).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation()).map { it.data.count }.onErrorReturnItem(0)
    }
    //TODO Handle error return parts


    fun getGender(): Observable<CommonIdResp> {
        val data = ArrayList<CommonIdResp.Snippet>(2)
        data.add(CommonIdResp.Snippet(1, "Male"))
        data.add(CommonIdResp.Snippet(2, "Female"))
        return Observable.just(CommonIdResp(data))

    }

    private fun prepareFilePart(partName: String, fileType: String, filePath: String): MultipartBody.Part {
        val file = File(filePath)
        return MultipartBody.Part.createFormData(partName, file.name, RequestBody.create(
                MediaType.parse(fileType),
                file
        ))
    }

    fun uploadImage(filePath: String, fileType: String): Observable<UploadMediaResp> {
        return api.uploadImage(prepareFilePart("file", fileType, filePath)
                , RequestBody.create(MediaType.parse("text/plain"), "image")).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateAttendance(learnerId: String, startOrEndCode: String, isStartCode: Boolean): Observable<AttendanceDetailResp> {
        return api.updateAttendance(UpdateAttendanceReq(UpdateAttendanceReq.Snippet(userId, learnerId, startOrEndCode, isStartCode))).subscribeOn(Schedulers.io())
                .onErrorReturn { AttendanceDetailResp() }
                .observeOn(Schedulers.computation())
    }


    fun getStartOrEndDetails(startOrEndCode: String, isStartCode: Boolean): Observable<AttendanceDetailResp> {
        return api.getStartOrEndDetails(AttendanceDetailReq(userId, startOrEndCode, isStartCode)).onErrorReturnItem(AttendanceDetailResp()).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation()).map { it.setRequestType(isStartCode) }
    }

    fun getStartOrEndDetails(req: AttendanceDetailReq): Observable<AttendanceDetailResp> {
        return api.getStartOrEndDetails(req).onErrorReturnItem(AttendanceDetailResp()).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation()).map { it.setRequestType(req.data?.isStartCode) }
    }

    fun registerFCMToken(fcmToken: String) {
        if (GEO_TAG.isBlank())
            api.getGeoDetail().map { it.data[0]?.textValue ?: "" }.onErrorReturn { "" }.subscribeOn(Schedulers.io()).
                    subscribe({
                        GEO_TAG = it
                        api.registerUserDevice(RegisterFCMToken(fcmToken, userId)).onErrorReturn { "" }.subscribeOn(Schedulers.io()).subscribe()
                    })
        else
            api.registerUserDevice(RegisterFCMToken(fcmToken, userId)).onErrorReturn { "" }.subscribeOn(Schedulers.io()).subscribe()

    }

    fun saveMedia(filePath: String, mediaType: String): Observable<CommonIdResp> {
        return api.saveMedia(SaveMediaReq(userId, filePath, mediaType)).onErrorReturn { CommonIdResp() }.observeOn(Schedulers.computation()).subscribeOn(Schedulers.io())
    }
}
