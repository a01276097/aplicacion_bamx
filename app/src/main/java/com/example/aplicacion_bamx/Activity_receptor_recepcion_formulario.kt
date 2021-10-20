package com.example.aplicacion_bamx

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Activity_receptor_recepcion_formulario : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_recepcion_formulario)

        queue = Volley.newRequestQueue(this)

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id_usuario","-1")

        val btnModificar = findViewById<Button>(R.id.boton_modificar_form)
        val btnConfirmar = findViewById<Button>(R.id.boton_confirmar_form)

        val  idWarehousesAssignation = intent.getIntExtra("idAsignacionBodega",0)
        val nombreOperador = intent.getStringExtra("nombre")
        val unidad = intent.getStringExtra("unidad")
        val usuarioOperador = intent.getStringExtra("usuario")
        val cantPan = intent.getIntExtra("pan",0)
        val cantAbarrote = intent.getIntExtra("abarrote",0)
        val cantFrutaVerdura = intent.getIntExtra("fruta",0)
        val cantNoComestible = intent.getIntExtra("noComestible",0)


        val tNombreOperador = findViewById<TextView>(R.id.titulos_2)
        val tUnidad = findViewById<TextView>(R.id.txtUnidadOperador)
        val tUsuarioOperador = findViewById<TextView>(R.id.txtUsuarioOperador)
        val tPan = findViewById<TextView>(R.id.txtPanRecepcion)
        val tFrutaVerdura = findViewById<TextView>(R.id.txtFrutaVerduraRecepcion)
        val tNoComestible = findViewById<TextView>(R.id.txtNoComestibleRecepcion)
        val tAbarrote = findViewById<TextView>(R.id.txtAbarroteRecepcion)


        tNombreOperador.setText(nombreOperador)
        tUnidad.setText("Unidad: ${unidad}")
        tUsuarioOperador.setText("Operador: $usuarioOperador")

        tPan.setText(tPan.text.toString() + " ${cantPan} kg")
        tFrutaVerdura.setText(tFrutaVerdura.text.toString() + " ${cantFrutaVerdura} kg")
        tNoComestible.setText(tNoComestible.text.toString() + " ${cantNoComestible} kg")
        tAbarrote.setText(tAbarrote.text.toString() + " ${cantAbarrote} kg")

        btnModificar.setOnClickListener {
            val intent = Intent(this@Activity_receptor_recepcion_formulario, Activity_receptor_recepcion_modificar:: class.java)
            intent.putExtra("idAsignacionBodega", idWarehousesAssignation)
            intent.putExtra("pan", cantPan)
            intent.putExtra("abarrote", cantAbarrote)
            intent.putExtra("fruta", cantFrutaVerdura)
            intent.putExtra("noComestible", cantNoComestible)
            startActivity(intent)
        }

        btnConfirmar.setOnClickListener {
            confirmDelivery(idWarehousesAssignation, idUser!!, cantPan, cantAbarrote, cantFrutaVerdura, cantNoComestible)
        }


    }


    fun confirmDelivery(idWarehousesAssignation : Int, idUser: String, pan: Int, abarrote:Int, frutaVerdura: Int, noComestible:Int){


        val data = JSONObject()
        val delivery = JSONObject()
        delivery.put("idReceiver", idUser)
        delivery.put("idWarehousesAssignation", idWarehousesAssignation)


        val delivered = JSONObject()
        val ent1 = JSONObject()
        val ent2 = JSONObject()
        val ent3 = JSONObject()
        val ent4 = JSONObject()


        ent1.put("cantidad", pan)
        ent1.put("idCategoria", "1")

        ent2.put("cantidad", abarrote)
        ent2.put("idCategoria", "2")

        ent3.put("cantidad", frutaVerdura)
        ent3.put("idCategoria", "3")

        ent4.put("cantidad", noComestible)
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

        val builder = AlertDialog.Builder(this@Activity_receptor_recepcion_formulario)
        builder.setTitle("Confirmación de recepción en bodega")
            .setMessage(txtDialog)
            .setPositiveButton("Confirmar"){_,_ ->
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/deliveries",
                    data,
                    { response ->
                        Log.e("VOLLEYRESPONSE", response.toString())
                        val main_receptor = Intent(this@Activity_receptor_recepcion_formulario, Activity_receptor_menu_lateral::class.java)
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