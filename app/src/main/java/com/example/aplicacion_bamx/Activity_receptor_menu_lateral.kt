package com.example.aplicacion_bamx

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aplicacion_bamx.R
import com.example.aplicacion_bamx.fragments.Fragment_operador_recoleccion_consulta
import com.example.aplicacion_bamx.fragments.Fragment_receptor_recepcion_consulta
import com.google.android.material.navigation.NavigationView

class Activity_receptor_menu_lateral : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receptor_menu_lateral)

        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val image_menu = findViewById<ImageView>(R.id.image_menu)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val fragmentManager = supportFragmentManager

        val sharedPreferences = getSharedPreferences("login",
            Context.MODE_PRIVATE)

        // Fragment inicial del receptor
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.contenedor, Fragment_receptor_recepcion_consulta())
        ft.commit()
        drawer_layout.closeDrawer(GravityCompat.START)

        image_menu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.boton_operadores_ruta -> {
                    val ft = fragmentManager.beginTransaction()
                    ft.replace(R.id.contenedor, Fragment_receptor_recepcion_consulta())
                    ft.commit()
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.boton_cerrar_sesion -> {
                    with(sharedPreferences.edit()){
                        remove("cargo")
                        commit()
                    }
                    val intent_login = Intent(this@Activity_receptor_menu_lateral, Activity_general_login::class.java)
                    startActivity(intent_login)
                    true
                }
                else -> false
            }
        }
    }
}