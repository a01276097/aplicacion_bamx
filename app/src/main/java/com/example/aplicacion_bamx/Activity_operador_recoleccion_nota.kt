package com.example.aplicacion_bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class Activity_operador_recoleccion_nota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_recoleccion_nota)

        val url="http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/collections?thisCollection=3"

        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val res = response.getJSONObject("data")

                    val folio = res.getString("folio")
                    val fechaRecoleccion = res.getString("fechaRecoleccion")
                    val responsable = res.getString("responsable")
                    val donador = res.getString("donador")
//                    val pan
//                    val frutaVerdura
//                    val abarrote
//                    val noComestible

                    val tFolio = findViewById<TextView>(R.id.txtFolioNota)
                    val tFechaRecoleccion = findViewById<TextView>(R.id.txtFechaNota)
                    val tResponsable = findViewById<TextView>(R.id.txtResponsableNota)
                    val tDonador = findViewById<TextView>(R.id.txtDonadorNota)
                    val tPan = findViewById<TextView>(R.id.txtPanNota)
                    val tFrutaVerdura = findViewById<TextView>(R.id.txtFrutaVerduraNota)
                    val tAbarrote = findViewById<TextView>(R.id.txtAbarroteNota)
                    val tNoComestible = findViewById<TextView>(R.id.txtNoComestibleNota)

                    tFolio.setText(tFolio.text.toString()  + " $folio")
                    tFechaRecoleccion.setText(fechaRecoleccion)
                    tResponsable.setText(tResponsable.text.toString()  + " $responsable en")
                    tDonador.setText(donador)
//                    tPan.setText(tPan.text.toString() + " $pan kg")
//                    tFrutaVerdura.setText(tFrutaVerdura.text.toString() + " $frutaVerdura kg")
//                    tAbarrote.setText(tAbarrote.text.toString() + " $abarrote kg")
//                    tNoComestible.setText(tNoComestible.text.toString() + " $noComestible kg")

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)


    }
}