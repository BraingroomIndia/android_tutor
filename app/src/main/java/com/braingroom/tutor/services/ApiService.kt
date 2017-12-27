package com.braingroom.tutor.services

import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


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

    @POST("apis/getAllClasses/{pageIndex}")
    fun getAllClasses(@Path("pageIndex") pageIndex: String, @Body req: ClassListReq): Observable<ClassListResp>

    @POST("apis/BuyerRegistration")
    fun signUp(@Body req: SignUpReq): Observable<SignUpResp>

    @POST("getPaymentDetails/{pageIndex}")
    fun getPaymentDetails(@Path("pageIndex") pageIndex: String, @Body req:CommonIdReq):Observable<PaymentDetailsResp>

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

    @GET("apis/getCategory")
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

    @POST("apis/getMessage")
    fun getMessages(@Body req:MessagesGetReq):Observable<MessageGetResp>

    @POST("apis/postMessage")
    fun reply(@Body req:MessageReplyReq):Observable<CommonIdResp>

    @POST("apis/getChatMessages")
    fun getMessageThread(@Body req:ChatMessageReq):Observable<ChatMessageResp>

    @POST("apis/changeMessageThreadStatus")
    fun changeMessageThreadStatus(@Body req: ChatMessageReq): Observable<CommonIdResp>

    @POST("apis/getLocality")
    fun getLocalities(@Body req: LocalityReq): Observable<CommonIdResp>

    @POST("apis/getUserNotifications/{pageIndex}")
    fun getUserNotifications(@Path("pageIndex") pageIndex: String, @Body notificationGetReq: CommonIdReq): Observable<NotificationListResp>

    @POST("apis/getReviews/{pageIndex}")
    fun reviewGet(@Path("pageIndex") pageIndex: String, @Body reviewGetReq: ReviewGetReq): Observable<ReviewGetResp>

}
