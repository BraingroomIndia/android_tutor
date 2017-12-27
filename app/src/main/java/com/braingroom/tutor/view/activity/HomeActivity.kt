package com.braingroom.tutor.view.activity


import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.braingroom.tutor.R
import com.braingroom.tutor.utils.*
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView?.setNavigationItemSelectedListener(this)
        initNavigationDrawer()
    }

    fun initNavigationDrawer() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.setDrawerListener(toggle)
        toggle.syncState()
        //  hideItem();
    }
    fun logout(){
        vm.preferencesEditor.remove(email)
        vm.preferencesEditor.remove(profilePic)
        vm.preferencesEditor.remove(mobile)
        vm.preferencesEditor.remove(lodgedIn)
        vm.preferencesEditor.remove(name)
        navigator.navigateActivity(LoginActivity::class.java)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.nav_notification){
            navigator.navigateActivity(NotificationActivity::class.java)
        }
        if (item.itemId == R.id.nav_message)
            navigator.navigateActivity(MessageActivity::class.java)
        if(item.itemId == R.id.nav_logout){
            logout()
        }
        if(item.itemId == R.id.nav_faq){
            navigator.navigateActivity(FAQActivity::class.java)
        }
        return true
    }
}