package com.ricardovejarano.kaitradeapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ricardovejarano.kaitradeapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    val preferences:SharedPreferences by lazy{
        getSharedPreferences("preferencias", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Se evalua si el usuario ya esta loggeado
       val logged = preferences.getBoolean("logged",false)
        if(logged){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.handler = this

    }

    fun login(){
        preferences.edit().putBoolean("logged",true).apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signup(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)

    }
}
