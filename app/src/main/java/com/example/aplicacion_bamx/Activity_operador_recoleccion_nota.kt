package com.example.aplicacion_bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class Activity_operador_recoleccion_nota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_recoleccion_nota)

        val loading = findViewById<ProgressBar>(R.id.loadingBar)
        val nota = findViewById<LinearLayout>(R.id.notaRecoleccion)
        val btnContinuar = findViewById<Button>(R.id.boton_continuar_nota)

        val idCollection = intent.getIntExtra("idRecoleccion", -1)
        val pantallaPrevia = intent.getStringExtra("previa")
        Log.e("Hola", idCollection.toString())
        val url="http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/collections?thisCollection=$idCollection"

        btnContinuar.setOnClickListener {
            if (pantallaPrevia == "formulario") {
                val pantallaPrincipal = Intent(
                    this@Activity_operador_recoleccion_nota,
                    Activity_operador_menu_lateral::class.java
                )
                startActivity(pantallaPrincipal)
            } else if (pantallaPrevia == "recoleccionesHechas") {
                val pantallaPrincipal = Intent(
                    this@Activity_operador_recoleccion_nota,
                    Activity_operador_menu_lateral::class.java
                )
                pantallaPrincipal.putExtra("pantalla", 1)
                startActivity(pantallaPrincipal)
            }
        }

        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    loading.visibility = View.GONE
                    nota.visibility = View.VISIBLE
                    val res = response.getJSONArray("collection")
                    Log.e("JSON",res.toString())
                    val data = res.getJSONObject(0)
                    val folio = data.getString("folio")
                    val fechaRecoleccion = data.getString("fechaRecoleccion")
                    val responsable = data.getString("responsableEntrega")
                    val donador = data.getString("nombre")
                    val pan = data.getString("cantidad")
                    val nota = data.getInt("nota")
                    val abarroteObj = res.getJSONObject(1)
                    val abarrote = abarroteObj.getString("cantidad")
                    val frutaVerduraObj = res.getJSONObject(2)
                    val frutaVerdura = frutaVerduraObj.getString("cantidad")
                    val noComestibleObj = res.getJSONObject(3)
                    val noComestible = noComestibleObj.getString("cantidad")

                    val tNota = findViewById<TextView>(R.id.linea_2)
                    val tFolio = findViewById<TextView>(R.id.txtFolioNota)
                    val tFechaRecoleccion = findViewById<TextView>(R.id.txtFechaNota)
                    val tResponsable = findViewById<TextView>(R.id.txtResponsableNota)
                    val tDonador = findViewById<TextView>(R.id.txtDonadorNota)
                    val tPan = findViewById<TextView>(R.id.txtPanNota)
                    val tFrutaVerdura = findViewById<TextView>(R.id.txtFrutaVerduraNota)
                    val tAbarrote = findViewById<TextView>(R.id.txtAbarroteNota)
                    val tNoComestible = findViewById<TextView>(R.id.txtNoComestibleNota)

                    if (nota==1){
                        tNota.setText("Recibió nota del establecimiento donador")
                    }
                    tFolio.setText(tFolio.text.toString()  + " $folio")
                    tFechaRecoleccion.setText(fechaRecoleccion)
                    tResponsable.setText(tResponsable.text.toString()  + " $responsable en")
                    tDonador.setText(donador)
                    tPan.setText(tPan.text.toString() + " $pan kg")
                    tFrutaVerdura.setText(tFrutaVerdura.text.toString() + " $frutaVerdura kg")
                    tAbarrote.setText(tAbarrote.text.toString() + " $abarrote kg")
                    tNoComestible.setText(tNoComestible.text.toString() + " $noComestible kg")

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            })
        { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)


    }
}