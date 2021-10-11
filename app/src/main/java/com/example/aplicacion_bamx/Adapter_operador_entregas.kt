package com.example.aplicacion_bamx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Entrega

class Adapter_operador_entregas(val context: Context, val layout: Int, val dataSource : List<Entrega>) : BaseAdapter() {
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

        val bodega_nombre  = view.findViewById(R.id.bodega_nombre_textview) as TextView
        val bodega_direccion = view.findViewById(R.id.bodega_direccion_textview) as TextView
        val fruta = view.findViewById(R.id.fruta_textview) as TextView
        val pan  = view.findViewById(R.id.pan_textview) as TextView
        val abarrote = view.findViewById(R.id.abarrote_textview) as TextView
        val no_comestible = view.findViewById(R.id.no_comestible_textview) as TextView

        //Extraer un elemento del dataSource
        val elemento = getItem(position) as Entrega

        bodega_nombre.text = elemento.bodega_nombre
        bodega_direccion.text = elemento.bodega_direccion
        fruta.text = elemento.fruta.toString()
        pan.text = elemento.pan.toString()
        abarrote.text = elemento.abarrote.toString()
        no_comestible.text = elemento.no_comestible.toString()

        return view
    }
}