package com.braingroom.tutor.services

import com.braingroom.tutor.model.req.*
import com.braingroom.tutor.model.resp.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

/*
 * Created by godara on 26/09/17.
 */

interface ApiService {
    @POST("login")
    fun login(@Body req: LoginReq): Observable<LoginResp?>

    @POST("socialLogin")
    fun socialLogin(@Body req: SocialLoginReq): Observable<LoginResp?>

    @POST("getProfile")
    fun getProfile(@Body req: CommonIdReq): Observable<MyProfileResp?>

    @POST("getAllClasses/{pageIndex}")
    fun getAllClasses(@Path("pageIndex") pageIndex: String, @Body req: ClassListReq): Observable<ClassListResp?>

    @POST("getInstitions")
    abstract fun getInstitute(@Body req: InstituteReq): Observable<CommonIdResp>

    @POST("getUsers")
    abstract fun getUser(@Body req: UserListReq): Observable<CommonIdResp>

    @POST("getSchools")
    abstract fun getSchools(@Body req: InstituteReq): Observable<CommonIdResp>
}
