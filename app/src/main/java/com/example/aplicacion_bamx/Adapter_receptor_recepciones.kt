package com.example.aplicacion_bamx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Recepcion

class Adapter_receptor_recepciones(val context: Context, val layout: Int, val dataSource : List<Recepcion>) : BaseAdapter() {
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
        val operador_nombre  = view.findViewById(R.id.operador_nombre_textview) as TextView
        val operador_usuario = view.findViewById(R.id.operador_usuario) as TextView
        val operador_unidad  = view.findViewById(R.id.operador_unidad) as TextView
        val fruta = view.findViewById(R.id.txtFrutaVerduraNota) as TextView
        val pan  = view.findViewById(R.id.txtPanNota) as TextView
        val abarrote = view.findViewById(R.id.txtAbarroteNota) as TextView
        val no_comestible = view.findViewById(R.id.txtNoComestibleNota) as TextView


        //Extraer un elemento del dataSource
        val elemento = getItem(position) as Recepcion

        operador_nombre.setText(operador_nombre.text.toString() + elemento.operador_nombre)
        operador_usuario.setText(operador_usuario.text.toString() +elemento.operador_usuario)
        operador_unidad.setText(operador_unidad.text.toString() + elemento.operador_unidad)
        fruta.setText(fruta.text.toString() + elemento.fruta.toString())
        pan.setText(pan.text.toString() + elemento.pan.toString())
        abarrote.setText(abarrote.text.toString()+elemento.abarrote.toString())
        no_comestible.setText(no_comestible.text.toString()+ elemento.no_comestible.toString())

        return view
    }
}