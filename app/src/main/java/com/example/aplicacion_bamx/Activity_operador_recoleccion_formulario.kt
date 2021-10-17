package com.example.aplicacion_bamx

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.operador_recoleccion_formulario.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.AbstractCollection

class Activity_operador_recoleccion_formulario : AppCompatActivity(), LocationListener {

    lateinit var queue: RequestQueue
    lateinit var  locationManager: LocationManager
    var latitud : Double = 0.0
    var longitud : Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operador_recoleccion_formulario)


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        checkPermissions(this)

        val idDonor = intent.getIntExtra("Id", 0)
        val idCollection = intent.getIntExtra("IdCollection", 0)
        val nombreDonador = intent.getStringExtra("Nombre")



        val donador = findViewById<TextView>(R.id.titulos_2)
        donador.text=nombreDonador;

        queue = Volley.newRequestQueue(this)
        val btnGuardar = findViewById<Button>(R.id.boton_continuar_nota)

        btnGuardar.setOnClickListener{
            insertUser(idDonor, nombreDonador!!, idCollection)
        }

    }

    override fun onLocationChanged(p0: Location) {
        latitud = p0.latitude
        longitud = p0.longitude
    }


    fun insertUser(idDonor : Int, nombreDonador: String, idCollection : Int){

        var nota : String = ""
        val hasNota = findViewById<RadioGroup>(R.id.radio_Formato)
        val idRB = hasNota.checkedRadioButtonId

        when (idRB) {
            R.id.rdSi -> nota = "1"
            R.id.rdNo -> nota = "0"
            else -> nota = "-1"
        }

        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val local = Locale("es","MX")
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss", local)
        simpleDateFormat.setTimeZone(myTimeZone)
        val fechaHora: String = simpleDateFormat.format(Date())


        val data = JSONObject()
        val collections = JSONObject()
        collections.put("recolectado", "1")
        collections.put("nota", nota)
        collections.put("folio", edtFolio.text.toString())
        collections.put("responsableEntrega", edtResponsable.text.toString())
        collections.put("longitud", longitud)
        collections.put("latitud", latitud)
        collections.put("fechaRecoleccion", fechaHora)


        val rec1 = JSONObject()
        val rec2 = JSONObject()
        val rec3 = JSONObject()
        val rec4 = JSONObject()


        rec1.put("idCollection", idCollection.toString())
        rec1.put("cantidad", edtPan.text.toString())
        rec1.put("idCategory", "1")

        rec2.put("idCollection", idCollection.toString())
        rec2.put("cantidad", edtAbarrote.text.toString())
        rec2.put("idCategory", "2")

        rec3.put("idCollection", idCollection.toString())
        rec3.put("cantidad", edtFrutaVerdura.text.toString())
        rec3.put("idCategory", "3")

        rec4.put("idCollection", idCollection.toString())
        rec4.put("cantidad", edtNoComestible.text.toString())
        rec4.put("idCategory", "4")

        val collected = JSONObject()

        collected.put("rec1", rec1)
        collected.put("rec2", rec2)
        collected.put("rec3", rec3)
        collected.put("rec4", rec4)

        data.put("collections", collections)
        data.put("collected", collected)

        Log.e("JSON", data.toString())

        val txtDialog = "FOLIO ${collections["folio"]} \n " +
                        "${collections["fechaRecoleccion"]} \n\n" +
                        "$nombreDonador \n" +
                        "Pan: ${rec1["cantidad"]} kg \n" +
                        "Abarrote: ${rec2["cantidad"]} kg \n" +
                        "Fruta y verdura: ${rec3["cantidad"]} kg \n" +
                        "No comestible: ${rec4["cantidad"]} kg \n"

        val builder = AlertDialog.Builder(this@Activity_operador_recoleccion_formulario)
        builder.setTitle("Confirme su recolección")
            .setMessage(txtDialog)
            .setPositiveButton("Confirmar"){_,_ ->
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.PATCH,
                    "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/donors/collected/collections?thisCollection=$idCollection",
                    data,
                    { response ->
                        Log.e("VOLLEYRESPONSE", response.toString())
                        val notaConfirmacion = Intent(this@Activity_operador_recoleccion_formulario, Activity_operador_recoleccion_nota::class.java)
                        notaConfirmacion.putExtra("idRecoleccion", idCollection)
                        intent.putExtra("previa", "formulario")
                        startActivity(notaConfirmacion)

                    },
                    {error->
                        Log.e("VOLLEYRESPONSE", error.message!!)
                    }
                )
                queue.add(jsonObjectRequest)
            }
            .setNegativeButton("Cancelar", {dialog, button -> dialog.dismiss()})
            .show()


    }


    private fun checkPermissions(context: Activity){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)){
                val builder = AlertDialog.Builder(context)
                builder.setMessage("El acceso a la ubicación se requiere por...")
                    .setTitle("Permiso de ubicacion requqerido")
                builder.setPositiveButton("OK") { dialog, id->
                    ActivityCompat.requestPermissions(context,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 45)
                }
                val dialogo = builder.create()
                dialogo.show()
            }
            else{
                // Si no se necesita mostrar una explicación al usuario
                ActivityCompat.requestPermissions(context,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 45)
            }
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                1f,
                this)
        }
    }



}