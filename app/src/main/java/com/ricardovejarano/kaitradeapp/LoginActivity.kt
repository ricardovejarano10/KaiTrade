package com.ricardovejarano.kaitradeapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ricardovejarano.kaitradeapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

import org.jetbrains.anko.toast


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    val preferences:SharedPreferences by lazy{
        getSharedPreferences("preferencias", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


       //Se evalua si el usuario ya esta loggeado
       val logged = preferences.getBoolean("logged",false)

        super.onCreate(savedInstanceState)
        if(logged){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        //Se crea una instancia de autenticacion
        mAuth = FirebaseAuth.getInstance()
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.handler = this


    }

    fun login(){
        //

        if(editText.text.isEmpty() || editText2.text.isEmpty()){
            toast(R.string.empty)
            return
        }
        else{

            val email = editText.text.toString()
            val password = editText2.text.toString()
            calllogin(email,password)
        }





    }

    fun signup(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)

    }

    fun calllogin(email:String,password:String){

        mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        preferences.edit().putBoolean("logged",true).apply()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        toast(R.string.loginError)
                        }
                    }
    }

}

