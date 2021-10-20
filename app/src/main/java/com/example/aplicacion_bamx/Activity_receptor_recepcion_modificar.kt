package com.example.aplicacion_bamx

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacion_bamx.R
import kotlinx.android.synthetic.main.operador_recoleccion_formulario.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Activity_receptor_recepcion_modificar : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_modificar)

        queue = Volley.newRequestQueue(this)

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id_usuario","-1")

        val tFrutaVerdura1 = findViewById<TextView>(R.id.campo_fruta_verdura)
        val tPan1 = findViewById<TextView>(R.id.campo_pan)
        val tAbarrote1 = findViewById<TextView>(R.id.campo_abarrote)
        val tNoComestible1 = findViewById<TextView>(R.id.campo_no_comestible)


        val  idWarehousesAssignation = intent.getIntExtra("idAsignacionBodega",0)
        val pan = intent.getIntExtra("pan",0)
        val abarrote = intent.getIntExtra("abarrote",0)
        val frutaVerdura = intent.getIntExtra("fruta",0)
        val noComestible = intent.getIntExtra("noComestible",0)

        tFrutaVerdura1.setText(frutaVerdura.toString())
        tPan1.setText(pan.toString())
        tAbarrote1.setText(abarrote.toString())
        tNoComestible1.setText(noComestible.toString())

        val btnConfirmacion = findViewById<Button>(R.id.boton_confirmar)
        val btnCancelar = findViewById<Button>(R.id.boton_cancelar)


        btnConfirmacion.setOnClickListener {
            confirmDelivery(idWarehousesAssignation, idUser!!)
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    fun confirmDelivery(idWarehousesAssignation : Int, idUser: String){

        val tFrutaVerdura1 = findViewById<TextView>(R.id.campo_fruta_verdura)
        val tPan1 = findViewById<TextView>(R.id.campo_pan)
        val tAbarrote1 = findViewById<TextView>(R.id.campo_abarrote)
        val tNoComestible1 = findViewById<TextView>(R.id.campo_no_comestible)

        val data = JSONObject()
        val delivery = JSONObject()
        delivery.put("idReceiver", idUser)
        delivery.put("idWarehousesAssignation", idWarehousesAssignation)


        val delivered = JSONObject()
        val ent1 = JSONObject()
        val ent2 = JSONObject()
        val ent3 = JSONObject()
        val ent4 = JSONObject()


        ent1.put("cantidad", tPan1.text.toString())
        ent1.put("idCategoria", "1")

        ent2.put("cantidad", tAbarrote1.text.toString())
        ent2.put("idCategoria", "2")

        ent3.put("cantidad", tFrutaVerdura1.text.toString())
        ent3.put("idCategoria", "3")

        ent4.put("cantidad", tNoComestible1.text.toString())
        ent4.put("idCategoria", "4")

        val collected = JSONObject()

        delivered.put("ent1", ent1)
        delivered.put("ent2", ent2)
        delivered.put("ent3", ent3)
        delivered.put("ent4", ent4)

        data.put("delivery", delivery)
        data.put("delivered", delivered)

        Log.e("JSON", data.toString())

        val txtDialog = "Confirme las cantidades que recibió: \n" +
                "Pan: ${ent1["cantidad"]} kg \n" +
                "Abarrote: ${ent2["cantidad"]} kg \n" +
                "Fruta y verdura: ${ent3["cantidad"]} kg \n" +
                "No comestible: ${ent4["cantidad"]} kg \n"

        val builder = AlertDialog.Builder(this@Activity_receptor_recepcion_modificar)
        builder.setTitle("Confirmación de recepción en bodega")
            .setMessage(txtDialog)
            .setPositiveButton("Confirmar"){_,_ ->
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/deliveries",
                    data,
                    { response ->
                        Log.e("VOLLEYRESPONSE", response.toString())
                        val main_receptor = Intent(this@Activity_receptor_recepcion_modificar, Activity_receptor_menu_lateral::class.java)
                        startActivity(main_receptor)
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

}