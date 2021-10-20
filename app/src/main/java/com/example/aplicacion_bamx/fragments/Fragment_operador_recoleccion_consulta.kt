package com.example.aplicacion_bamx.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
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
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.operador_recoleccion_consulta, container, false)

        sharedPreferences = this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id_usuario","-1")

        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = view.findViewById<TextView>(R.id.txtFechaNota)
        txtFecha.text=currentDateAndTime

        val emptystate = view.findViewById<LinearLayout>(R.id.layout_emptystate)
        val err = view.findViewById<LinearLayout>(R.id.layout_error)
        val loader = view.findViewById<ProgressBar>(R.id.cargaRecoleccionConsulta)


        val url = "http://Bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/collections/driver?thisDriver=$idUser"
        val lstRecolecciones = view.findViewById<ListView>(R.id.lista_recepciones)
        val recolecciones = mutableListOf<Recoleccion>()
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    Log.e("VOLLEYRESPONSE", jsonArray.toString())
                    if(jsonArray.isNull(0)){
                        emptystate.visibility=View.VISIBLE
                        loader.visibility = View.GONE
                        err.visibility = View.GONE
                        lstRecolecciones.visibility = View.VISIBLE
                    }
                    else {
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val idCollection = jsonObject.getInt("idCollection")
                            val idDonor = jsonObject.getInt("idDonor")
                            val nombre = jsonObject.getString("nombre")
                            val callen = jsonObject.getString("calle")
                            val numExterior = jsonObject.getString("numExterior")
                            val colonia = jsonObject.getString("colonia")
                            val municipio = jsonObject.getString("municipio")
                            val cp = jsonObject.getString("cp")
                            val estado = jsonObject.getString("estado")
                            val direccion =
                                callen + ", " + numExterior + ", " + colonia + ", " + municipio + ", " + estado + ", " + cp
                            recolecciones.add(
                                i,
                                Recoleccion(idCollection, idDonor, nombre, direccion)
                            )


                            val adaptador = Adapter_operador_recolecciones(
                                requireActivity(),
                                R.layout.operador_card_consulta_recoleccion,
                                recolecciones
                            )
                            lstRecolecciones.adapter = adaptador

                            lstRecolecciones.setOnItemClickListener { parent, view, position, id ->
                                val intent = Intent(
                                    requireActivity(),
                                    Activity_operador_recoleccion_formulario::class.java
                                )
                                intent.putExtra("Id", recolecciones[position].donador_id)
                                intent.putExtra("Nombre", recolecciones[position].donador_nombre)
                                intent.putExtra(
                                    "IdCollection",
                                    recolecciones[position].recoleccion_id
                                )
                                startActivity(intent)
                            }
                        }
                        loader.visibility = View.GONE
                        lstRecolecciones.visibility = View.VISIBLE
                        err.visibility = View.GONE
                        emptystate.visibility = View.GONE
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    err.visibility = View.VISIBLE
                    loader.visibility = View.GONE
                    lstRecolecciones.visibility = View.GONE
                    emptystate.visibility = View.GONE
                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)


        return view
    }
}