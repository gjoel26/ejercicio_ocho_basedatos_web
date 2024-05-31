package com.example.ejercicio_ocho

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    fun save(v: View) {
        val name = findViewById<EditText>(R.id.txtName).text.toString()
        val phone = findViewById<EditText>(R.id.txtPhoneNomber).text.toString()
        val contact = Contact(0, name, phone)

        val retrofit = RetrofitApp.getRetrofit()
        val service = retrofit.create(IContact::class.java)
        val petition: Call<Boolean> = service.addPersona(contact)

        petition.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val status = response.body()
                    Log.v("Respuesta", "Respuesta $status")
                    if (status == true) {
                        Toast.makeText(applicationContext, "Contacto guardado correctamente", Toast.LENGTH_LONG).show()
                        // Finaliza la actividad actual y regresa a la anterior
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Error al guardar el contacto", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Manejo del caso en que la respuesta no fue exitosa
                    Log.e("Error", "Error en la respuesta: ${response.code()} ${response.message()}")
                    Toast.makeText(applicationContext, "Error al guardar el contacto", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("Error", "Error en la petición: ${t.message}")
                Toast.makeText(applicationContext, "Error de red", Toast.LENGTH_LONG).show()
            }
        })
    }
  fun cancelOrDelete(v: View) {
    // Crear un Intent para iniciar AddActivity
    val intent = Intent(this, MainActivity::class.java)
    // Iniciar AddActivity
    startActivity(intent)
    // Finaliza la actividad actual
    finish()
}
}