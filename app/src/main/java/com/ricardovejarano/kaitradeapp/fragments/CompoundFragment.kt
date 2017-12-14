package com.ricardovejarano.kaitradeapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

import com.ricardovejarano.kaitradeapp.R
import com.ricardovejarano.kaitradeapp.util.text
import kotlinx.android.synthetic.main.fragment_compound.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.support.v4.toast


class CompoundFragment : Fragment() {


    var value:Float = 0.0f
    var xCompound:Float = 0.0f

    private val uCompound = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_compound, container, false)
    }

    override fun onResume() {
        super.onResume()


        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid
        val uDatabase = FirebaseDatabase.getInstance().getReference("compound").child(id)


        btnCalculate.setOnClickListener(this::calculate)
        val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, uCompound)
        listViewCompound.adapter = arrayAdapter

        uDatabase.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                val value = p0?.getValue(String::class.java).toString()
                uCompound.add(value)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

                val value = p0?.getValue(String::class.java).toString()
                uCompound.add(value)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                val value = p0?.getValue(String::class.java).toString()
                uCompound.add(value)
                arrayAdapter.notifyDataSetChanged()

            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {


                val value = p0?.getValue(String::class.java).toString()
                uCompound.add(value)
                arrayAdapter.notifyDataSetChanged()

            }

        })


    }

    fun calculate(view: View){

        if(editTextInitial.text.isEmpty() || editTextPercent.text.isEmpty() || editTextNumber.text.isEmpty()){
            toast(R.string.empty)

        }else{


            val number = editTextNumber.text().toInt()

            val initial = editTextInitial.text().toInt().toFloat()
            val percent = editTextPercent.text().toInt().toFloat()
            val percentOperable:Float = (percent/100)
            val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, uCompound)
            listViewCompound.adapter = arrayAdapter
            uCompound.clear()
            arrayAdapter.notifyDataSetChanged()

            val user = FirebaseAuth.getInstance().currentUser
            val id = user?.uid
            val database = FirebaseDatabase.getInstance()
            val myRef1 = database.getReference("compound").child(id)
            myRef1.removeValue()
            for (i in 0 until number) {

                if(i ==0){
                    xCompound =  initial
                }else{
                    xCompound = value
                }

                value = (xCompound * percentOperable) + (xCompound)
                val goSave = (i+1).toString() + "                    " + "%.2f".format(xCompound) + "                     " + "%.2f".format(value)
                uCompound.add(goSave)
                arrayAdapter.notifyDataSetChanged()

                myRef1.push().setValue(goSave)

            }

        }





        }
}
