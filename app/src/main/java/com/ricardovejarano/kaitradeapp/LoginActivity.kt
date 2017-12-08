package com.ricardovejarano.kaitradeapp

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ricardovejarano.kaitradeapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.handler = this

    }

    fun login(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signup(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)

    }
}
