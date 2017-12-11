package com.braingroom.tutor.model.data;

import com.braingroom.tutor.model.resp.CommonIdResp;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ashketchup on 11/12/17.
 */
public class CountryCache extends RealmObject{
    RealmList<CommonIdRealmWrapper> data;
    @PrimaryKey
    public String searchQuery="country";
    public void setData(RealmList<CommonIdRealmWrapper> data){
        this.data=data;
    }
    public CountryCache(){

    }
    public List<CommonIdRealmWrapper> getData(){
        return data;
    }
    public void setSearchKey(String searchKey){
        this.searchQuery=searchKey;
    }
    public String getSearchKey(){
        return searchQuery;
    }
        public CountryCache(RealmList<CommonIdRealmWrapper> data){
            this.data=data;
        }
        public static CountryCache create(RealmList<CommonIdRealmWrapper> realmList){
            return new CountryCache(realmList);
        }
    }
