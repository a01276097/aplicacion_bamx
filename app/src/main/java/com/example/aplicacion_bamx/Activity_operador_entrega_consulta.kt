package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacion_bamx.model.Entrega
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class Activity_operador_entrega_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_entrega_consulta)

        val emptystateEntrega = findViewById<LinearLayout>(R.id.layout_emptystate)


        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val txtFecha = findViewById<TextView>(R.id.txtFechaEntrega)
        txtFecha.text=currentDateAndTime

        val url = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/drivers/assignedWarehouses/2"
        val lstEntregas = findViewById<ListView>(R.id.lista_entrega)
        val entregas = mutableListOf<Entrega>()
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    lstEntregas.visibility = View.VISIBLE
                    emptystateEntrega.visibility= View.GONE
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val nombreBodega = jsonObject.getString("bodega")
                        val callen = jsonObject.getString("calle")
                        val numExterior = jsonObject.getString("numExterior")
                        val colonia= jsonObject.getString("colonia")
                        val municipio = jsonObject.getString("municipio")
                        val cp = jsonObject.getString("cp")
                        val pan = jsonObject.getInt("pan")
                        val fruta = jsonObject.getInt("fruta")
                        val abarrote = jsonObject.getInt("abarrote")
                        val noComestible = jsonObject.getInt("noComestible")
                        val direccion = callen + ", " + numExterior + ", " + colonia + ", " + municipio + ", " + cp
                        entregas.add(i, Entrega(nombreBodega, direccion,pan, fruta, abarrote, noComestible ))

                        val adaptador = Adapter_operador_entregas(this@Activity_operador_entrega_consulta, R.layout.operador_card_consulta_entrega, entregas)
                        lstEntregas.adapter = adaptador

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)

    }
}