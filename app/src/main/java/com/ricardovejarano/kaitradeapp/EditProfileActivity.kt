package com.ricardovejarano.kaitradeapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import android.content.Intent
import org.jetbrains.anko.startActivityForResult
import android.provider.MediaStore
import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage


class EditProfileActivity : AppCompatActivity() {

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 17

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val ab = supportActionBar
        ab?.setTitle(R.string.edit_profile_user).toString()
        btn_edit.setOnClickListener(this::editProfile)
        btn_load.setOnClickListener(this::uploadImage)

        //primero se trae la informacion del usuario si es que existe
        val user = FirebaseAuth.getInstance().currentUser
        val storageRef = FirebaseStorage.getInstance().getReference()
        val id = user?.uid
        val uDatabase = FirebaseDatabase.getInstance().getReference("profile").child(id).child("name")
        val uDatabaseLastName = FirebaseDatabase.getInstance().getReference("profile").child(id).child("lastName")
        val uDatabaseMarket = FirebaseDatabase.getInstance().getReference("profile").child(id).child("market")
        val uDatabaseAge = FirebaseDatabase.getInstance().getReference("profile").child(id).child("age")
        val uDatabaseBroker = FirebaseDatabase.getInstance().getReference("profile").child(id).child("broker")



        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(String::class.java).toString()

                if(dataSnapshot.exists()){
                    field_name.setText(value)

                }else{
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })

        uDatabaseLastName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(String::class.java).toString()

                if(dataSnapshot.exists()){
                    field_last_name.setText(value)

                }else{
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
                    field_Market.setText(value)

                }else{
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
                    field_age.setText(value)

                }else{
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
                    field_broker.setText(value)

                }else{

                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })
    }

    fun editProfile(view: View){


        //se verifica que ningún campo este sin llenar
        if(field_name.text.isEmpty() || field_last_name.text.isEmpty() || field_age.text.isEmpty() || field_broker.text.isEmpty() || field_Market.text.isEmpty()){
            toast(R.string.empty)
        }else{
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                val userName = field_name.text.toString()
                val userLastName = field_last_name.text.toString()
                val userAge = field_age.text.toString()
                val userBroker = field_broker.text.toString()
                val userMarket = field_Market.text.toString()
                val id = user.uid
                val database = FirebaseDatabase.getInstance()
                val myRef1 = database.getReference("profile").child(id).child("name")
                myRef1.setValue(userName)
                val myRef2 = database.getReference("profile").child(id).child("lastName")
                myRef2.setValue(userLastName)
                val myRef3 = database.getReference("profile").child(id).child("age")
                myRef3.setValue(userAge)
                val myRef4 = database.getReference("profile").child(id).child("broker")
                myRef4.setValue(userBroker)
                val myRef5 = database.getReference("profile").child(id).child("market")
                myRef5.setValue(userMarket)
                val myRef6 = database.getReference("profile").child(id).child("full")
                myRef6.setValue(userName + " " + userLastName)
                toast(getString(R.string.edited_profile))
                startActivity<MainActivity>()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                // API 5+ solution
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun uploadImage(view: View){

        //Primero se selecciona la imagen
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       // if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            filePath = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                userImage.setImageBitmap(bitmap)
                toast("Imagen Puesta")
            }
            catch(e: Exception){
                e.printStackTrace()
            }

       // }

    }

}
