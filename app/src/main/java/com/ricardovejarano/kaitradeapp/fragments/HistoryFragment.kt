package com.ricardovejarano.kaitradeapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.ricardovejarano.kaitradeapp.R
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.support.v4.toast
import com.google.firebase.database.*
import android.widget.ArrayAdapter


class HistoryFragment : Fragment() {

    private val uHistory = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onResume() {
        super.onResume()

        //Listener de botones
        buttonSave.setOnClickListener(this::write)


        //Variables para Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid
        val uDatabase = FirebaseDatabase.getInstance().getReference("history").child(id)



        //Adapter para el arraylist uHistory
        val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, uHistory)
        listViewHistory.adapter = arrayAdapter

        uDatabase.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                val value = p0?.getValue(String::class.java).toString()
                uHistory.add(value)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

                val value = p0?.getValue(String::class.java).toString()
                uHistory.add(value)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                val value = p0?.getValue(String::class.java).toString()
                uHistory.add(value)
                arrayAdapter.notifyDataSetChanged()

            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {


                val value = p0?.getValue(String::class.java).toString()
                uHistory.add(value)
                arrayAdapter.notifyDataSetChanged()

            }

        })

        /*myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {


            }

        })*/

        //region Intentos
        /*
                myRef.addChildEventListener(object : ChildEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                        toast("ALGOOOOOO")
                    }

                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                        toast("ALGOOOOOO")
                    }

                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                        toast("ALGOOOOOO")
                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {
                        toast("ALGOOOOOO")
                    }

                    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                       /* val sizeList =listHistory.size
                        val historyValues = p0?.getValue(String::class.java)

                        listHistory.add("uno")
                        toast(sizeList)

                        adapterHistory.notifyDataSetChanged()*/
                        toast("ALGOOOOOO")
                    }

                })*/


        /*myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)

                toast(value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                toast("error")
            }
        })*/
        //endregion

    } //cierra OnResume

    fun write(view:View){
        if(editTextPair.text.isEmpty() || editTextInvestment.text.isEmpty() || editTextDate.text.isEmpty() || editTextPayout.text.isEmpty()){
            toast(R.string.empty)
        }else{
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                val pair = editTextPair.text.toString()
                val investment = editTextInvestment.text.toString()
                val date = editTextDate.text.toString()
                val payout = editTextPayout.text.toString()
                val id = user.uid
                val database = FirebaseDatabase.getInstance()

                val infoRegister = pair+ "             " + investment + "           " + date + "         " + payout

                val myRef1 = database.getReference("history").child(id)
                myRef1.push().setValue(infoRegister)
                toast(getString(R.string.registred))
            }
        }
    }//cierra funcion write

}// Required empty public constructor
