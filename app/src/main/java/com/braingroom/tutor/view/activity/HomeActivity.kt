package com.braingroom.tutor.view.activity


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.braingroom.tutor.R
import com.braingroom.tutor.databinding.NavHomeHeaderBinding
import com.braingroom.tutor.viewmodel.activity.HomeViewModel

/*
 * Created by godara on 27/09/17.
 */
class HomeActivity : Activity(), NavigationView.OnNavigationItemSelectedListener {


    override val vm: HomeViewModel by lazy {
        HomeViewModel()
    }
    override val layoutId: Int by lazy {
        R.layout.activity_home
    }

    var navigationView: NavigationView? = null
    var drawer: DrawerLayout? = null
    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        initNavigationDrawer()
    }

    override fun onStart() {
        super.onStart()

    }

    fun initNavigationDrawer() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.setDrawerListener(toggle)
        toggle.syncState()


        //  hideItem();


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /*   if(item.itemId==R.id.action_notifications)
               navigator.navigateActivity(FAQActivity::class.java)*/
        return true
    }
}