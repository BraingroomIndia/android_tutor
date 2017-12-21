package com.braingroom.tutor.services

import android.util.Log
import com.braingroom.tutor.model.data.CommonIdRealmWrapper
import com.braingroom.tutor.model.data.CommonIdSnippetWrapper
import com.braingroom.tutor.model.resp.CommonIdResp
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList


/*
 * Created by ashketchup on 11/12/17.
 */
public class RealmCacheService : CacheService {
    override fun getCachedCommon(searchQuery: String): Observable<CommonIdResp> {
        val realm = Realm.getDefaultInstance()
        val x = CommonIdRealmWrapper()
        val item: MutableList<CommonIdResp.Snippet> = mutableListOf()
        val data: CommonIdRealmWrapper? = realm.where(x.javaClass).equalTo("searchQuery", searchQuery).findFirst() ?: return Observable.just(CommonIdResp(null))
        (data?.data?.isNotEmpty())?.let {
            data.data.mapTo(item) { it.toSnippet() }
        }
        return Observable.just(CommonIdResp(item))
    }


    override fun putCachedCommon(countriesList: List<CommonIdResp.Snippet>, searchQuery: String): CommonIdResp {
        val realmList = RealmList<CommonIdSnippetWrapper>()
        countriesList.mapTo(realmList) { CommonIdSnippetWrapper(it) }
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync({
            it.insert(CommonIdRealmWrapper.create(realmList, searchQuery))
        }, { e ->
            Log.e("Realm Error", e.message, e)
        })
        return CommonIdResp(countriesList)
    }
}