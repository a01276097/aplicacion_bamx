package com.example.aplicacion_bamx

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.general_login.*
import org.json.JSONObject

class Activity_general_login : AppCompatActivity(), View.OnClickListener {
    var logIn: Button? = null;
    lateinit var forgotPassword: Button;
    lateinit var usuario: EditText;
    lateinit var password: EditText;
    lateinit var sharedPreferences: SharedPreferences
    lateinit var queue: RequestQueue

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.general_login)
        sharedPreferences = getSharedPreferences("login",
            Context.MODE_PRIVATE)

        if(sharedPreferences.getString("cargo","@") == "operador" && sharedPreferences.getString("cargo","@") != "@"){
            val intent_operador = Intent(this@Activity_general_login, Activity_operador_menu_lateral::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent_operador)
        } else if(sharedPreferences.getString("cargo","@") == "receptor"  &&
            sharedPreferences.getString("cargo","@") != "@"){
            val intent_receptor = Intent(this@Activity_general_login, Activity_receptor_menu_lateral::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent_receptor)
        }

        logIn = findViewById<Button>(R.id.iniciar_sesion)
        forgotPassword = findViewById<Button>(R.id.olvide_contraseña)
        usuario = findViewById<EditText>(R.id.entrada_usuario)
        password = findViewById<EditText>(R.id.entrada_contraseña)

        logIn!!.setOnClickListener(this@Activity_general_login)
        forgotPassword.setOnClickListener(this@Activity_general_login)
    }

    override fun onClick(p0: View?){
        when(p0!!.id){
            R.id.iniciar_sesion -> {

                val intent_operador = Intent(this@Activity_general_login, Activity_operador_menu_lateral::class.java)
                val intent_receptor = Intent(this@Activity_general_login, Activity_receptor_menu_lateral::class.java)

                queue = Volley.newRequestQueue(this)

                val nombreUsuario = entrada_usuario.text.toString()
                val contrasena = entrada_contraseña.text.toString()

                val body1 = JSONObject()
                body1.put("nombreUsuario", nombreUsuario)
                body1.put("contrasena", contrasena)

                val body = JSONObject()
                body.put("body", body1)

                Log.e("autenticacion", body.toString())


                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    "http://bamxapi-env.eba-wsth22h3.us-east-1.elasticbeanstalk.com/users/login",
                    body,
                    { response ->
                        Log.e("VOLLEYRESPONSE", response.toString())
                        val cargo = response.get("rol").toString()
                        val idUsuario = response.get("idUser").toString()
                        with(sharedPreferences.edit()) {
                            putString("cargo", cargo)
                            putString("id_usuario", idUsuario)
                            commit()
                        }

                        if(cargo =="operador"){
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent_operador)
                        }
                        else if(cargo=="receptor"){
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent_receptor)
                        }

                    },
                    {error->
                        Toast.makeText(this@Activity_general_login, "Credenciales inválidas. Compruebe usuario y contraseña", Toast.LENGTH_LONG).show()

                        Log.e("VOLLEYRESPONSE", error.toString())
                    }
                )
                queue.add(jsonObjectRequest)


            }
            R.id.olvide_contraseña -> {
                Toast.makeText(this,
                    "Contacte al administrador del sistema para cambiar su contraseña.",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}