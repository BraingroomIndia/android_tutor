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
    RealmList<CommonIdSnippetWrapper> data;
    public void setData(RealmList<CommonIdSnippetWrapper> data){
        this.data=data;
    }
    public CountryCache(){

    }
    @PrimaryKey
    public String searchQuery="country";

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    public List<CommonIdSnippetWrapper> getData(){
        return data;
    }
        public CountryCache(RealmList<CommonIdSnippetWrapper> data){
            this.data=data;
        }
        public static CountryCache create(RealmList<CommonIdSnippetWrapper> realmList){
            return new CountryCache(realmList);
        }
    }
