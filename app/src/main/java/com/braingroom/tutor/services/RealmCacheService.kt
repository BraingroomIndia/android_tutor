package com.braingroom.tutor.services

import com.braingroom.tutor.model.data.CommonIdRealmWrapper
import com.braingroom.tutor.model.data.CountryCache
import com.braingroom.tutor.model.data.CommonIdSnippetWrapper
import com.braingroom.tutor.model.resp.CommonIdResp
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList


/**
 * Created by ashketchup on 11/12/17.
 */
public class RealmCacheService:CacheService{
    override fun getCachedCountries(): Observable<CommonIdResp>{
        val realm = Realm.getDefaultInstance()
        val x = CountryCache()
        val country:MutableList<CommonIdResp.Snippet> = mutableListOf()
        var data :CountryCache ?=realm.where(x.javaClass).equalTo("searchQuery","country").findFirst()
        if(data==null)
            return Observable.just(CommonIdResp(null))
        (data.data.isNotEmpty()).let {
                for(d in data.data){
                    country.add(d.data.toSnippet())
                }
        }
        return Observable.just(CommonIdResp(country))
    }

    override fun putCountries(countriesList: List<CommonIdResp.Snippet>):CommonIdResp{
        var realmList = RealmList<CommonIdRealmWrapper>()
        for(snippet in countriesList){
            realmList.add(CommonIdRealmWrapper(CommonIdSnippetWrapper(snippet)))
        }
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm ->
            realm.insert(CountryCache.create(realmList))
        }
        return CommonIdResp(countriesList)
    }
}