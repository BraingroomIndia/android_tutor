package com.braingroom.tutor.viewmodel.item

import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 22/12/17.
 */

class ReviewItemViewModel():ViewModel(){
    var review:String=""
    var rating:String ="3"
    var name:String=""
    var title:String=""

    constructor(review:String,rating:Int,name:String,title:String) : this(){
        this.review=review
        this.rating=""+rating
        this.name=name
        this.title=title

    }
}