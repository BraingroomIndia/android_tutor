package com.braingroom.tutor.services

import com.braingroom.tutor.model.resp.CommonIdResp
import io.reactivex.Observable

/**
 * Created by ashketchup on 11/12/17.
 */
public interface CacheService{
    fun getCachedCommon(searchQuery:String): Observable<CommonIdResp>
    fun putCachedCommon(commonIdRespList:List<CommonIdResp.Snippet>, searchQuery: String):CommonIdResp
}