package com.example.aplicacion_bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.aplicacion_bamx.model.Entrega

class Activity_operador_entrega_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_entrega_consulta)

        val lstEntregas = findViewById<ListView>(R.id.lista_entrega)
        val datos = listOf(Entrega("Bodega 1", "calle a numero 1 colonia alfa", 1, 1, 1, 1),
            Entrega("Bodega 2", "calle b numero 2 colonia beta", 2, 2, 2, 2),
            Entrega("Bodega 3", "calle c numero 3 colonia kapa", 3, 3, 3, 3),
            Entrega("Bodega 4", "calle d numero 4 colonia delta", 4, 4, 4, 4),
            Entrega("Bodega 5", "calle e numero 5 colonia epsilon", 5, 5, 5, 5))

        val adaptador = Adapter_operador_entregas(this@Activity_operador_entrega_consulta, R.layout.operador_card_consulta_entrega, datos)

        lstEntregas.adapter = adaptador

        //pasar datos a la siguiente pantalla
//        lstRecolecciones.setOnItemClickListener{parent, view, position, id ->
//            val intent = Intent(this@Activity_operador_recoleccion_consulta, Activity_operador_recoleccion_consulta:: class.java)
//            intent.putExtra("id", datos[position].donador_id)
//            intent.putExtra("nombre", datos[position].donador_nombre)
//            startActivity(intent)
//        }

    }
}