package com.example.aplicacion_bamx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Dia

class Adapter_operador_dia(val context: Context, val layout: Int, val dataSource : List<Dia>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(layout, parent, false)

        //Cargar datos del data source en el elemento cargado

        val donador_nombre  = view.findViewById(R.id.donador_nombre_textview) as TextView
        val donador_direccion = view.findViewById(R.id.donador_direccion_textview) as TextView

        //Extraer un elemento del dataSource
        val elemento = getItem(position) as Dia

        donador_nombre.text = elemento.donador_nombre
        donador_direccion.text = elemento.donador_direccion

        return view
    }
}