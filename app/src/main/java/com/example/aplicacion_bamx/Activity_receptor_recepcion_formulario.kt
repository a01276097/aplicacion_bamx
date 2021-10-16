package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Activity_receptor_recepcion_formulario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_formulario)

        val nombreOperador = intent.getStringExtra("nombre")
        val unidad = intent.getStringExtra("unidad")
        val usuarioOperador = intent.getStringExtra("usuario")
        val cantPan = intent.getStringExtra("pan")
        val cantAbarrote = intent.getStringExtra("abarrote")
        val cantFrutaVerdura = intent.getStringExtra("fruta")
        val cantNoComestible = intent.getStringExtra("noComestible")


        val tNombreOperador = findViewById<TextView>(R.id.titulos_2)
        val tUnidad = findViewById<TextView>(R.id.txtUnidadOperador)
        val tUsuarioOperador = findViewById<TextView>(R.id.txtUsuarioOperador)
        val tPan = findViewById<TextView>(R.id.txtPanRecepcion)
        val tFrutaVerdura = findViewById<TextView>(R.id.txtFrutaVerduraRecepcion)
        val tNoComestible = findViewById<TextView>(R.id.txtNoComestibleRecepcion)
        val tAbarrote = findViewById<TextView>(R.id.txtAbarroteRecepcion)


        tNombreOperador.setText(nombreOperador)
        tUnidad.setText(unidad)
        tUsuarioOperador.setText(usuarioOperador)

        tPan.setText(tPan.text.toString() + " ${cantPan}")
        tFrutaVerdura.setText(tFrutaVerdura.text.toString() + " ${cantFrutaVerdura}")
        tNoComestible.setText(tNoComestible.text.toString() + " ${cantNoComestible}")
        tAbarrote.setText(tAbarrote.text.toString() + " ${cantAbarrote}")




    }
}