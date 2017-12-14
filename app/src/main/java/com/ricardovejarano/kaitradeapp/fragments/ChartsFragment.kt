package com.ricardovejarano.kaitradeapp.fragments


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.ricardovejarano.kaitradeapp.R
import kotlinx.android.synthetic.main.fragment_charts.*
import org.jetbrains.anko.support.v4.toast
import android.content.ContentValues.TAG
import com.google.firebase.database.ValueEventListener




class ChartsFragment : Fragment() {
    var winRate:Float = 0f
    var loseRate:Float = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onResume() {
        super.onResume()

        //Se traen los valores de winRate y loseRate de la persona
        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid
          val uDatabaseWin = FirebaseDatabase.getInstance().getReference("trades").child(id).child("win")

        uDatabaseWin.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(Int::class.java)?.toFloat()

                if(dataSnapshot.exists()){
                    winRate = value!!

                }else{
                    winRate = 0f
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })


        val uDatabaseLose = FirebaseDatabase.getInstance().getReference("trades").child(id).child("lose")

        uDatabaseLose.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot?.getValue(Int::class.java)?.toFloat()

                if(dataSnapshot.exists()){
                    loseRate = value!!
                   // toast(loseRate.toString())

                }else{
                    loseRate = 0f
                }
            }
            override fun onCancelled(error: DatabaseError) {
                toast("error")
            }
        })


        btnDraw.setOnClickListener(this::draw)


    }

    fun draw(view: View){

        setPie(winRate,loseRate)


    }

    fun setPie(win:Float, lose:Float){

        //Configuracion del PIE
        fxChart.setUsePercentValues(true)
        fxChart.description.isEnabled = false
        fxChart.setExtraOffsets(5.0f,10.0f,5.0f,5.0f)

        fxChart.dragDecelerationFrictionCoef = 0.99f

        fxChart.isDrawHoleEnabled = true
        fxChart.setHoleColor(Color.WHITE)
        fxChart.transparentCircleRadius = 61f
        val yValues = ArrayList<PieEntry>()


        yValues.add(PieEntry(win, "Ganados"))
        yValues.add(PieEntry(lose, "Perdidos"))

        val dataSet = PieDataSet(yValues, "Trades")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.YELLOW)

        fxChart.data = data



    }
}
