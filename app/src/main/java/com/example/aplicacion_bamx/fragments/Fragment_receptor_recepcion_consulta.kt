package com.example.aplicacion_bamx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.aplicacion_bamx.Adapter_receptor_recepciones
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Recepcion

class Fragment_receptor_recepcion_consulta: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.receptor_recepcion_consulta, container, false)

        //val view= inflater.inflate(R.layout.receptor...)
        val lstRecepciones = view.findViewById<ListView>(R.id.lista_recolecciones)
        val datos = listOf(
            Recepcion("nombre1","id_1","un_1", 1, 1, 1, 1),
            Recepcion("nombre2","id_2","un_2", 2, 2, 2, 2),
            Recepcion("nombre3","id_3","un_3", 3, 3, 3, 3),
            Recepcion("nombre4","id_4","un_4", 4, 4, 4, 4),
            Recepcion("nombre5","id_5","un_5", 5, 5, 5, 5))

        val adaptador = Adapter_receptor_recepciones(requireActivity(), R.layout.receptor_card_recepcion, datos)

        lstRecepciones.adapter = adaptador

        return view
    }
}