package com.example.aplicacion_bamx

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Activity_general_login : AppCompatActivity(), View.OnClickListener {
    var logIn: Button? = null;
    lateinit var forgotPassword: Button;
    lateinit var usuario: EditText;
    lateinit var password: EditText;
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.general_login)

        sharedPreferences = getSharedPreferences("login",
            Context.MODE_PRIVATE)

        if(sharedPreferences.getString("operador","@") == "operador" &&
            sharedPreferences.getString("operador","@") != "@"){
            // mandar al home
            val intent_operador = Intent(this@Activity_general_login, Activity_operador_menu_lateral::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent_operador)
        } else if(sharedPreferences.getString("receptor","@") == "receptor"  &&
            sharedPreferences.getString("receptor","@") != "@"){
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

                if (usuario.text.toString() == "operador" &&  // peticion al API
                    password.text.toString() == "123") {
                    // usuario correcto
                    with(sharedPreferences.edit()) {
                        putString("operador", usuario.text.toString())
                        commit()
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent_operador)

                } else if (usuario.text.toString() == "receptor" &&  // peticion al API
                    password.text.toString() == "321") {
                    // usuario correcto
                    with(sharedPreferences.edit()) {
                        putString("receptor", usuario.text.toString())
                        commit()
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent_receptor)

                } else {
                    // usuario incorrecto
                    Toast.makeText(
                        this,
                        "Usuario o contrasena incorrectas",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
            R.id.olvide_contraseña -> {
                Toast.makeText(this,
                    "olvide mi contrasena",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}