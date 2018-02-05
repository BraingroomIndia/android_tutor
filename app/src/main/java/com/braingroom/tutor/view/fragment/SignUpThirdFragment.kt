package com.braingroom.tutor.view.fragment

import android.os.Bundle
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.FRAGMENT_TITLE
import com.braingroom.tutor.view.activity.SignupActivity
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.activity.SignupViewModel

/**
 * Created by godara on 23/01/18.
 */
class SignUpThirdFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String): SignUpThirdFragment {
            val bundle = Bundle()
            bundle.putString(FRAGMENT_TITLE, title)
            val fragment = SignUpThirdFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val vm: ViewModel by lazy {
        activity.getFragmentViewModel(getStringArguments(FRAGMENT_TITLE))
    }

    override val layoutId: Int by lazy {
        R.layout.fragment_signup_3
    }
}
