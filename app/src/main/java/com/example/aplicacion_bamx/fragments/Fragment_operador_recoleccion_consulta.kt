package com.example.aplicacion_bamx.fragments

import android.content.Intent
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
import com.example.aplicacion_bamx.Activity_operador_recoleccion_formulario
import com.example.aplicacion_bamx.Adapter_operador_recolecciones
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Recoleccion
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class   Fragment_operador_recoleccion_consulta: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.operador_recoleccion_consulta, container, false)

        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = view.findViewById<TextView>(R.id.txtFechaNota)
        txtFecha.text=currentDateAndTime

        val emptystate = view.findViewById<LinearLayout>(R.id.layout_empystate_entrega)


        val url = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/collections/driver?thisDriver=7"
        val lstRecolecciones = view.findViewById<ListView>(R.id.lista_recolecciones)
        val recolecciones = mutableListOf<Recoleccion>()
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    lstRecolecciones.visibility = View.VISIBLE
                    emptystate.visibility=View.GONE
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val idDonor = jsonObject.getInt("idDonor")
                        val nombre = jsonObject.getString("nombre")
                        val callen = jsonObject.getString("calle")
                        val numExterior = jsonObject.getString("numExterior")
                        val colonia= jsonObject.getString("colonia")
                        val municipio = jsonObject.getString("municipio")
                        val cp = jsonObject.getString("cp")
                        val estado = jsonObject.getString("estado")
                        val direccion = callen + ", " + numExterior + ", " + colonia + ", " + municipio + ", " + estado + ", " + cp
                        recolecciones.add(i, Recoleccion(idDonor, nombre, direccion))


                        val adaptador = Adapter_operador_recolecciones(requireActivity(), R.layout.operador_card_consulta_recoleccion, recolecciones)
                        lstRecolecciones.adapter = adaptador



                        lstRecolecciones.setOnItemClickListener{parent, view, position, id ->
                            val intent = Intent(requireActivity(), Activity_operador_recoleccion_formulario:: class.java)
                            intent.putExtra("Id", recolecciones[position].donador_id)
                            intent.putExtra("Nombre", recolecciones[position].donador_nombre)
                            startActivity(intent)
                        }
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