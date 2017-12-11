package com.braingroom.tutor.model.data;

import io.realm.RealmObject;

/**
 * Created by ashketchup on 11/12/17.
 */

public class CommonIdRealmWrapper extends RealmObject{
    public CommonIdSnippetWrapper data;
    public   CommonIdRealmWrapper(CommonIdSnippetWrapper data){
        this.data=data;
    }
    public CommonIdRealmWrapper(){

    }
}
