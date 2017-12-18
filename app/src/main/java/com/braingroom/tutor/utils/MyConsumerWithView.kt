package com.braingroom.tutor.utils

import io.reactivex.annotations.NonNull

/**
 * Created by ashketchup on 1/12/17.
 */
interface MyConsumerWithView<T,View>{
    fun accept(@NonNull v1:T,v2:View)
}