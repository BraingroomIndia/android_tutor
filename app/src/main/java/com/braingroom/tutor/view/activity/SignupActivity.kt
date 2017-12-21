package com.braingroom.tutor.view.activity

import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.model.req.SignUpReq
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.fragment.*
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.SignupViewModel

/*
 * Created by ashketchup on 30/11/17.
 */
class SignupActivity : Activity() {

    val FIRST_FRAGMENT = "firstFragment"
    val SECOND_FRAGMENT = "secondFragment"
    val THIRD_FRAGMENT= "thirdFragment"

    override val layoutId: Int = R.layout.activity_signup;



    override val vm: SignupViewModel by lazy {
        SignupViewModel(object : UiHelper {
            override fun firstFragment() {
                navigator.openFragment(SignUpFirstFragment.newInstance(FIRST_FRAGMENT))
            }

            override fun secondFragment() {
                navigator.openFragment(SignUpSecondFragment.newInstance(SECOND_FRAGMENT))
            }

            override fun thirdFragment() {
                navigator.openFragment(SignUpThirdFragment.newInstance(THIRD_FRAGMENT))
            }

            override fun signUp() {
                Log.d("why","why")
                navigator.finishActivity()
            }
        }, object : FragmentHelper {
            override fun show(tag: String) {
                navigator.openFragment(tag, SearchSelectListFragment.newInstance(tag))
            }

            override fun remove(tag: String) {
                popBackStack(tag)
            }
        })
    }


    override fun getFragmentViewModel(title: String): ViewModel {

        return when (title) {
            Country -> vm.countryVm
            State -> vm.stateVm
            City -> vm.cityVm
            Locality -> vm.localityVm
            College -> vm.instituteVm
            else -> vm
        }

    }

    interface UiHelper {
        fun firstFragment()
        fun secondFragment()
        fun thirdFragment()
        fun signUp()
    }
}