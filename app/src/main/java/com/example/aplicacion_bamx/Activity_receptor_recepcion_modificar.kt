package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.aplicacion_bamx.R

class Activity_receptor_recepcion_modificar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_modificar)

        val tFrutaVerdura1 = findViewById<TextView>(R.id.campo_fruta_verdura)
        val tPan1 = findViewById<TextView>(R.id.campo_pan)
        val tAbarrote1 = findViewById<TextView>(R.id.campo_abarrote)
        val tNoComestible1 = findViewById<TextView>(R.id.campo_no_comestible)

        val pan = intent.getIntExtra("pan",0)
        val abarrote = intent.getIntExtra("abarrote",0)
        val frutaVerdura = intent.getIntExtra("fruta",0)
        val noComestible = intent.getIntExtra("noComestible",0)

        tFrutaVerdura1.setText(frutaVerdura)
        tPan1.setText(pan)
        tAbarrote1.setText(abarrote)
        tNoComestible1.setText(noComestible)

    }
}