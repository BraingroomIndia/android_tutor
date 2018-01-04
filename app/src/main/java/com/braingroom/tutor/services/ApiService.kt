package com.braingroom.tutor.services

import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


/*
 * Created by godara on 26/09/17.
 */

interface ApiService {
    @POST("apis/login")
    fun login(@Body req: LoginReq): Observable<LoginResp?>

    @POST("apis/socialLogin")
    fun socialLogin(@Body req: SocialLoginReq): Observable<LoginResp>

    @POST("apis/getProfile")
    fun getProfile(@Body req: CommonIdReq): Observable<MyProfileResp>

    @POST("tutorApi/getAllClasses/{pageIndex}")
    fun getAllClasses(@Path("pageIndex") pageIndex: String, @Body req: ClassListReq): Observable<ClassListResp>

    @POST("apis/BuyerRegistration")
    fun signUp(@Body req: SignUpReq): Observable<SignUpResp>

    @POST("tutorApi/getPaymentSummaryByClasses/{pageIndex}")
    fun getPaymentSummaryByClasses(@Path("pageIndex") pageIndex: String, @Body req: PaymentSummaryReq): Observable<PaymentDetailsResp>

    @POST("getInstitions")
    fun getInstitute(@Body req: InstituteReq): Observable<CommonIdResp>

    @POST("apis/getUsers")
    fun getUser(@Body req: UserListReq): Observable<CommonIdResp>

    @POST("apis/getSchools")
    fun getSchools(@Body req: InstituteReq): Observable<CommonIdResp>

    @POST("apis/getGallary")
    fun getGallery(@Body req: GalleryReq): Observable<GalleryResp>

    @POST("apis/getCategory")
    fun getCategories(): Observable<CommonIdResp>

    @GET("apis/getCommunity")
    fun getCommunity(): Observable<CommonIdResp>

    @POST("apis/getCountry")
    fun getCountry(): Observable<CommonIdResp>

    @POST("apis/getState")
    fun getState(@Body req: StateReq): Observable<CommonIdResp>

    @POST("apis/getCity")
    fun getCity(@Body req: CityReq): Observable<CommonIdResp>

    @POST("apis/changeNotificationStatus")
    fun changeNotificationStatus(@Body req: ChangeNotificationStatusReq): Observable<CommonIdResp>

    @POST("apis/getUnreadNotificationCount")
    fun getUnreadNotificationCount(@Body req: CommonIdReq): Observable<NotificationCountResp>

    @Multipart
    @POST("tutorApi/uploadMedia")
    fun uploadImage(@Part file: MultipartBody.Part): Observable<UploadMediaResp>
    @POST("apis/getMessage")
    fun getMessages(@Body req: MessagesGetReq): Observable<MessageGetResp>

    @POST("apis/postMessage")
    fun reply(@Body req: MessageReplyReq): Observable<CommonIdResp>

    @POST("apis/changePassword")
    fun changePassword(@Body req: ChangePasswordReq): Observable<ChangePasswordResp>

    @POST("apis/getChatMessages")
    fun getMessageThread(@Body req: ChatMessageReq): Observable<ChatMessageResp>

    @POST("apis/changeMessageThreadStatus")
    fun changeMessageThreadStatus(@Body req: ChatMessageReq): Observable<CommonIdResp>

    @POST("apis/getLocality")
    fun getLocalities(@Body req: LocalityReq): Observable<CommonIdResp>

    @POST("apis/getUserNotifications/{pageIndex}")
    fun getUserNotifications(@Path("pageIndex") pageIndex: String, @Body notificationGetReq: CommonIdReq): Observable<NotificationListResp>

    @POST("apis/getReviews/{pageIndex}")
    fun reviewGet(@Path("pageIndex") pageIndex: String, @Body reviewGetReq: ReviewGetReq): Observable<ReviewGetResp>


    @POST("tutorApi/getPaymentSummary")
    fun getPaymentSummary(@Body req: PaymentSummaryReq): Observable<PaymentSummaryResp>

    @POST("tutorApi/getAttendances")
    fun getStartOrEndDetails(@Body req: AttendanceDetailReq): Observable<AttendanceDetailResp>

    @POST("tutorApi/updateAttendance")
    fun updateAttendance(@Body req: UpdateAttendanceReq): Observable<UpdateAttendanceResp>

}
