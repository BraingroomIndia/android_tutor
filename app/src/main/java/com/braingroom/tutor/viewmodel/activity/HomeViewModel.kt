package com.braingroom.tutor.viewmodel.activity


import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.SecondActivity
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.HomeItemViewModel
import io.reactivex.functions.Action
import java.util.ArrayList


/*
 * Created by godara on 27/09/17.
 */
class HomeViewModel : ViewModel() {

    val onClick: Action by lazy {
        Action {
            navigator?.navigateActivity(SecondActivity::class.java)
        }
    }
    val spanCount by lazy { 2 }
    val homeItemViewModels: List<ViewModel>
    val homeItemViewProvider: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                return R.layout.item_home;
            }
        }
    }
    private val bottomDrawableList = ArrayList<@ColorRes Int>(8)
    private val topDrawableList = ArrayList<@DrawableRes Int>(8)


    init {
        bottomDrawableList += R.color.material_pinkA400
        bottomDrawableList += R.color.material_blue400
        bottomDrawableList += R.color.material_pink200
        bottomDrawableList += R.color.material_lightgreen500
        bottomDrawableList += R.color.material_purpleA100
        bottomDrawableList += R.color.material_cyan300
        bottomDrawableList += R.color.material_pink500
        bottomDrawableList += R.color.material_lightgreen600
        topDrawableList += R.drawable.ic_add_class
        topDrawableList += R.drawable.ic_myclass_36dp
        topDrawableList += R.drawable.ic_payment_36dp
        topDrawableList += R.drawable.ic_attendance_36dp
        topDrawableList += R.drawable.ic_gallery_36dp
        topDrawableList += R.drawable.ic_calnder_36dp
        topDrawableList += R.drawable.ic_review_36dp
        topDrawableList += R.drawable.ic_promot_36dp

        homeItemViewModels = (0..7).mapTo(ArrayList<HomeItemViewModel>(8)) { HomeItemViewModel(topDrawableList[it], bottomDrawableList[it], "Hello") }

    }
}