package com.braingroom.tutor.view.fragment

import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FRAGMENT_TITLE
import com.braingroom.tutor.viewmodel.ViewModel

/**
 * Created by ashketchup on 21/12/17.
 */
class SignUpFourthFragment :BaseFragment(){
    companion object {
        fun newInstance(title:String): SignUpFourthFragment {
            val bundle = Bundle()
            bundle.putString(FRAGMENT_TITLE, title)
            val fragment = SignUpFourthFragment()
            fragment.arguments = bundle
            return fragment

        }
    }
    override val vm: ViewModel by lazy {
        activity.getFragmentViewModel(getStringArguments(FRAGMENT_TITLE))
    }

    override val layoutId: Int by lazy {
        R.layout.fragment_signup_4
    }
}
