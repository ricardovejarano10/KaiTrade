package com.ricardovejarano.kaitradeapp

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ricardovejarano.kaitradeapp.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.toast


class SignUpActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.signupHandler = this

        //Se crea una instancia de autenticacion
        mAuth = FirebaseAuth.getInstance()
    }

    fun signup(){
        val email = editText3.text.toString()
        val pass  = editText4.text.toString()
        callsignup(email,pass)
    }

    fun returntologin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun callsignup(email:String, password:String){

        mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro creado satisfactoriamente
                        toast(R.string.signupOk)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        val user = mAuth?.getCurrentUser()

                    } else {
                       //mensaje de fallo de registro de usuario
                        toast(R.string.signupError)

                        //Se limpia formulario
                        editText3.setText("")
                        editText4.setText("")
                    }
                }
    }
}
