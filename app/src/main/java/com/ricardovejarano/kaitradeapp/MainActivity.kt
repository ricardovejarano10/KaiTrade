package com.ricardovejarano.kaitradeapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.ricardovejarano.kaitradeapp.fragments.ChartsFragment
import com.ricardovejarano.kaitradeapp.fragments.CompoundFragment
import com.ricardovejarano.kaitradeapp.fragments.HistoryFragment
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
        drawer.addDrawerListener(this)
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

    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
        toggle.onDrawerSlide(drawerView, slideOffset)
    }

    override fun onDrawerClosed(drawerView: View?) {
        toggle.onDrawerClosed(drawerView)
    }

    override fun onDrawerOpened(drawerView: View?) {
        toggle.onDrawerOpened(drawerView)
    }
    //endregion

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_compound -> {
                transaction.replace(R.id.content, CompoundFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                transaction.replace(R.id.content, HistoryFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_charts -> {
                transaction.replace(R.id.content, ChartsFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
