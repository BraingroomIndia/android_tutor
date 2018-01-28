package com.braingroom.tutor.view.activity

import android.util.Log
import com.braingroom.tutor.R
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
    val THIRD_FRAGMENT = "thirdFragment"
    val FOURTH_FRAGEMNT = "fourthFragment"

    override val layoutId: Int = R.layout.activity_signup;


    override val vm: SignupViewModel by lazy {
        SignupViewModel(helperFactory, object : UiHelper {


            override fun firstFragment() {
                navigator.openFragment(SignUpFirstFragment.newInstance(FIRST_FRAGMENT))
                fragmentManager?.
                        beginTransaction()?.
//                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                        add(R.id.fragment_container, SignUpFirstFragment.newInstance(FIRST_FRAGMENT))?.
                        commit()
                popBackStack(SECOND_FRAGMENT);
                popBackStack(THIRD_FRAGMENT)
                popBackStack(FOURTH_FRAGEMNT)
            }

            override fun secondFragment() {
                fragmentManager?.
                        beginTransaction()?.
//                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                        replace(R.id.fragment_container, SignUpSecondFragment.newInstance(SECOND_FRAGMENT))?.
                        addToBackStack(FIRST_FRAGMENT)?.
                        commit()
                popBackStack(THIRD_FRAGMENT)
                popBackStack(FOURTH_FRAGEMNT)
            }

            override fun thirdFragment() {
                fragmentManager?.
                        beginTransaction()?.
//                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                        replace(R.id.fragment_container, SignUpThirdFragment.newInstance(THIRD_FRAGMENT))?.
                        addToBackStack(SECOND_FRAGMENT)?.
                        commit()
                popBackStack(FOURTH_FRAGEMNT)
            }

            override fun toForth() {
                navigator.openFragment(SignUpFourthFragment.newInstance(FOURTH_FRAGEMNT))
                fragmentManager?.
                        beginTransaction()?.
//                setCustomAnimations(R.animator.bottom_in, R.animator.top_out)?.
                        replace(R.id.fragment_container, SignUpFourthFragment.newInstance(FOURTH_FRAGEMNT))?.
                        addToBackStack(THIRD_FRAGMENT)?.
                        commit()
            }

            override fun signUp() {
                Log.d("why", "why")
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
            Category -> vm.categoryVm
            Community -> vm.communityVm
            FIRST_FRAGMENT -> vm
            SECOND_FRAGMENT -> vm
            THIRD_FRAGMENT -> vm
            FOURTH_FRAGEMNT -> vm
            else -> throw NoSuchFieldError()
        }

    }

    interface UiHelper {
        fun firstFragment()
        fun secondFragment()
        fun thirdFragment()
        fun toForth()
        fun signUp()
    }
}