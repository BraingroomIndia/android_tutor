package com.braingroom.tutor.utils

/**
 * Created by ashketchup on 1/12/17.
 */
import io.reactivex.annotations.NonNull

/**
 * Created by agrahari on 12/04/17.
 */

interface MyConsumer<T> {
    fun accept(@NonNull var1: T)
}
