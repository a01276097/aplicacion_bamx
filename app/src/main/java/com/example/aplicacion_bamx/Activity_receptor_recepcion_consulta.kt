package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.aplicacion_bamx.model.Recepcion

class Activity_receptor_recepcion_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_consulta)

        val lstRecepciones = findViewById<ListView>(R.id.lista_recolecciones)
        val datos = listOf( Recepcion("nombre1","id_1","un_1", 1, 1, 1, 1),
            Recepcion("nombre2","id_2","un_2", 2, 2, 2, 2),
            Recepcion("nombre3","id_3","un_3", 3, 3, 3, 3),
            Recepcion("nombre4","id_4","un_4", 4, 4, 4, 4),
            Recepcion("nombre5","id_5","un_5", 5, 5, 5, 5))

        val adaptador = Adapter_receptor_recepciones(this@Activity_receptor_recepcion_consulta, R.layout.receptor_card_recepcion, datos)

        lstRecepciones.adapter = adaptador

        //pasar datos a la siguiente pantalla
//        lstRecolecciones.setOnItemClickListener{parent, view, position, id ->
//            val intent = Intent(this@Activity_operador_recoleccion_consulta, Activity_operador_recoleccion_consulta:: class.java)
//            intent.putExtra("id", datos[position].donador_id)
//            intent.putExtra("nombre", datos[position].donador_nombre)
//            startActivity(intent)
//        }

    }
}