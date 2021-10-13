package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.aplicacion_bamx.model.Dia

class Activity_operador_dia_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_dia_consulta)

        val lstDia = findViewById<ListView>(R.id.lista_recolecciones)
        val datos = listOf(Dia(0,"tienda a","calle a numero 1 colonia alfa"),
            Dia(1,"tienda b","calle b numero 2 colonia beta"),
            Dia(2,"tienda c","calle c numero 3 colonia kapa"),
            Dia(3,"tienda d","calle d numero 4 colonia delta"),
            Dia(4,"tienda e","calle e numero 5 colonia epsilon"))

        val adaptador = Adapter_operador_dia(this@Activity_operador_dia_consulta, R.layout.operador_card_consulta_dia, datos)

        lstDia.adapter = adaptador

        //pasar datos a la siguiente pantalla
//        lstRecolecciones.setOnItemClickListener{parent, view, position, id ->
//            val intent = Intent(this@Activity_operador_recoleccion_consulta, Activity_operador_recoleccion_consulta:: class.java)
//            intent.putExtra("id", datos[position].donador_id)
//            intent.putExtra("nombre", datos[position].donador_nombre)
//            startActivity(intent)
//        }

    }
}