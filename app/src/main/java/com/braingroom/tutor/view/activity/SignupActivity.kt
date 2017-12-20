package com.braingroom.tutor.view.activity

import com.braingroom.tutor.R
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.view.fragment.SearchSelectListFragment
import com.braingroom.tutor.view.fragment.SignUpFirstFragment
import com.braingroom.tutor.view.fragment.SignUpSecondFragment
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.SignupViewModel

/*
 * Created by ashketchup on 30/11/17.
 */
class SignupActivity : Activity() {

    val FIRST_FRAGMENT = "firstFragment"
    val SECOND_FRAGMENT = "secondFragment"

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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
    }
}