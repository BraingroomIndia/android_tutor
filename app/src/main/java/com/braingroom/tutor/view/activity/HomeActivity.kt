package com.braingroom.tutor.view.activity


import android.support.design.internal.NavigationMenu
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
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

    fun initNavigationDrawer() {
        toolbar= findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView:NavigationView = findViewById(R.id.nav_view)
        navigationView.menu.clear()
         if (vm.loggedIn)
            navigationView.inflateMenu(R.menu.home)
        else
            navigationView.inflateMenu(R.menu.home)
        navigationView.setNavigationItemSelectedListener(this)
        //  hideItem();
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_notifications)
            navigator.navigateActivity(FAQActivity::class.java)
        return true
    }
}