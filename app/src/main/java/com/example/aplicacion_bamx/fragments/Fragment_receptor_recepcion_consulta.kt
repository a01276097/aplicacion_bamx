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
import com.example.aplicacion_bamx.Activity_receptor_recepcion_formulario
import com.example.aplicacion_bamx.Adapter_receptor_recepciones
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.model.Recepcion
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class Fragment_receptor_recepcion_consulta: Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.receptor_recepcion_consulta, container, false)

        sharedPreferences = this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id_usuario","-1")

        val emptystateRecepcion = view.findViewById<LinearLayout>(R.id.layout_emptystate)
        val err = view.findViewById<LinearLayout>(R.id.layout_error)
        val loader = view.findViewById<ProgressBar>(R.id.cargaRecepcion)

        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = view.findViewById<TextView>(R.id.txtFechaRecepcion)
        txtFecha.text=currentDateAndTime

        val url = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/assigneddeliveries/${idUser}"
        val lstRecepciones = view.findViewById<ListView>(R.id.lista_recepciones)
        val recepciones = mutableListOf<Recepcion>()
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val jsonArray = response.getJSONArray("data")
                    if(jsonArray.isNull(0)){
                        lstRecepciones.visibility = View.GONE
                        emptystateRecepcion.visibility= View.VISIBLE
                        loader.visibility = View.GONE
                        err.visibility = View.GONE
                    }
                    else {
                        Log.e("Hola", jsonArray.toString())
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val idAsignacion = jsonObject.getInt("idWarehousesAssignation")
                            val nombre = jsonObject.getString("operador")
                            val apellidoP = jsonObject.getString("apellidoP")
                            val apellidoM = jsonObject.getString("apellidoM")
                            val nombreUsuario = jsonObject.getString("nombreUsuario")
                            val modeloUnidad = jsonObject.getString("modelo")
                            val pan = jsonObject.getInt("pan")
                            val fruta = jsonObject.getInt("fruta")
                            val abarrote = jsonObject.getInt("abarrote")
                            val noComestible = jsonObject.getInt("noComestible")
                            val nombreOperador = "$nombre $apellidoP $apellidoM"
                            recepciones.add(
                                i,
                                Recepcion(
                                    idAsignacion,
                                    nombreOperador,
                                    nombreUsuario,
                                    modeloUnidad,
                                    pan,
                                    fruta,
                                    abarrote,
                                    noComestible
                                )
                            )

                            val adaptador = Adapter_receptor_recepciones(
                                requireActivity(),
                                R.layout.receptor_card_recepcion,
                                recepciones
                            )
                            lstRecepciones.adapter = adaptador

                            lstRecepciones.setOnItemClickListener { parent, view, position, id ->
                                val intent = Intent(
                                    requireActivity(),
                                    Activity_receptor_recepcion_formulario::class.java
                                )
                                intent.putExtra("idAsignacionBodega", recepciones[position].id_asignacion)
                                intent.putExtra("nombre", recepciones[position].operador_nombre)
                                intent.putExtra("unidad", recepciones[position].operador_unidad)
                                intent.putExtra("usuario", recepciones[position].operador_usuario)
                                intent.putExtra("pan", recepciones[position].pan)
                                intent.putExtra("abarrote", recepciones[position].abarrote)
                                intent.putExtra("fruta", recepciones[position].fruta)
                                intent.putExtra("noComestible", recepciones[position].no_comestible)
                                startActivity(intent)
                            }
                        }
                        lstRecepciones.visibility = View.VISIBLE
                        emptystateRecepcion.visibility= View.GONE
                        loader.visibility = View.GONE
                        err.visibility = View.GONE

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    lstRecepciones.visibility = View.GONE
                    emptystateRecepcion.visibility= View.GONE
                    loader.visibility = View.GONE
                    err.visibility = View.VISIBLE

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)

        return view
    }
}