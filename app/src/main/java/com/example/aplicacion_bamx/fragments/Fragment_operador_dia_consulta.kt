package com.example.aplicacion_bamx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.aplicacion_bamx.Adapter_operador_dia
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Dia

class Fragment_operador_dia_consulta: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.operador_dia_consulta, container, false)

        //val view= inflater.inflate(R.layout.receptor...)
        val lstDia = view.findViewById<ListView>(R.id.lista_recolecciones)
        val datos = listOf(
            Dia(0,"tienda a","calle a numero 1 colonia alfa"),
            Dia(1,"tienda b","calle b numero 2 colonia beta"),
            Dia(2,"tienda c","calle c numero 3 colonia kapa"),
            Dia(3,"tienda d","calle d numero 4 colonia delta"),
            Dia(4,"tienda e","calle e numero 5 colonia epsilon"))

        val adaptador = Adapter_operador_dia(requireActivity(), R.layout.operador_card_consulta_dia, datos)

        lstDia.adapter = adaptador

        return view
    }
}