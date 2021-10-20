package com.example.aplicacion_bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Activity_receptor_recepcion_formulario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_formulario)


        val btnModificar = findViewById<Button>(R.id.boton_modificar_form)
        val btnConfirmar = findViewById<Button>(R.id.boton_confirmar_form)


        val nombreOperador = intent.getStringExtra("nombre")
        val unidad = intent.getStringExtra("unidad")
        val usuarioOperador = intent.getStringExtra("usuario")
        val cantPan = intent.getIntExtra("pan",0)
        val cantAbarrote = intent.getIntExtra("abarrote",0)
        val cantFrutaVerdura = intent.getIntExtra("fruta",0)
        val cantNoComestible = intent.getIntExtra("noComestible",0)


        val tNombreOperador = findViewById<TextView>(R.id.titulos_2)
        val tUnidad = findViewById<TextView>(R.id.txtUnidadOperador)
        val tUsuarioOperador = findViewById<TextView>(R.id.txtUsuarioOperador)
        val tPan = findViewById<TextView>(R.id.txtPanRecepcion)
        val tFrutaVerdura = findViewById<TextView>(R.id.txtFrutaVerduraRecepcion)
        val tNoComestible = findViewById<TextView>(R.id.txtNoComestibleRecepcion)
        val tAbarrote = findViewById<TextView>(R.id.txtAbarroteRecepcion)


        tNombreOperador.setText(nombreOperador)
        tUnidad.setText("Unidad: ${unidad}")
        tUsuarioOperador.setText("Operador: $usuarioOperador")

        tPan.setText(tPan.text.toString() + " ${cantPan} kg")
        tFrutaVerdura.setText(tFrutaVerdura.text.toString() + " ${cantFrutaVerdura} kg")
        tNoComestible.setText(tNoComestible.text.toString() + " ${cantNoComestible} kg")
        tAbarrote.setText(tAbarrote.text.toString() + " ${cantAbarrote} kg")

        btnModificar.setOnClickListener {
            val intent = Intent(this@Activity_receptor_recepcion_formulario, Activity_receptor_recepcion_modificar:: class.java)
            intent.putExtra("pan", cantPan)
            intent.putExtra("abarrote", cantAbarrote)
            intent.putExtra("fruta", cantFrutaVerdura)
            intent.putExtra("noComestible", cantNoComestible)
            startActivity(intent)
        }


    }
}