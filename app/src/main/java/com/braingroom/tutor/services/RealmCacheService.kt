package com.braingroom.tutor.services

import com.braingroom.tutor.model.data.CommonIdRealmWrapper
import com.braingroom.tutor.model.data.CommonIdSnippetWrapper
import com.braingroom.tutor.model.resp.CommonIdResp
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList


/**
 * Created by ashketchup on 11/12/17.
 */
public class RealmCacheService:CacheService{
    override fun getCachedCommon(searchQuery: String): Observable<CommonIdResp>{
        val realm = Realm.getDefaultInstance()
        val x = CommonIdRealmWrapper()
        val item:MutableList<CommonIdResp.Snippet> = mutableListOf()
        var data : CommonIdRealmWrapper?=realm.where(x.javaClass).equalTo("searchQuery",searchQuery).findFirst()
        if(data==null)
            return Observable.just(CommonIdResp(null))
        (data.data.isNotEmpty()).let {
                for(d in data.data){
                    item.add(d.toSnippet())
                }
        }
        return Observable.just(CommonIdResp(item))
    }

    override fun putCachedCommon(countriesList: List<CommonIdResp.Snippet>, searchQuery:String):CommonIdResp{
        var realmList = RealmList<CommonIdSnippetWrapper>()
        for(snippet in countriesList){
            realmList.add(CommonIdSnippetWrapper(snippet))
        }
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm ->
            realm.insert(CommonIdRealmWrapper.create(realmList,searchQuery))
        }
        return CommonIdResp(countriesList)
    }
}