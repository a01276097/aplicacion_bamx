package com.example.aplicacion_bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacion_bamx.model.Recepcion
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class Activity_receptor_recepcion_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_consulta)

        val emptystateRecepcion = findViewById<LinearLayout>(R.id.layout_emptystate)

        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = findViewById<TextView>(R.id.txtFechaRecepcion)
        txtFecha.text=currentDateAndTime

        val url = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/assigneddeliveries/14"
        val lstRecepciones = /*view.*/findViewById<ListView>(R.id.lista_recepciones)
        val recepciones = mutableListOf<Recepcion>()
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    lstRecepciones.visibility = View.VISIBLE
                    emptystateRecepcion.visibility= View.GONE
                    val jsonArray = response.getJSONArray("data")
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
                        recepciones.add(i, Recepcion(idAsignacion, nombreOperador, nombreUsuario, modeloUnidad ,pan, fruta, abarrote, noComestible ))

                        val adaptador = Adapter_receptor_recepciones(this@Activity_receptor_recepcion_consulta, R.layout.receptor_card_recepcion, recepciones)
                        lstRecepciones.adapter = adaptador

                        lstRecepciones.setOnItemClickListener{parent, view, position, id ->
                            val intent = Intent(this@Activity_receptor_recepcion_consulta, Activity_receptor_recepcion_formulario:: class.java)
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
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("Hola", e.toString())

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)

    }
}