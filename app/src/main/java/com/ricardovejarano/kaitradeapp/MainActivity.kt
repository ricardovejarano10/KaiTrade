package com.ricardovejarano.kaitradeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.ricardovejarano.kaitradeapp.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), DrawerLayout.DrawerListener {


    val toggle: ActionBarDrawerToggle by lazy{
        ActionBarDrawerToggle(this, drawer, R.string.open_menu, R.string.close_menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content, CompoundFragment()).commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val ab = supportActionBar
        ab?.setTitle(R.string.title_compound).toString()
        drawer.addDrawerListener(this)
        nav.setNavigationItemSelectedListener {setContent(it)}
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    //region Drawer Layout - Navigation
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDrawerStateChanged(newState: Int) {
        toggle.onDrawerStateChanged(newState)
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        toggle.onDrawerSlide(drawerView, slideOffset)
    }

    override fun onDrawerClosed(drawerView: View) {
        toggle.onDrawerClosed(drawerView)
    }

    override fun onDrawerOpened(drawerView: View) {
        toggle.onDrawerOpened(drawerView)
    }
    //endregion

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_compound -> {
                drawer.closeDrawers()
                transaction.replace(R.id.content, CompoundFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.title_compound).toString()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                drawer.closeDrawers()
                transaction.replace(R.id.content, HistoryFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.title_history).toString()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_charts -> {
                drawer.closeDrawers()
                transaction.replace(R.id.content, ChartsFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.title_charts).toString()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun setContent(item:MenuItem):Boolean{
        drawer.closeDrawers()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val intent = Intent(this, LoginActivity::class.java)
        when(item?.itemId){

            R.id.nav_profile -> {
                transaction.replace(R.id.content, UserProfileFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.profile).toString()
            }

            R.id.nav_forex ->{
            transaction.replace(R.id.content, ForexInfoFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.forex).toString()
            }

            R.id.nav_binaryOptions ->{
                transaction.replace(R.id.content, BinaryOptionsInfoFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.binary_options).toString()
            }
            R.id.nav_futures ->{
                transaction.replace(R.id.content, FuturesInfoFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.futures).toString()
            }
            R.id.nav_actions -> {
                transaction.replace(R.id.content, ActionsInfoFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.futures).toString()
            }
            R.id.nav_configuration -> {
                transaction.replace(R.id.content, ConfigurationFragment()).commit()
                val ab = supportActionBar
                ab?.setTitle(R.string.about).toString()
            }
            R.id.nav_logout -> {
                getSharedPreferences("preferencias", Context.MODE_PRIVATE).edit().putBoolean("logged",false).apply()
                startActivity(intent)
            }

        }

        return true
    }

}
