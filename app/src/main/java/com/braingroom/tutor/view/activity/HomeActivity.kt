package com.braingroom.tutor.view.activity


import android.support.design.internal.NavigationMenu
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.activity.HomeViewModel

/*
 * Created by godara on 27/09/17.
 */
class HomeActivity : Activity() , NavigationView.OnNavigationItemSelectedListener{


    override val vm: HomeViewModel by lazy {
        HomeViewModel()
    }
    override val layoutId: Int by lazy {
        R.layout.activity_home
    }

    override fun onStart() {
        super.onStart()
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}