package com.example.aplicacion_bamx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.aplicacion_bamx.Adapter_operador_entregas
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Entrega

class Fragment_operador_entrega_consulta: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.operador_entrega_consulta, container, false)

        //val view= inflater.inflate(R.layout.receptor...)
        val lstDia = view.findViewById<ListView>(R.id.lista_entrega)
        val datos = listOf(
            Entrega("Bodega 1", "calle a numero 1 colonia alfa", 1, 1, 1, 1),
            Entrega("Bodega 2", "calle b numero 2 colonia beta", 2, 2, 2, 2),
            Entrega("Bodega 3", "calle c numero 3 colonia kapa", 3, 3, 3, 3),
            Entrega("Bodega 4", "calle d numero 4 colonia delta", 4, 4, 4, 4),
            Entrega("Bodega 5", "calle e numero 5 colonia epsilon", 5, 5, 5, 5))

        val adaptador = Adapter_operador_entregas(requireActivity(), R.layout.operador_card_consulta_entrega, datos)

        lstDia.adapter = adaptador

        return view
    }
}