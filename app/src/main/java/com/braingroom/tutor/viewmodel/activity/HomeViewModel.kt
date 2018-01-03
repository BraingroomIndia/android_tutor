package com.braingroom.tutor.viewmodel.activity


import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.braingroom.tutor.R
import com.braingroom.tutor.view.activity.*
import com.braingroom.tutor.view.activity.BarcodeCaptureActivity
import com.braingroom.tutor.view.adapters.ViewProvider
import com.braingroom.tutor.view.fragment.AttendanceStatusFragment
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.item.HomeItemViewModel
import com.braingroom.tutor.viewmodel.item.NotifyDataSetChanged
import io.reactivex.functions.Action
import java.util.*


/*
 * Created by godara on 27/09/17.
 */
class HomeViewModel : ViewModel() {

    val onClick: Action by lazy {
        Action {
        }
    }
    val spanCount = 2

    val view: ViewProvider by lazy {
        object : ViewProvider {
            override fun getView(vm: ViewModel?): Int {
                if (vm == null)
                    throw NullPointerException()
                return when (vm) {
                    is HomeItemViewModel -> R.layout.item_home
                    else -> throw NoSuchFieldError()
                }

            }
        }
    }
    private val bottomDrawableList = ArrayList<@ColorRes Int>(8)
    private val topDrawableList = ArrayList<@DrawableRes Int>(8)
    private val textList = ArrayList<String>(8)
    private val actionList = ArrayList<Action>(8)


    init {
        topDrawableList += R.drawable.ic_add_class//1
        textList += "Add Class"  //1
        bottomDrawableList += R.color.material_pinkA400 //1
        actionList += Action { } //1


        topDrawableList += R.drawable.ic_myclass_36dp//2
        textList += "My Class"   //2
        bottomDrawableList += R.color.material_blue400  //2
        actionList += Action { navigator?.navigateActivity(MyClassesActivity::class.java) } //2


        topDrawableList += R.drawable.ic_payment_36dp//3
        textList += "Payment Details" //3
        bottomDrawableList += R.color.material_pink200  //3
        actionList += Action { navigator?.navigateActivity(PaymentDetailActivity::class.java) } //3


        topDrawableList += R.drawable.ic_attendance_36dp//4
        textList += "Attendance" //4
        bottomDrawableList += R.color.material_lightgreen500 //4
        actionList += Action { navigator?.navigateActivity(BarcodeCaptureActivity::class.java) } //4


        topDrawableList += R.drawable.ic_gallery_36dp//5
        textList += "Media" //5
        bottomDrawableList += R.color.material_purpleA100  //5
        actionList += Action { navigator?.navigateActivity(MediaActivity::class.java) } //5


        topDrawableList += R.drawable.ic_calnder_36dp//6
        textList += "Booking Calender" //6
        bottomDrawableList += R.color.material_cyan300  //6
        actionList += Action {  } //6

        topDrawableList += R.drawable.ic_review_36dp//7
        textList += "Review" //7
        bottomDrawableList += R.color.material_pink500//7
        actionList += Action { navigator?.navigateActivity(ReviewActivity::class.java) } //7


        topDrawableList += R.drawable.ic_promot_36dp//8
        textList += "Promote" //8
        bottomDrawableList += R.color.material_lightgreen600//8
        actionList += Action { } //8


        (0..7).forEach { i -> item.onNext(HomeItemViewModel(topDrawableList[i], bottomDrawableList[i], textList[i], actionList[i])) }
        item.onNext(NotifyDataSetChanged())
    }
}
