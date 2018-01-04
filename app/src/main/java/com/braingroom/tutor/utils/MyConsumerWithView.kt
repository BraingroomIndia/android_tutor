package com.braingroom.tutor.utils

import android.view.View
import io.reactivex.annotations.NonNull

/**
 * Created by ashketchup on 1/12/17.
 */
interface MyConsumerWithView<in T> {
    fun accept(@NonNull viewModel: T, view: View)
}