package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.aplicacion_bamx.model.Recoleccion

class Activity_operador_recoleccion_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_recoleccion_consulta)

        val lstRecolecciones = findViewById<ListView>(R.id.lista_recolecciones)
        val datos = listOf(Recoleccion(0,"tienda a","calle a numero 1 colonia alfa"),
            Recoleccion(1,"tienda b","calle b numero 2 colonia beta"),
            Recoleccion(2,"tienda c","calle c numero 3 colonia kapa"),
            Recoleccion(3,"tienda d","calle d numero 4 colonia delta"),
            Recoleccion(4,"tienda e","calle e numero 5 colonia epsilon"))

        val adaptador = Adapter_operador_recolecciones(this@Activity_operador_recoleccion_consulta, R.layout.operador_card_consulta_recoleccion, datos)

        lstRecolecciones.adapter = adaptador

        //pasar datos a la siguiente pantalla
//        lstRecolecciones.setOnItemClickListener{parent, view, position, id ->
//            val intent = Intent(this@Activity_operador_recoleccion_consulta, Activity_operador_recoleccion_consulta:: class.java)
//            intent.putExtra("id", datos[position].donador_id)
//            intent.putExtra("nombre", datos[position].donador_nombre)
//            startActivity(intent)
//        }

    }
}