package com.braingroom.tutor.services

import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import com.braingroom.tutor.model.resp.CategoryResp
import retrofit2.http.GET
import com.braingroom.tutor.model.req.InstituteReq
import com.braingroom.tutor.model.resp.CommonIdResp
import com.braingroom.tutor.model.req.CityReq
import com.braingroom.tutor.model.req.LocalityReq




/*
 * Created by godara on 26/09/17.
 */

interface ApiService {
    @POST("login")
    fun login(@Body req: LoginReq): Observable<LoginResp?>

    @POST("socialLogin")
    fun socialLogin(@Body req: SocialLoginReq): Observable<LoginResp>

    @POST("getProfile")
    fun getProfile(@Body req: CommonIdReq): Observable<MyProfileResp>

    @POST("getAllClasses/{pageIndex}")
    fun getAllClasses(@Path("pageIndex") pageIndex: String, @Body req: ClassListReq): Observable<ClassListResp>

    @POST("getInstitions")
    fun getInstitute(@Body req: InstituteReq): Observable<CommonIdResp>

    @POST("getUsers")
    fun getUser(@Body req: UserListReq): Observable<CommonIdResp>

    @POST("getSchools")
    fun getSchools(@Body req: InstituteReq): Observable<CommonIdResp>

    @POST("getGallary")
    fun getGallery(@Body req: GalleryReq): Observable<GalleryResp>

    @POST("getCategory")
    fun getCategories(): Observable<CommonIdResp>

    @GET("getCategory")
    fun getCommunity(): Observable<CommonIdResp>

    @POST("getCountry")
    fun getCountry(): Observable<CommonIdResp>

    @POST("getState")
    fun getState(@Body req: StateReq): Observable<CommonIdResp>

    @POST("getCity")
    fun getCity(@Body req: CityReq): Observable<CommonIdResp>

    @POST("getLocality")
    fun getLocalities(@Body req: LocalityReq): Observable<CommonIdResp>


}
