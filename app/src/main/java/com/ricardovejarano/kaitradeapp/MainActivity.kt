package com.ricardovejarano.kaitradeapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.ricardovejarano.kaitradeapp.fragments.ChartsFragment
import com.ricardovejarano.kaitradeapp.fragments.CompoundFragment
import com.ricardovejarano.kaitradeapp.fragments.HistoryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_compound -> {
                transaction.replace(R.id.container, CompoundFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                transaction.replace(R.id.container, HistoryFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_charts -> {
                transaction.replace(R.id.container, ChartsFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, CompoundFragment()).commit()
    }
}
