package com.ricardovejarano.kaitradeapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ricardovejarano.kaitradeapp.EditProfileActivity

import com.ricardovejarano.kaitradeapp.R
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class UserProfileFragment : Fragment() {

    private val uProfile = ArrayList<String>()
    var userName:String = ""
    var userMarket:String = ""
    var userAge:Int = 0
    var userLastName:String = ""
    var userBroker:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user_profile, container, false)


    }

    override fun onResume() {
        super.onResume()
        btn_editProfile.setOnClickListener(this::editProfile)

        //Variables para Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid
        val email = user?.email
        val uDatabase = FirebaseDatabase.getInstance().getReference("profile").child(id).child("full")
        val uDatabaseMarket = FirebaseDatabase.getInstance().getReference("profile").child(id).child("market")
        val uDatabaseAge = FirebaseDatabase.getInstance().getReference("profile").child(id).child("age")
        val uDatabaseBroker = FirebaseDatabase.getInstance().getReference("profile").child(id).child("broker")

        textViewEmail.setText(email)

        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               val value = dataSnapshot?.getValue(String::class.java).toString()

                if(dataSnapshot.exists()){
                    textViewName.setText(value)

                }else{
                    textViewName.setText(R.string.edit_your_profile)
                }
            }
            override fun onCancelled(error: DatabaseError) {
               toast("error")
            }
        })


        uDatabaseMarket.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(String::class.java).toString()

                if(dataSnapshot.exists()){
                    textViewMarket.setText(value)

                }else{
                    textViewMarket.setText(R.string.edit)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })

        uDatabaseAge.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(String::class.java).toString()

                if(dataSnapshot.exists()){
                    edad_usuario.setText(value)

                }else{
                    edad_usuario.setText(R.string.edit)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })

        uDatabaseBroker.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(String::class.java).toString()

                if(dataSnapshot.exists()){
                    textEditBroker.setText(value)

                }else{
                    textEditBroker.setText(R.string.edit)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })






    }



    fun editProfile(view: View){
       // val fin = activity?.supportFragmentManager
       startActivity<EditProfileActivity>()

    }

}// Required empty public constructor
