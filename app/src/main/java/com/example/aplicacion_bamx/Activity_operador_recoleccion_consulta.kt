package com.example.aplicacion_bamx

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacion_bamx.model.Recoleccion
import org.json.JSONException


class Activity_operador_recoleccion_consulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_recoleccion_consulta)


        val url = "https://reqres.in/api/users?page=2"
        val url1 = "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/collections/driver?thisDriver=7"
        val recolecciones = mutableListOf<Recoleccion>()
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url1, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("Hola", jsonObject.toString())
//                        val id = jsonObject.getInt("id")
//                        val first_name = jsonObject.getString("first_name")
//                        val email = jsonObject.getString("email")
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
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("Hola", e.toString())

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)



        val lstRecolecciones = findViewById<ListView>(R.id.lista_recolecciones)

        val adaptador = Adapter_operador_recolecciones(this@Activity_operador_recoleccion_consulta, R.layout.operador_card_consulta_recoleccion, recolecciones)

        lstRecolecciones.adapter = adaptador

        //pasar datos a la siguiente pantalla
//        lstRecolecciones.setOnItemClickListener{parent, view, position, id ->
//            val intent = Intent(this@Activity_operador_recoleccion_consulta, Activity_operador_recoleccion_consulta:: class.java)
//            intent.putExtra("id", datos[position].donador_id)
//            intent.putExtra("nombre", datos[position].donador_nombre)
//            startActivity(intent)
//        }

    }
}