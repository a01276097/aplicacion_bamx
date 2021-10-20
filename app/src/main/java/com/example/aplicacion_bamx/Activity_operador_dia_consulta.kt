package com.example.aplicacion_bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacion_bamx.model.Dia
import com.example.aplicacion_bamx.model.Recoleccion
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class Activity_operador_dia_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_dia_consulta)

        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = findViewById<TextView>(R.id.txtFechaNota)
        txtFecha.text=currentDateAndTime

        val emptystate = findViewById<LinearLayout>(R.id.layout_empystate_hechas)


        val url = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/collections/done/driver?thisDriver=2"
        val lstRecoleccionesHechas = findViewById<ListView>(R.id.lista_recolecciones_hechas)
        val recoleccionesHechas = mutableListOf<Recoleccion>()
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    lstRecoleccionesHechas.visibility = View.VISIBLE
                    emptystate.visibility= View.GONE
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val idCollection  = jsonObject.getInt("idCollection")
                        val idDonor = jsonObject.getInt("idDonor")
                        val nombre = jsonObject.getString("nombre")
                        val callen = jsonObject.getString("calle")
                        val numExterior = jsonObject.getString("numExterior")
                        val colonia= jsonObject.getString("colonia")
                        val municipio = jsonObject.getString("municipio")
                        val cp = jsonObject.getString("cp")
                        val estado = jsonObject.getString("estado")
                        val direccion = callen + ", " + numExterior + ", " + colonia + ", " + municipio + ", " + estado + ", " + cp
                        recoleccionesHechas.add(i, Recoleccion(idCollection,idDonor, nombre, direccion))

                        val adaptador = Adapter_operador_recolecciones(this@Activity_operador_dia_consulta, R.layout.operador_card_consulta_dia, recoleccionesHechas)
                        lstRecoleccionesHechas.adapter = adaptador

                        lstRecoleccionesHechas.setOnItemClickListener{parent, view, position, id ->
                            val intent = Intent(this@Activity_operador_dia_consulta, Activity_operador_recoleccion_nota:: class.java)
                            intent.putExtra("IdDonor", recoleccionesHechas[position].donador_id)
                            intent.putExtra("Nombre", recoleccionesHechas[position].donador_nombre)
                            intent.putExtra("idRecoleccion", recoleccionesHechas[position].recoleccion_id)
                            intent.putExtra("previa", "recoleccionesHechas")
                            startActivity(intent)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)


    }
}