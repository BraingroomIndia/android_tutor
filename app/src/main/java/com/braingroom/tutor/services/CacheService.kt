package com.braingroom.tutor.services

import com.braingroom.tutor.model.data.CountryCache
import com.braingroom.tutor.model.resp.CommonIdResp
import io.reactivex.Observable

/**
 * Created by ashketchup on 11/12/17.
 */
public interface CacheService{
    fun getCachedCountries(): Observable<CommonIdResp>
    fun putCountries(countriesList:List<CommonIdResp.Snippet>):CommonIdResp
}