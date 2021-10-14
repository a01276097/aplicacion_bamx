package com.example.aplicacion_bamx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacion_bamx.Adapter_operador_entregas
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Entrega
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class Fragment_operador_entrega_consulta: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.operador_entrega_consulta, container, false)

        val emptystateEntrega = view.findViewById<LinearLayout>(R.id.layout_empystate_entrega)


        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = view.findViewById<TextView>(R.id.txtFechaEntrega)
        txtFecha.text=currentDateAndTime

        val url = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/drivers/assignedWarehouses/:idDriver"
        val lstEntregas = view.findViewById<ListView>(R.id.lista_entrega)
        val entregas = mutableListOf<Entrega>()
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    lstEntregas.visibility = View.VISIBLE
                    emptystateEntrega.visibility= View.GONE
                    val jsonArray = response.getJSONArray("listaEntregas")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nombreBodega = jsonObject.getString("nombre")
                        val callen = jsonObject.getString("calle")
                        val numExterior = jsonObject.getString("numExterior")
                        val colonia= jsonObject.getString("colonia")
                        val municipio = jsonObject.getString("municipio")
                        val cp = jsonObject.getString("cp")
                        val estado = jsonObject.getString("estado")
                        val pan = jsonObject.getInt("pan")
                        val fruta = jsonObject.getInt("fruta")
                        val abarrote = jsonObject.getInt("abarrote")
                        val noComestible = jsonObject.getInt("nocomestible")
                        val direccion = callen + ", " + numExterior + ", " + colonia + ", " + municipio + ", " + estado + ", " + cp
                        entregas.add(i, Entrega(nombreBodega, direccion,pan, fruta, abarrote, noComestible ))

                        val adaptador = Adapter_operador_entregas(requireActivity(), R.layout.operador_card_consulta_entrega, entregas)
                        lstEntregas.adapter = adaptador

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)

        return view
    }
}